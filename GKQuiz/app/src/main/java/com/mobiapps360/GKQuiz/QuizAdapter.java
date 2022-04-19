package com.mobiapps360.GKQuiz;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.ViewHolder> {
    ArrayList<QuestionItem> listQuestionItem;
    private Context context;
    QuestionItem questionItemModel;
    ArrayList<QuestionOptionModel> listQuestionOptions;
    int cardIndex = 0;
    Handler handler;

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
        questionItemModel = listQuestionItem.get(position);
        holder.quizCardNumber.setText((position + 1) + "/" + listQuestionItem.size());
        holder.txtViewQuestion.setText(questionItemModel.getQuestion());
        listQuestionOptions = questionItemModel.getArrayOption();
        holder.btnOption1.setText(listQuestionOptions.get(0).getOptionStr());
        holder.btnOption2.setText(listQuestionOptions.get(1).getOptionStr());
        if (listQuestionOptions.size() > 2) {
            holder.btnOption3.setText(listQuestionOptions.get(2).getOptionStr());
        }
        if (listQuestionOptions.size() > 3) {
            holder.btnOption4.setText(listQuestionOptions.get(3).getOptionStr());
        }


        holder.imgBtnStatus1.setAlpha((float) 1.0);
        holder.imgBtnStatus2.setAlpha((float) 1.0);
        holder.imgBtnStatus3.setAlpha((float) 1.0);
        holder.imgBtnStatus4.setAlpha((float) 1.0);
        holder.btnOption1.setAlpha((float) 1.0);
        holder.btnOption2.setAlpha((float) 1.0);
        holder.btnOption3.setAlpha((float) 1.0);
        holder.btnOption4.setAlpha((float) 1.0);
        if (questionItemModel.isReadOnly()) {
            holder.btnOption1.setEnabled(false);
            holder.btnOption2.setEnabled(false);
            holder.btnOption3.setEnabled(false);
            holder.btnOption4.setEnabled(false);
        } else {
            holder.btnOption1.setEnabled(true);
            holder.btnOption2.setEnabled(true);
            holder.btnOption3.setEnabled(true);
            holder.btnOption4.setEnabled(true);
        }

        if (questionItemModel.getAnswer() == 0) {
            holder.imgBtnStatus1.setImageResource(R.mipmap.right_sign);
            holder.imgBtnStatus2.setImageResource(R.mipmap.wrong_sign);
            holder.imgBtnStatus3.setImageResource(R.mipmap.wrong_sign);
            holder.imgBtnStatus4.setImageResource(R.mipmap.wrong_sign);

        } else if (questionItemModel.getAnswer() == 1) {
            holder.imgBtnStatus1.setImageResource(R.mipmap.wrong_sign);
            holder.imgBtnStatus2.setImageResource(R.mipmap.right_sign);
            holder.imgBtnStatus3.setImageResource(R.mipmap.wrong_sign);
            holder.imgBtnStatus4.setImageResource(R.mipmap.wrong_sign);
        } else if (questionItemModel.getAnswer() == 2) {
            holder.imgBtnStatus1.setImageResource(R.mipmap.wrong_sign);
            holder.imgBtnStatus2.setImageResource(R.mipmap.wrong_sign);
            holder.imgBtnStatus3.setImageResource(R.mipmap.right_sign);
            holder.imgBtnStatus4.setImageResource(R.mipmap.wrong_sign);
        } else {
            holder.imgBtnStatus1.setImageResource(R.mipmap.wrong_sign);
            holder.imgBtnStatus2.setImageResource(R.mipmap.wrong_sign);
            holder.imgBtnStatus3.setImageResource(R.mipmap.wrong_sign);
            holder.imgBtnStatus4.setImageResource(R.mipmap.right_sign);
        }

        if ((listQuestionOptions.get(0).getOptionStatus()) == -1) {
            holder.imgBtnStatus1.setVisibility(View.INVISIBLE);
        } else {
            holder.imgBtnStatus1.setVisibility(View.VISIBLE);

        }

        if ((listQuestionOptions.get(1).getOptionStatus()) == -1) {
            holder.imgBtnStatus2.setVisibility(View.INVISIBLE);
        } else {
            holder.imgBtnStatus2.setVisibility(View.VISIBLE);
        }

        //To check whether 3 option is available or not
        if (listQuestionOptions.size() > 2) {
            holder.imgViewD.setVisibility(View.VISIBLE);
            holder.btnOption3.setVisibility(View.VISIBLE);
            if ((listQuestionOptions.get(2).getOptionStatus()) == -1) {
                holder.imgBtnStatus3.setVisibility(View.INVISIBLE);
            } else {
                holder.imgBtnStatus3.setVisibility(View.VISIBLE);
            }
        } else {
            holder.imgBtnStatus3.setVisibility(View.INVISIBLE);
            holder.imgViewC.setVisibility(View.INVISIBLE);
            holder.btnOption3.setVisibility(View.INVISIBLE);
        }

        //To check whether 4 option is available or not
        if (listQuestionOptions.size() > 3) {
            holder.imgViewD.setVisibility(View.VISIBLE);
            holder.btnOption4.setVisibility(View.VISIBLE);
            if ((listQuestionOptions.get(3).getOptionStatus()) == -1) {
                holder.imgBtnStatus4.setVisibility(View.INVISIBLE);
            } else {
                holder.imgBtnStatus4.setVisibility(View.VISIBLE);
//                if ((listQuestionOptions.get(3).getOptionStatus()) == 1) {
//                    holder.btnOption4.setTextColor(context.getResources().getColor(R.color.green_ans));
//                } else {
//                    holder.btnOption4.setTextColor(context.getResources().getColor(R.color.red_done));
//                }

            }
        } else {
            holder.imgBtnStatus4.setVisibility(View.INVISIBLE);
            holder.imgViewD.setVisibility(View.INVISIBLE);
            holder.btnOption4.setVisibility(View.INVISIBLE);
        }


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

        ImageView imgViewA;
        ImageView imgViewB;
        ImageView imgViewC;
        ImageView imgViewD;

        TextView quizCardNumber;

        LinearLayout linearLayout1;
        LinearLayout linearLayout2;
        LinearLayout linearLayout3;
        LinearLayout linearLayout4;


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

            this.imgViewA = itemView.findViewById(R.id.imgViewA);
            this.imgViewB = itemView.findViewById(R.id.imgViewB);
            this.imgViewC = itemView.findViewById(R.id.imgViewC);
            this.imgViewD = itemView.findViewById(R.id.imgViewD);

            this.linearLayout1 = itemView.findViewById(R.id.linearLayout1);
            this.linearLayout2 = itemView.findViewById(R.id.linearLayout2);
            this.linearLayout3 = itemView.findViewById(R.id.linearLayout3);
            this.linearLayout4 = itemView.findViewById(R.id.linearLayout4);

            this.quizCardNumber = itemView.findViewById(R.id.quizCardNumber);
            //------------------------------------------

            QuizActivity quizActivity = (QuizActivity) context;
            //------------------------------------------


            linearLayout1.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
//                    System.out.println("Basic Gk clicked.");
                    imgBtnStatus1.setVisibility(View.VISIBLE);
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN: {
                            btnOption1.setAlpha((float) 0.5);
                            imgBtnStatus1.setAlpha((float) 0.5);
                            handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        btnOption1.setAlpha((float) 1.0);
                                        imgBtnStatus1.setAlpha((float) 1.0);
                                    } catch (Exception e) {

                                    }
                                }
                            }, 500);
                            break;
                        }
                        case MotionEvent.ACTION_UP: {
                            handler.removeCallbacksAndMessages(null);
                            btnOption1.setAlpha((float) 1.0);
                            imgBtnStatus1.setAlpha((float) 1.0);
                            int setOptionStatus = 0;
                            if (questionItemModel.getAnswer() == 0) {
                                setOptionStatus = 1;
                            }
                            QuestionItem questionItemModelTemp;
                            questionItemModelTemp = listQuestionItem.get(getBindingAdapterPosition());
                          //  System.out.println("--1st clicked-" + questionItemModelTemp.getQuestion() + "status" + questionItemModelTemp.getAnswer());
                            ArrayList<QuestionOptionModel> listQuestionOptionsTemp;
                            listQuestionOptionsTemp = questionItemModelTemp.getArrayOption();
                            QuestionOptionModel questionOptionModel1 = new QuestionOptionModel(listQuestionOptionsTemp.get(0).getOptionStr(), setOptionStatus);
                            listQuestionOptionsTemp.set(0, questionOptionModel1);
                            questionItemModelTemp.setArrayOption(listQuestionOptionsTemp);
                            if (questionItemModelTemp.getAnswer() == 0) {
                                questionItemModelTemp.setReadOnly(true);
                            }
                            listQuestionItem.set(getBindingAdapterPosition(), questionItemModelTemp);
                            Constant.arrayXyz.set(getBindingAdapterPosition(), questionItemModelTemp);
                            notifyItemChanged(getBindingAdapterPosition());
                            if (questionItemModelTemp.getAnswer() == 0) {
                                btnOption1.setEnabled(false);
                                btnOption2.setEnabled(false);
                                btnOption3.setEnabled(false);
                                btnOption4.setEnabled(false);
                                quizActivity.playSoundOptionClick("perfect");
                                quizActivity.reloadRecycleView(getBindingAdapterPosition(), true);
                            } else {
                                quizActivity.playSoundOptionClick("wrong_ans_sound");
                                quizActivity.reloadRecycleView(getBindingAdapterPosition(), false);
                            }
                        }
                    }
                    return true;
                }
            });

            linearLayout2.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
