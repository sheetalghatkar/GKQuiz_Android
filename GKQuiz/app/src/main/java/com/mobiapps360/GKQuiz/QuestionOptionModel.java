package com.mobiapps360.GKQuiz;

public class QuestionOptionModel {
     String optionStr;
     int optionStatus;

    public String getOptionStr() {
        return optionStr;
    }

    public void setOptionStr(String optionStr) {
        this.optionStr = optionStr;
    }

    public QuestionOptionModel(String optionStr, int optionStatus) {
        this.optionStr = optionStr;
        this.optionStatus = optionStatus;
    }
}
