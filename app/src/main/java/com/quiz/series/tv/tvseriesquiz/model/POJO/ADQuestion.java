package com.quiz.series.tv.tvseriesquiz.model.POJO;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Emilio on 11/02/2016.
 */

@Data
@EqualsAndHashCode(callSuper=false)
public class ADQuestion{
    private int code;

    private String question;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private int correct;

    private int season;
    private int episode;

    private String language;
    
    private int serieCode;
}
