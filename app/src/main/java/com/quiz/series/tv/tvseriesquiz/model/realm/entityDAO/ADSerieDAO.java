package com.quiz.series.tv.tvseriesquiz.model.realm.entityDAO;

import java.util.ArrayList;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import lombok.Data;

@Data
@RealmClass
public class ADSerieDAO implements RealmModel{

    @PrimaryKey
    private int code;

    private String name;

    private int season;
    private int totalEpisodes;

    private int seasonProgress;
    private int episodeProgress;

    private boolean downloaded;

    private ArrayList<Integer> listEpisode;

    private boolean complete;
    private int status;

    private String urlAvatar;
    private String urlImageBackground;
}
