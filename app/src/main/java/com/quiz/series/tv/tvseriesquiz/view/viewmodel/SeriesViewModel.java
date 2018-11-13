package com.quiz.series.tv.tvseriesquiz.view.viewmodel;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.quiz.series.tv.tvseriesquiz.MyApp;
import com.quiz.series.tv.tvseriesquiz.model.POJO.ADSerie;

import java.io.Serializable;
import java.util.ArrayList;

import lombok.Data;

@Data
public class SeriesViewModel implements Serializable {

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

    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(MyApp.getContext())
                .load(imageUrl)
                .into(view);
    }

    public static SeriesViewModel converterEntitytoViewModel(ADSerie serie) {
        SeriesViewModel viewModel = new SeriesViewModel();

        viewModel.setCode(serie.getCode());
        viewModel.setName(serie.getName());

        viewModel.setSeason(serie.getSeason());
        viewModel.setTotalEpisodes(serie.getTotalEpisodes());

        viewModel.setSeasonProgress(serie.getSeasonProgress());
        viewModel.setEpisodeProgress(serie.getEpisodeProgress());

        viewModel.setDownloaded(serie.isDownloaded());

        viewModel.setListEpisode(serie.getListEpisode());

        viewModel.setComplete(serie.isComplete());
        viewModel.setStatus(serie.getStatus());

        viewModel.setUrlAvatar(serie.getUrlAvatar());
        viewModel.setUrlImageBackground(serie.getUrlImageBackground());

        return viewModel;
    }
}
