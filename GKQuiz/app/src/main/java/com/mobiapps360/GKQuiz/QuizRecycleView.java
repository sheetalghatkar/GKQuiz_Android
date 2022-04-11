package com.mobiapps360.GKQuiz;
import android.content.Context;
import android.util.AttributeSet;
import androidx.recyclerview.widget.RecyclerView;

public class QuizRecycleView extends RecyclerView {

    Context context;

    public QuizRecycleView(Context context) {
        super(context);
        this.context = context;
    }

    public QuizRecycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public QuizRecycleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean fling(int velocityX, int velocityY) {

        // velocityY *= 0.7;
        velocityX *= 0.1;// for Horizontal recycler view. comment velocityY line not require for Horizontal Mode.

        return super.fling(velocityX, velocityY);
    }

}