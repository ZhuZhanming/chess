package com.zzming.chess.message;

import java.io.Serializable;

public class StepMessage implements Serializable {
    private static final long serialVersionUID = -3419714012470352792L;
    private String stepStr;

    public StepMessage(String stepStr) {
        this.stepStr = stepStr;
    }

    public String getStepStr() {
        return stepStr;
    }

}