//                    System.out.println("Basic Gk clicked.");
                    imgBtnStatus2.setVisibility(View.VISIBLE);
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN: {
                            btnOption2.setAlpha((float) 0.5);
                            imgBtnStatus2.setAlpha((float) 0.5);
                            handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        btnOption2.setAlpha((float) 1.0);
                                        imgBtnStatus2.setAlpha((float) 1.0);
                                    } catch (Exception e) {

                                    }
                                }
                            }, 500);
                            break;
                        }
                        case MotionEvent.ACTION_UP: {
                            handler.removeCallbacksAndMessages(null);
                            btnOption2.setAlpha((float) 1.0);
                            imgBtnStatus2.setAlpha((float) 1.0);
                            int setOptionStatus = 0;
                            if (questionItemModel.getAnswer() == 1) {
                                setOptionStatus = 1;
                            }

                            QuestionItem questionItemModelTemp;
                            questionItemModelTemp = listQuestionItem.get(getBindingAdapterPosition());
                           // System.out.println("--2nd clicked-" + questionItemModelTemp.getQuestion() + "status" + questionItemModelTemp.getAnswer());
                            ArrayList<QuestionOptionModel> listQuestionOptionsTemp;
                            listQuestionOptionsTemp = questionItemModelTemp.getArrayOption();
                            QuestionOptionModel questionOptionModel1 = new QuestionOptionModel(listQuestionOptionsTemp.get(1).getOptionStr(), setOptionStatus);
                            listQuestionOptionsTemp.set(1, questionOptionModel1);
                            questionItemModelTemp.setArrayOption(listQuestionOptionsTemp);
                            if (questionItemModelTemp.getAnswer() == 1) {
                                questionItemModelTemp.setReadOnly(true);
                            }
                            listQuestionItem.set(getBindingAdapterPosition(), questionItemModelTemp);
                            Constant.arrayXyz.set(getBindingAdapterPosition(), questionItemModelTemp);
                            notifyItemChanged(getBindingAdapterPosition());
                            if (questionItemModelTemp.getAnswer() == 1) {
                                btnOption1.setEnabled(false);
                                btnOption2.setEnabled(false);
                                btnOption3.setEnabled(false);
                                btnOption4.setEnabled(false);
                                quizActivity.playSoundOptionClick("well_done");
                                quizActivity.reloadRecycleView(getBindingAdapterPosition(), true);
                            } else {
                                quizActivity.playSoundOptionClick("wrong_ans_sound");
                                quizActivity.reloadRecycleView(getBindingAdapterPosition(), false);
                            }
                        }
                    }
                    return true;
                }
            });

            linearLayout3.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
