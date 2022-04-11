package com.mobiapps360.GKQuiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.ViewHolder> {
    ArrayList<QuestionItem> listQuestionItem;
    private Context context;

    public QuizAdapter(Context context) {
        this.context = context;
    }

    public QuizAdapter(ArrayList<QuestionItem> listQuestionItemData) {
        this.listQuestionItem = listQuestionItemData;
    }

    public void setListMenuItem(ArrayList<QuestionItem> listQuestionItemData) {
        this.listQuestionItem = listQuestionItemData;
        notifyDataSetChanged();
    }

    @Override
    public QuizAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View listItem = inflater.inflate(R.layout.quiz_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull QuizAdapter.ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        final QuestionItem questionItemModel = listQuestionItem.get(position);
        holder.txtViewQuestion.setText(questionItemModel.getQuestion());

        ArrayList<QuestionOptionModel> listQuestionOptions = questionItemModel.getArrayOption();
        holder.btnOption1.setText(listQuestionOptions.get(0).getOptionStr());
        holder.btnOption2.setText(listQuestionOptions.get(1).getOptionStr());
        holder.btnOption3.setText(listQuestionOptions.get(2).getOptionStr());
        holder.btnOption4.setText(listQuestionOptions.get(3).getOptionStr());
    }

    @Override
    public int getItemCount() {
        return listQuestionItem.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtViewQuestion;
        public Button btnOption1;
        public Button btnOption2;
        public Button btnOption3;
        public Button btnOption4;

        public ImageButton imgBtnStatus1;
        public ImageButton imgBtnStatus2;
        public ImageButton imgBtnStatus3;
        public ImageButton imgBtnStatus4;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.txtViewQuestion = itemView.findViewById(R.id.txtViewQuestion);
            this.btnOption1 = itemView.findViewById(R.id.btnOption1);
            this.btnOption2 = itemView.findViewById(R.id.btnOption2);
            this.btnOption3 = itemView.findViewById(R.id.btnOption3);
            this.btnOption4 = itemView.findViewById(R.id.btnOption4);

            this.imgBtnStatus1 = itemView.findViewById(R.id.imgBtnStatus1);
            this.imgBtnStatus2 = itemView.findViewById(R.id.imgBtnStatus2);
            this.imgBtnStatus3 = itemView.findViewById(R.id.imgBtnStatus3);
            this.imgBtnStatus4 = itemView.findViewById(R.id.imgBtnStatus4);
            //------------------------------------------


            btnOption1.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    System.out.println("Basic Gk clicked.");
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN: {
                            ((Button) v).setAlpha((float) 0.5);
                            imgBtnStatus1.setAlpha((float) 0.5);
                            break;
                        }
                        case MotionEvent.ACTION_UP: {
                            ((Button) v).setAlpha((float) 1.0);
                            imgBtnStatus1.setAlpha((float) 1.0);
                            imgBtnStatus1.setVisibility(View.VISIBLE);
                           // btnImgHomeSound.setImageResource(R.mipmap.sound_off);
                        }
                    }
                    return true;
                }
            });

            btnOption2.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    System.out.println("Basic Gk clicked.");
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN: {
                            ((Button) v).setAlpha((float) 0.5);
                            imgBtnStatus2.setAlpha((float) 0.5);
                            break;
                        }
                        case MotionEvent.ACTION_UP: {
                            ((Button) v).setAlpha((float) 1.0);
                            imgBtnStatus2.setAlpha((float) 1.0);
                            imgBtnStatus2.setVisibility(View.VISIBLE);
                        }
                    }
                    return true;
                }
            });

            btnOption3.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    System.out.println("Basic Gk clicked.");
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN: {
                            ((Button) v).setAlpha((float) 0.5);
                            imgBtnStatus3.setAlpha((float) 0.5);
                            break;
                        }
                        case MotionEvent.ACTION_UP: {
                            ((Button) v).setAlpha((float) 1.0);
                            imgBtnStatus3.setAlpha((float) 1.0);
                            imgBtnStatus3.setVisibility(View.VISIBLE);
                        }
                    }
                    return true;
                }
            });

            btnOption4.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    System.out.println("Basic Gk clicked.");
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN: {
                            ((Button) v).setAlpha((float) 0.5);
                            imgBtnStatus4.setAlpha((float) 0.5);
                            break;
                        }
                        case MotionEvent.ACTION_UP: {
                            ((Button) v).setAlpha((float) 1.0);
                            imgBtnStatus4.setAlpha((float) 1.0);
                            imgBtnStatus4.setVisibility(View.VISIBLE);
                        }
                    }
                    return true;
                }
            });

            imgBtnStatus1.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    System.out.println("Basic Gk clicked.");
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN: {
                            ((ImageButton) v).setAlpha((float) 0.5);
                            btnOption1.setAlpha((float) 0.5);
                            break;
                        }
                        case MotionEvent.ACTION_UP: {
                            ((ImageButton) v).setAlpha((float) 1.0);
                            btnOption1.setAlpha((float) 1.0);
                            imgBtnStatus1.setVisibility(View.VISIBLE);
                            // btnImgHomeSound.setImageResource(R.mipmap.sound_off);
                        }
                    }
                    return true;
                }
            });

            imgBtnStatus2.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    System.out.println("Basic Gk clicked.");
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN: {
                            ((ImageButton) v).setAlpha((float) 0.5);
                            btnOption2.setAlpha((float) 0.5);
                            break;
                        }
                        case MotionEvent.ACTION_UP: {
                            ((ImageButton) v).setAlpha((float) 1.0);
                            btnOption2.setAlpha((float) 1.0);
                            imgBtnStatus2.setVisibility(View.VISIBLE);
                        }
                    }
                    return true;
                }
            });

            imgBtnStatus3.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    System.out.println("Basic Gk clicked.");
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN: {
                            ((ImageButton) v).setAlpha((float) 0.5);
                            btnOption3.setAlpha((float) 0.5);
                            break;
                        }
                        case MotionEvent.ACTION_UP: {
                            ((ImageButton) v).setAlpha((float) 1.0);
                            btnOption3.setAlpha((float) 1.0);
                            imgBtnStatus3.setVisibility(View.VISIBLE);
                        }
                    }
                    return true;
                }
            });

            imgBtnStatus4.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    System.out.println("Basic Gk clicked.");
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN: {
                            ((ImageButton) v).setAlpha((float) 0.5);
                            btnOption4.setAlpha((float) 0.5);
                            break;
                        }
                        case MotionEvent.ACTION_UP: {
                            ((ImageButton) v).setAlpha((float) 1.0);
                            btnOption4.setAlpha((float) 1.0);
                            imgBtnStatus4.setVisibility(View.VISIBLE);
                        }
                    }
                    return true;
                }
            });
        }
    }
}

