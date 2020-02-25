package com.idea.group.neverhaveiever.Views

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.facebook.ads.*
import com.idea.group.neverhaveiever.BuildConfig
import com.idea.group.neverhaveiever.Controllers.BaseControllerFactory
import com.idea.group.neverhaveiever.Controllers.NeverHaveIEverController
import com.idea.group.neverhaveiever.Models.APIModels.IHaveNeverCardAPIModel
import com.idea.group.neverhaveiever.R
import com.idea.group.neverhaveiever.Services.AnalyticsService
import com.idea.group.neverhaveiever.Views.CustomViews.IHaveNeverCardView
import com.idea.group.neverhaveiever.Views.Interfaces.IMenuHost
import com.idea.group.neverhaveiever.Views.Interfaces.IOnCardSwipe
import com.mindorks.placeholderview.SwipeDecor
import com.mindorks.placeholderview.SwipePlaceHolderView
import com.mindorks.placeholderview.SwipeViewBuilder
import mosquito.digital.template.mdpersistence.PersistenceService
import java.util.function.Consumer


private const val ARG_PARAM1 = "param1"

class NeverHaveIEverFragment : Fragment() , IOnCardSwipe {

    private var contentType: String? = null
    private var listener: IMenuHost? = null
    private var mSwipeView: SwipePlaceHolderView? = null
    private var adView: AdView? = null
    private var topHolder : LinearLayout? = null
    private var refresherView : View? = null
    private lateinit var refresherButton : Button
    private var cardCount = 0
    private lateinit var controller : NeverHaveIEverController
    private lateinit var  interstitialAd : InterstitialAd
    private var swipesSinceLastAd = 0
    private val NUMBER_OF_SWIPES_BEFORE_AD = 14

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            contentType = it.getString(ARG_PARAM1)

            controller = ViewModelProvider(this,BaseControllerFactory{
                NeverHaveIEverController(PersistenceService(),AnalyticsService(),contentType!!)
            }).get(NeverHaveIEverController::class.java)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(
            R.layout.fragment_never_have_i_ever,
            container, false
        )

        if (BuildConfig.ADS_ENABLED) {
            loadAd(view)
        }

        topHolder = view.findViewById(R.id.top)

        val image = ImageView(this.context)
        image.setImageDrawable(this.context!!.resources.getDrawable(R.drawable.ic_menu_black_24dp))
        image.setColorFilter(Color.argb(255, 255, 255, 255))
        image.isClickable = true
        image.setOnClickListener {
            listener?.showMenu()
        }

        topHolder!!.addView(image)

        setUpSwipeView(view)
        return view
    }

    private fun setUpSwipeView(view: View) {
        mSwipeView = view.findViewById(R.id.swipeView)
        mSwipeView!!.getBuilder<SwipePlaceHolderView, SwipeViewBuilder<SwipePlaceHolderView>>()
            .setDisplayViewCount(3)
            .setSwipeDecor(
                SwipeDecor()
                    .setPaddingTop(20)
                    .setRelativeScale(0.01f)

                    .setSwipeInMsgLayoutId(R.layout.swipe_in_view)
                    .setSwipeOutMsgLayoutId(R.layout.swipe_out_view)
            )
        val onClick = View.OnClickListener {
            mSwipeView!!.doSwipe(true)
        }
        setUpTestData(onClick)

        refresherView = view.findViewById(R.id.refresh_view)
        refresherButton = view.findViewById(R.id.never_card_view_reload);
        refresherButton.setOnClickListener {
            setUpTestData(onClick)
            refresherView!!.visibility = View.GONE
        }
    }

    private fun loadAd(view: View) {
        AdSettings.addTestDevice("HASHED ID")

        val adContainer = view.findViewById(R.id.banner_container) as LinearLayout

        adView =
            AdView(this.context, "638417063572474_638420086905505", AdSize.BANNER_HEIGHT_50)
        adContainer.addView(adView)

        adView!!.loadAd()

        interstitialAd = InterstitialAd(this.context,"638417063572474_653995195347994")
        interstitialAd.setAdListener(object : InterstitialAdListener {
            override fun onInterstitialDisplayed(ad: Ad) { // Interstitial ad displayed callback
                Log.e("", "Interstitial ad displayed.")
            }

            override fun onInterstitialDismissed(ad: Ad) { // Interstitial dismissed callback
                Log.e("", "Interstitial ad dismissed.")
            }

            override fun onError(ad: Ad, adError: AdError) { // Ad error callback
                Log.e("", "Interstitial ad failed to load: " + adError.errorMessage)
            }

            override fun onAdLoaded(ad: Ad) { // Interstitial ad is loaded and ready to be displayed
                Log.d("", "Interstitial ad is loaded and ready to be displayed!")
                // Show the ad
                interstitialAd.show()
            }

            override fun onAdClicked(ad: Ad) { // Ad clicked callback
                Log.d("", "Interstitial ad clicked!")
            }

            override fun onLoggingImpression(ad: Ad) { // Ad impression logged callback
                Log.d("", "Interstitial ad impression logged!")
            }
        })

    }

    private fun setUpTestData(onClick: View.OnClickListener) {
        val testData = controller.getTestData(contentType!!);

        cardCount = testData.count()
        testData.forEach {
            mSwipeView!!.addView(
                IHaveNeverCardView(
                    this.context,
                    it,
                    mSwipeView,
                    onClick,
                    this
                )
            )
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is IMenuHost) {
            listener = context

        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }

    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onDestroy() {
        if (adView != null) {
            adView!!.destroy()
        }
        if (interstitialAd != null) {
            interstitialAd.destroy()
        }

        super.onDestroy()
    }

    companion object {
        @JvmStatic
        val CARDS_NAUGHTY = "NAUGHTY"
        @JvmStatic
        val CARDS_CLEAN = "CLEAN"
        @JvmStatic
        val CARDS_EXPOSED = "EXPOSED"
        @JvmStatic
        val CARDS_COUPLES = "COUPLES"
        @JvmStatic
        fun newInstance(param1: String) =
            NeverHaveIEverFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }

    override fun OnSwipe(pos: Int) {
        controller.ItemSeen(pos)
      if (cardCount == pos)
        {
            refresherView!!.visibility = View.VISIBLE
        }

        if(swipesSinceLastAd >= NUMBER_OF_SWIPES_BEFORE_AD)
        {
            interstitialAd.loadAd()
            swipesSinceLastAd = 0
        }
        else
        {
            swipesSinceLastAd++
        }
    }

    override fun OnBadSwipe(pos: Int) {
        controller.ItemVotedBad(pos)
    }
}
