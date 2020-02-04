package com.idea.group.neverhaveiever.Views

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.facebook.ads.AdSettings
import com.facebook.ads.AdSize
import com.facebook.ads.AdView
import com.idea.group.neverhaveiever.Models.APIModels.IHaveNeverCardAPIModel
import com.idea.group.neverhaveiever.Models.UIModels.IHaveNeverCardUIModel
import com.idea.group.neverhaveiever.R
import com.mindorks.placeholderview.SwipeDecor
import com.mindorks.placeholderview.SwipePlaceHolderView
import com.mindorks.placeholderview.SwipeViewBuilder

private const val ARG_PARAM1 = "param1"

class NeverHaveIEverFragment : Fragment() {

    private var param1: String? = null
    private var listener: IMenuHost? = null
    private var mSwipeView: SwipePlaceHolderView? = null
    private var adView: AdView? = null
    private var topHolder : LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
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
        AdSettings.addTestDevice("HASHED ID")

        // Find the Ad Container
        val adContainer = view.findViewById(R.id.banner_container) as LinearLayout
        this.activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        // Add the ad view to your activity layout
        adView = AdView(this.context, "638417063572474_638420086905505", AdSize.BANNER_HEIGHT_50)
        adContainer.addView(adView)

        adView!!.loadAd()
        mSwipeView = view.findViewById(R.id.swipeView);
        mSwipeView!!.getBuilder<SwipePlaceHolderView, SwipeViewBuilder<SwipePlaceHolderView>>()
            .setDisplayViewCount(3)
            .setSwipeDecor(
                SwipeDecor()
                    .setPaddingTop(20)
                    .setRelativeScale(0.01f)

                    .setSwipeInMsgLayoutId(R.layout.swipe_in_view)
                    .setSwipeOutMsgLayoutId(R.layout.swipe_out_view)
            )
        val onClick =  View.OnClickListener {
            mSwipeView!!.doSwipe(true)
        }
        mSwipeView!!.addView( IHaveNeverCardUIModel(this.context, IHaveNeverCardAPIModel(id = "1",info = "Had Sex on a Boat" ),mSwipeView,onClick));
        mSwipeView!!.addView( IHaveNeverCardUIModel(this.context, IHaveNeverCardAPIModel(id = "2",info = "Fallen down a hill drunk"),mSwipeView,onClick));

        topHolder = view.findViewById(R.id.top)
        val image = ImageView(this.context)
        image.setImageDrawable(this.context!!.resources.getDrawable(R.drawable.ic_menu_black_24dp))
        image.setColorFilter(Color.argb(255, 255, 255, 255))
        image.isClickable = true
        image.setOnClickListener(View.OnClickListener {
            listener?.showMenu()
        })
        topHolder!!.addView(image)
        return view
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
        super.onDestroy()
    }



    companion object {

        @JvmStatic
        fun newInstance(param1: String) =
            NeverHaveIEverFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}