//                    System.out.println("Basic Gk clicked.");
                    imgBtnStatus3.setVisibility(View.VISIBLE);
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN: {
                            btnOption3.setAlpha((float) 0.5);
                            imgBtnStatus3.setAlpha((float) 0.5);
                            handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        btnOption3.setAlpha((float) 1.0);
                                        imgBtnStatus3.setAlpha((float) 1.0);
                                    } catch (Exception e) {

                                    }
                                }
                            }, 500);
                            break;
                        }
                        case MotionEvent.ACTION_UP: {
                            handler.removeCallbacksAndMessages(null);
                            btnOption3.setAlpha((float) 1.0);
                            imgBtnStatus3.setAlpha((float) 1.0);
                            int setOptionStatus = 0;
                            if (questionItemModel.getAnswer() == 2) {
                                setOptionStatus = 1;
                            }
                            QuestionItem questionItemModelTemp;
                            questionItemModelTemp = listQuestionItem.get(getBindingAdapterPosition());
                          //  System.out.println("--3rd clicked-" + questionItemModelTemp.getQuestion() + "status" + questionItemModelTemp.getAnswer());
                            ArrayList<QuestionOptionModel> listQuestionOptionsTemp;
                            listQuestionOptionsTemp = questionItemModelTemp.getArrayOption();
                            QuestionOptionModel questionOptionModel1 = new QuestionOptionModel(listQuestionOptionsTemp.get(2).getOptionStr(), setOptionStatus);
                            listQuestionOptionsTemp.set(2, questionOptionModel1);
                            questionItemModelTemp.setArrayOption(listQuestionOptionsTemp);
                            listQuestionItem.set(getBindingAdapterPosition(), questionItemModelTemp);
                            if (questionItemModelTemp.getAnswer() == 2) {
                                questionItemModelTemp.setReadOnly(true);
                            }
                            Constant.arrayXyz.set(getBindingAdapterPosition(), questionItemModelTemp);
                            notifyItemChanged(getBindingAdapterPosition());
                            if (questionItemModelTemp.getAnswer() == 2) {
                                btnOption1.setEnabled(false);
                                btnOption2.setEnabled(false);
                                btnOption3.setEnabled(false);
                                btnOption4.setEnabled(false);
                                quizActivity.playSoundOptionClick("great_job");
                                quizActivity.reloadRecycleView(getBindingAdapterPosition(), true);
                            } else {
                                quizActivity.playSoundOptionClick("wrong_ans_sound");
                                quizActivity.reloadRecycleView(getBindingAdapterPosition(), false);
                            }
                        }
                    }
                    return true;
                }
            });

            linearLayout4.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
