package com.idea.group.neverhaveiever.Views

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.facebook.ads.AdSettings
import com.facebook.ads.AdSize
import com.facebook.ads.AdView
import com.idea.group.neverhaveiever.R
import com.idea.group.neverhaveiever.Views.Interfaces.IMenuHost

class CategorySelectorFragment : Fragment() {

    private var listener: IMenuHost? = null
    private var adView: AdView? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(
            R.layout.fragment_category_selector,
            container, false
        )

        val cleanButton : Button = view.findViewById(R.id.category_selector_clean)
        cleanButton.setOnClickListener {
            listener!!.GoToIHaveNeverScreen(NeverHaveIEverFragment.CARDS_CLEAN)
        }

        val exposedButton : Button = view.findViewById(R.id.category_selector_exposed)
        exposedButton.setOnClickListener {
            listener!!.GoToIHaveNeverScreen(NeverHaveIEverFragment.CARDS_EXPOSED)
        }

        val couplesButton : Button = view.findViewById(R.id.category_selector_couples)
        couplesButton.setOnClickListener {
            listener!!.GoToIHaveNeverScreen(NeverHaveIEverFragment.CARDS_COUPLES)
        }

        val naughtyButton : Button = view.findViewById(R.id.category_selector_naughty)
        naughtyButton.setOnClickListener {
            listener!!.GoToIHaveNeverScreen(NeverHaveIEverFragment.CARDS_NAUGHTY)
        }

        AdSettings.addTestDevice("HASHED ID")

        val adContainer = view.findViewById(R.id.banner_container) as LinearLayout

        adView = AdView(this.context, "638417063572474_638420086905505", AdSize.BANNER_HEIGHT_50)
        adContainer.addView(adView)

        adView!!.loadAd()

        return view
    }

    override fun onDestroy() {
        if (adView != null) {
            adView!!.destroy()
        }
        super.onDestroy()
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

    companion object {

        @JvmStatic
        fun newInstance() =
            CategorySelectorFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}
