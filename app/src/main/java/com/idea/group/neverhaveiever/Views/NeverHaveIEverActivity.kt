package com.idea.group.neverhaveiever.Views

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.facebook.ads.AdSettings
import com.facebook.ads.AdSize
import com.facebook.ads.AdView
import com.idea.group.neverhaveiever.Models.APIModels.IHaveNeverCardAPIModel
import com.idea.group.neverhaveiever.Models.UIModels.IHaveNeverCardUIModel
import com.idea.group.neverhaveiever.R
import com.mindorks.placeholderview.SwipeDecor
import com.mindorks.placeholderview.SwipePlaceHolderView
import com.mindorks.placeholderview.SwipeViewBuilder


class NeverHaveIEverActivity : AppCompatActivity()
{

    private var mSwipeView: SwipePlaceHolderView? = null
    private var adView: AdView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_never_have_i_ever)
        AdSettings.addTestDevice("HASHED ID")

        // Find the Ad Container
        val adContainer = findViewById(R.id.banner_container) as LinearLayout
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        // Add the ad view to your activity layout
        adView = AdView(this, "638417063572474_638420086905505", AdSize.BANNER_HEIGHT_50)
        adContainer.addView(adView)

        adView!!.loadAd()
        mSwipeView = findViewById(R.id.swipeView);
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
        mSwipeView!!.addView( IHaveNeverCardUIModel(this, IHaveNeverCardAPIModel(id = "1",info = "Had Sex on a Boat" ),mSwipeView,onClick));
        mSwipeView!!.addView( IHaveNeverCardUIModel(this, IHaveNeverCardAPIModel(id = "2",info = "Fallen down a hill drunk"),mSwipeView,onClick));


    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)


    }

    override fun onDestroy() {
        if (adView != null) {
            adView!!.destroy()
        }
        super.onDestroy()
    }
}