//                    System.out.println("Basic Gk clicked.");
                    imgBtnStatus4.setVisibility(View.VISIBLE);
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN: {
                            btnOption4.setAlpha((float) 0.5);
                            imgBtnStatus4.setAlpha((float) 0.5);
                            handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        btnOption4.setAlpha((float) 1.0);
                                        imgBtnStatus4.setAlpha((float) 1.0);
                                    } catch (Exception e) {

                                    }
                                }
                            }, 500);
                            break;
                        }
                        case MotionEvent.ACTION_UP: {
                            handler.removeCallbacksAndMessages(null);
                            btnOption4.setAlpha((float) 1.0);
                            imgBtnStatus4.setAlpha((float) 1.0);
                            int setOptionStatus = 0;
                            if (questionItemModel.getAnswer() == 3) {
                                setOptionStatus = 1;
                            }
                            QuestionItem questionItemModelTemp;
                            questionItemModelTemp = listQuestionItem.get(getBindingAdapterPosition());
                          //  System.out.println("--4th clicked-" + questionItemModelTemp.getQuestion() + "status" + questionItemModelTemp.getAnswer());

                            ArrayList<QuestionOptionModel> listQuestionOptionsTemp;
                            listQuestionOptionsTemp = questionItemModelTemp.getArrayOption();

                            QuestionOptionModel questionOptionModel1 = new QuestionOptionModel(listQuestionOptionsTemp.get(3).getOptionStr(), setOptionStatus);
                            listQuestionOptionsTemp.set(3, questionOptionModel1);

                            questionItemModelTemp.setArrayOption(listQuestionOptionsTemp);
                            if (questionItemModelTemp.getAnswer() == 3) {
                                questionItemModelTemp.setReadOnly(true);
                            }
                            listQuestionItem.set(getBindingAdapterPosition(), questionItemModelTemp);
                            Constant.arrayXyz.set(getBindingAdapterPosition(), questionItemModelTemp);
                            notifyItemChanged(getBindingAdapterPosition());
                            if (questionItemModelTemp.getAnswer() == 3) {
                                // System.out.println("4 correct-----");
                                // ((Button) v).setTextColor(context.getResources().getColor(R.color.green_ans));
                                btnOption1.setEnabled(false);
                                btnOption2.setEnabled(false);
                                btnOption3.setEnabled(false);
                                btnOption4.setEnabled(false);
                                quizActivity.playSoundOptionClick("excellent");
                                quizActivity.reloadRecycleView(getBindingAdapterPosition(), true);
                            } else {
                                // System.out.println("4 false-----");
                                //  ((Button) v).setTextColor(context.getResources().getColor(R.color.red_done));
                                quizActivity.playSoundOptionClick("wrong_ans_sound");
                                quizActivity.reloadRecycleView(getBindingAdapterPosition(), false);
                            }
                        }
                    }
                    return true;
                }
            });

            //On click text view

            btnOption1.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
