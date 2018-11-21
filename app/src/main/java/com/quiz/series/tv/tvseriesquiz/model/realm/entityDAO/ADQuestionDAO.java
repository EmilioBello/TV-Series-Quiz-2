package com.quiz.series.tv.tvseriesquiz.model.realm.entityDAO;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import lombok.Data;

@Data
@RealmClass
public class ADQuestionDAO implements RealmModel{

    @PrimaryKey
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

    private int serie_code;
}
