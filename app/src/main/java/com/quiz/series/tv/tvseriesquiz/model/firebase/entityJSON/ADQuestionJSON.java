package com.quiz.series.tv.tvseriesquiz.model.firebase.entityJSON;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ADQuestionJSON {

    private int code;
    private int active;
    private long updatedAt;

    private String question;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private int correct;
    private int season;
    private int episode;
    private String language;
    private int serie_code;
}