//                    System.out.println("Basic Gk clicked.");
                    imgBtnStatus1.setVisibility(View.VISIBLE);
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN: {
                            btnOption1.setAlpha((float) 0.5);
                            imgBtnStatus1.setAlpha((float) 0.5);
                            handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        btnOption1.setAlpha((float) 1.0);
                                        imgBtnStatus1.setAlpha((float) 1.0);
                                    } catch (Exception e) {

                                    }
                                }
                            }, 500);
                            break;
                        }
                        case MotionEvent.ACTION_UP: {
                            handler.removeCallbacksAndMessages(null);
                            btnOption1.setAlpha((float) 1.0);
                            imgBtnStatus1.setAlpha((float) 1.0);
                            int setOptionStatus = 0;
                            if (questionItemModel.getAnswer() == 0) {
                                setOptionStatus = 1;
                            }
                            QuestionItem questionItemModelTemp;
                            questionItemModelTemp = listQuestionItem.get(getBindingAdapterPosition());
                           // System.out.println("--1st clicked-" + questionItemModelTemp.getQuestion() + "status" + questionItemModelTemp.getAnswer());
                            ArrayList<QuestionOptionModel> listQuestionOptionsTemp;
                            listQuestionOptionsTemp = questionItemModelTemp.getArrayOption();
                            QuestionOptionModel questionOptionModel1 = new QuestionOptionModel(listQuestionOptionsTemp.get(0).getOptionStr(), setOptionStatus);
                            listQuestionOptionsTemp.set(0, questionOptionModel1);
                            questionItemModelTemp.setArrayOption(listQuestionOptionsTemp);
                            if (questionItemModelTemp.getAnswer() == 0) {
                                questionItemModelTemp.setReadOnly(true);
                            }
                            listQuestionItem.set(getBindingAdapterPosition(), questionItemModelTemp);
                            Constant.arrayXyz.set(getBindingAdapterPosition(), questionItemModelTemp);
                            notifyItemChanged(getBindingAdapterPosition());
                            if (questionItemModelTemp.getAnswer() == 0) {
                                btnOption1.setEnabled(false);
                                btnOption2.setEnabled(false);
                                btnOption3.setEnabled(false);
                                btnOption4.setEnabled(false);
                                quizActivity.playSoundOptionClick("perfect");
                                quizActivity.reloadRecycleView(getBindingAdapterPosition(), true);
                            } else {
                                quizActivity.playSoundOptionClick("wrong_ans_sound");
                                quizActivity.reloadRecycleView(getBindingAdapterPosition(), false);
                            }
                        }
                    }
                    return true;
                }
            });

            btnOption2.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
