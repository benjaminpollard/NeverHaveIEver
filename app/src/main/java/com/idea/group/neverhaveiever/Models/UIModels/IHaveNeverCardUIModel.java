package com.idea.group.neverhaveiever.Models.UIModels;

import android.content.Context;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.idea.group.neverhaveiever.Models.APIModels.IHaveNeverCardAPIModel;
import com.idea.group.neverhaveiever.R;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;

@NonReusable
@Layout(R.layout.never_card_view)
public class IHaveNeverCardUIModel {

    @View(R.id.never_card_view_info)
    public TextView infoText;

    @View(R.id.never_card_view_next)
    public Button next;

    public IHaveNeverCardAPIModel mProfile;
    public Context mContext;
    public SwipePlaceHolderView mSwipeView;
    android.view.View.OnClickListener onNext;

    public IHaveNeverCardUIModel(Context context, IHaveNeverCardAPIModel profile, SwipePlaceHolderView swipeView,android.view.View.OnClickListener onNext ) {
        mContext = context;
        mProfile = profile;
        mSwipeView = swipeView;
        this.onNext = onNext;

    }

    @Resolve
    public void onResolved()
    {
        next.setOnClickListener(onNext);
        infoText.setText(mProfile.getInfo());
    }

    @SwipeOut
    public void onSwipedOut(){
        Log.d("EVENT", "onSwipedOut");
       // mSwipeView.addView(this);
    }

    @SwipeCancelState
    public void onSwipeCancelState(){
        Log.d("EVENT", "onSwipeCancelState");
    }

    @SwipeIn
    public void onSwipeIn(){
        Log.d("EVENT", "onSwipedIn");
    }

    @SwipeInState
    public void onSwipeInState(){
        Log.d("EVENT", "onSwipeInState");
    }

    @SwipeOutState
    public void onSwipeOutState(){
        Log.d("EVENT", "onSwipeOutState");
    }
}