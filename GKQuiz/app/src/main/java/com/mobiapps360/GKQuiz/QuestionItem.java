package com.mobiapps360.GKQuiz;
import java.util.ArrayList;
import java.util.Map;

public class QuestionItem {
    String question;
    public ArrayList<QuestionOptionModel> arrayOption;
    int answer;
    boolean isReadOnly;

    public boolean isReadOnly() {
        return isReadOnly;
    }

    public void setReadOnly(boolean readOnly) {
        isReadOnly = readOnly;
    }

    public QuestionItem(String question, ArrayList<QuestionOptionModel> arrayOption, int answer, boolean isReadOnly) {
        this.question = question;
        this.arrayOption = arrayOption;
        this.answer = answer;
        this.isReadOnly = isReadOnly;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<QuestionOptionModel> getArrayOption() {
        return arrayOption;
    }

    public void setArrayOption(ArrayList<QuestionOptionModel> arrayOption) {
        this.arrayOption = arrayOption;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }
}