//                    System.out.println("Basic Gk clicked.");
                    imgBtnStatus2.setVisibility(View.VISIBLE);
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN: {
                            btnOption2.setAlpha((float) 0.5);
                            imgBtnStatus2.setAlpha((float) 0.5);
                            handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        btnOption2.setAlpha((float) 1.0);
                                        imgBtnStatus2.setAlpha((float) 1.0);
                                    } catch (Exception e) {

                                    }
                                }
                            }, 500);
                            break;
                        }
                        case MotionEvent.ACTION_UP: {
                            handler.removeCallbacksAndMessages(null);
                            btnOption2.setAlpha((float) 1.0);
                            imgBtnStatus2.setAlpha((float) 1.0);
                            int setOptionStatus = 0;
                            if (questionItemModel.getAnswer() == 1) {
                                setOptionStatus = 1;
                            }

                            QuestionItem questionItemModelTemp;
                            questionItemModelTemp = listQuestionItem.get(getBindingAdapterPosition());
                        //    System.out.println("--2nd clicked-" + questionItemModelTemp.getQuestion() + "status" + questionItemModelTemp.getAnswer());
                            ArrayList<QuestionOptionModel> listQuestionOptionsTemp;
                            listQuestionOptionsTemp = questionItemModelTemp.getArrayOption();
                            QuestionOptionModel questionOptionModel1 = new QuestionOptionModel(listQuestionOptionsTemp.get(1).getOptionStr(), setOptionStatus);
                            listQuestionOptionsTemp.set(1, questionOptionModel1);
                            questionItemModelTemp.setArrayOption(listQuestionOptionsTemp);
                            if (questionItemModelTemp.getAnswer() == 1) {
                                questionItemModelTemp.setReadOnly(true);
                            }
                            listQuestionItem.set(getBindingAdapterPosition(), questionItemModelTemp);
                            Constant.arrayXyz.set(getBindingAdapterPosition(), questionItemModelTemp);
                            notifyItemChanged(getBindingAdapterPosition());
                            if (questionItemModelTemp.getAnswer() == 1) {
                                btnOption1.setEnabled(false);
                                btnOption2.setEnabled(false);
                                btnOption3.setEnabled(false);
                                btnOption4.setEnabled(false);
                                quizActivity.playSoundOptionClick("well_done");
                                quizActivity.reloadRecycleView(getBindingAdapterPosition(), true);
                            } else {
                                quizActivity.playSoundOptionClick("wrong_ans_sound");
                                quizActivity.reloadRecycleView(getBindingAdapterPosition(), false);
                            }
                        }
                    }
                    return true;
                }
            });

            btnOption3.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
//                    System.out.println("Basic Gk clicked.");
                    imgBtnStatus3.setVisibility(View.VISIBLE);
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN: {
                            btnOption3.setAlpha((float) 0.5);
                            imgBtnStatus3.setAlpha((float) 0.5);
                            handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        btnOption3.setAlpha((float) 1.0);
                                        imgBtnStatus3.setAlpha((float) 1.0);
                                    } catch (Exception e) {

                                    }
                                }
                            }, 500);
                            break;
                        }
                        case MotionEvent.ACTION_UP: {
                            handler.removeCallbacksAndMessages(null);
                            btnOption3.setAlpha((float) 1.0);
                            imgBtnStatus3.setAlpha((float) 1.0);
                            int setOptionStatus = 0;
                            if (questionItemModel.getAnswer() == 2) {
                                setOptionStatus = 1;
                            }
                            QuestionItem questionItemModelTemp;
                            questionItemModelTemp = listQuestionItem.get(getBindingAdapterPosition());
                         //   System.out.println("--3rd clicked-" + questionItemModelTemp.getQuestion() + "status" + questionItemModelTemp.getAnswer());
                            ArrayList<QuestionOptionModel> listQuestionOptionsTemp;
                            listQuestionOptionsTemp = questionItemModelTemp.getArrayOption();
                            QuestionOptionModel questionOptionModel1 = new QuestionOptionModel(listQuestionOptionsTemp.get(2).getOptionStr(), setOptionStatus);
                            listQuestionOptionsTemp.set(2, questionOptionModel1);
                            questionItemModelTemp.setArrayOption(listQuestionOptionsTemp);
                            listQuestionItem.set(getBindingAdapterPosition(), questionItemModelTemp);
                            if (questionItemModelTemp.getAnswer() == 2) {
                                questionItemModelTemp.setReadOnly(true);
                            }
                            Constant.arrayXyz.set(getBindingAdapterPosition(), questionItemModelTemp);
                            notifyItemChanged(getBindingAdapterPosition());
                            if (questionItemModelTemp.getAnswer() == 2) {
                                btnOption1.setEnabled(false);
                                btnOption2.setEnabled(false);
                                btnOption3.setEnabled(false);
                                btnOption4.setEnabled(false);
                                quizActivity.playSoundOptionClick("great_job");
                                quizActivity.reloadRecycleView(getBindingAdapterPosition(), true);
                            } else {
                                quizActivity.playSoundOptionClick("wrong_ans_sound");
                                quizActivity.reloadRecycleView(getBindingAdapterPosition(), false);
                            }
                        }
                    }
                    return true;
                }
            });

            btnOption4.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
