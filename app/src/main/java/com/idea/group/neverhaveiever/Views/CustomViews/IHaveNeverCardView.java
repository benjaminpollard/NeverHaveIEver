package com.idea.group.neverhaveiever.Views.CustomViews;

import android.content.Context;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.idea.group.neverhaveiever.Models.APIModels.IHaveNeverCardAPIModel;
import com.idea.group.neverhaveiever.R;
import com.idea.group.neverhaveiever.Views.Interfaces.IOnCardSwipe;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Position;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;

@NonReusable
@Layout(R.layout.never_card_view)
public class IHaveNeverCardView {

    @View(R.id.never_card_view_info)
     TextView infoText;

    @View(R.id.never_card_view_next)
     Button next;

    public IHaveNeverCardAPIModel mProfile;
    public Context mContext;
    public SwipePlaceHolderView mSwipeView;
    android.view.View.OnClickListener onNext;
    IOnCardSwipe OnCardSwipe;
    @Position
    public int position;

    public IHaveNeverCardView(Context context, IHaveNeverCardAPIModel profile, SwipePlaceHolderView swipeView, android.view.View.OnClickListener onNext , IOnCardSwipe OnCardSwipe) {
        mContext = context;
        mProfile = profile;
        mSwipeView = swipeView;
        this.onNext = onNext;
        this.OnCardSwipe = OnCardSwipe;

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
        OnCardSwipe.OnSwipe(position);
    }

    @SwipeCancelState
    public void onSwipeCancelState(){
        Log.d("EVENT", "onSwipeCancelState");
    }

    @SwipeIn
    public void onSwipeIn(){
        Log.d("EVENT", "onSwipedIn");
        OnCardSwipe.OnSwipe(position);
    }

    @SwipeInState
    public void onSwipeInState(){
        Log.d("EVENT", "onSwipeInState");
    }

    @SwipeOutState
    public void onSwipeOutState(){
        OnCardSwipe.OnBadSwipe(position);
        Log.d("EVENT", "onSwipeOutState");
    }
}