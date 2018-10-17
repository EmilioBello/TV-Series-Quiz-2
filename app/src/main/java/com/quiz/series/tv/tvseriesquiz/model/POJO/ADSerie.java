package com.quiz.series.tv.tvseriesquiz.model.POJO;

import java.util.ArrayList;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Emilio on 11/02/2016.
 */

@Data
@Builder
@EqualsAndHashCode(callSuper=false)
public class ADSerie {

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