//                    System.out.println("Basic Gk clicked.");
                    imgBtnStatus4.setVisibility(View.VISIBLE);
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN: {
                            btnOption4.setAlpha((float) 0.5);
                            imgBtnStatus4.setAlpha((float) 0.5);
                            handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        btnOption4.setAlpha((float) 1.0);
                                        imgBtnStatus4.setAlpha((float) 1.0);
                                    } catch (Exception e) {

                                    }
                                }
                            }, 500);
                            break;
                        }
                        case MotionEvent.ACTION_UP: {
                            handler.removeCallbacksAndMessages(null);
                            btnOption4.setAlpha((float) 1.0);
                            imgBtnStatus4.setAlpha((float) 1.0);
                            int setOptionStatus = 0;
                            if (questionItemModel.getAnswer() == 3) {
                                setOptionStatus = 1;
                            }
                            QuestionItem questionItemModelTemp;
                            questionItemModelTemp = listQuestionItem.get(getBindingAdapterPosition());
                           // System.out.println("--4th clicked-" + questionItemModelTemp.getQuestion() + "status" + questionItemModelTemp.getAnswer());

                            ArrayList<QuestionOptionModel> listQuestionOptionsTemp;
                            listQuestionOptionsTemp = questionItemModelTemp.getArrayOption();

                            QuestionOptionModel questionOptionModel1 = new QuestionOptionModel(listQuestionOptionsTemp.get(3).getOptionStr(), setOptionStatus);
                            listQuestionOptionsTemp.set(3, questionOptionModel1);

                            questionItemModelTemp.setArrayOption(listQuestionOptionsTemp);
                            if (questionItemModelTemp.getAnswer() == 3) {
                                questionItemModelTemp.setReadOnly(true);
                            }
                            listQuestionItem.set(getBindingAdapterPosition(), questionItemModelTemp);
                            Constant.arrayXyz.set(getBindingAdapterPosition(), questionItemModelTemp);
                            notifyItemChanged(getBindingAdapterPosition());
                            if (questionItemModelTemp.getAnswer() == 3) {
                                // System.out.println("4 correct-----");
                                // ((Button) v).setTextColor(context.getResources().getColor(R.color.green_ans));
                                btnOption1.setEnabled(false);
                                btnOption2.setEnabled(false);
                                btnOption3.setEnabled(false);
                                btnOption4.setEnabled(false);
                                quizActivity.playSoundOptionClick("excellent");
                                quizActivity.reloadRecycleView(getBindingAdapterPosition(), true);
                            } else {
                                // System.out.println("4 false-----");
                                //  ((Button) v).setTextColor(context.getResources().getColor(R.color.red_done));
                                quizActivity.playSoundOptionClick("wrong_ans_sound");
                                quizActivity.reloadRecycleView(getBindingAdapterPosition(), false);
                            }
                        }
                    }
                    return true;
                }
            });
        }
    }
}