package com.mobiapps360.GKQuiz;

import androidx.recyclerview.widget.RecyclerView;;


/**
 * Created by Dajavu on 16/8/18.
 */
public class CenterScrollListener extends RecyclerView.OnScrollListener{
    private boolean mAutoSet = false;



    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if(!(layoutManager instanceof CircleLayoutManager)){
            mAutoSet = true;
            return;
        }

        if(!mAutoSet){
            if(newState == RecyclerView.SCROLL_STATE_IDLE){
                int dx = 0;
                if(layoutManager instanceof CircleLayoutManager){
                    dx = ((CircleLayoutManager) layoutManager).getOffsetCenterView();
                }
               // System.out.println("print dx****"+dx);
                recyclerView.smoothScrollBy(dx,0);
            }
            mAutoSet = true;
        }
        if(newState == RecyclerView.SCROLL_STATE_DRAGGING || newState == RecyclerView.SCROLL_STATE_SETTLING){
            mAutoSet = false;
        }
    }
}
