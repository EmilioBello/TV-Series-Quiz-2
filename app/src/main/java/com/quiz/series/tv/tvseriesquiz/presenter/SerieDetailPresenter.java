package com.quiz.series.tv.tvseriesquiz.presenter;

import com.quiz.series.tv.tvseriesquiz.utils.ADConstants;
import com.quiz.series.tv.tvseriesquiz.view.activity.SeriesDetail;
import com.quiz.series.tv.tvseriesquiz.view.viewmodel.SeriesViewModel;

public class SerieDetailPresenter implements Presenter {

    private final SeriesDetail view;

    public SerieDetailPresenter(SeriesDetail view) {
        this.view = view;
    }

    @Override
    public void init() {
        final SeriesViewModel viewModel = (SeriesViewModel) view.getIntent().getSerializableExtra(ADConstants.serie);
        viewModel.loadImage(view.getIvImageBackground(), viewModel.getUrlImageBackground());
        viewModel.loadImage(view.getIvAvatar(), viewModel.getUrlAvatar());

        view.getPbSeason().setMax(viewModel.getSeason());
        view.getPbSeason().setProgress(viewModel.getSeasonProgress());

        view.getPbEpisode().setMax(viewModel.getTotalEpisodes());
        view.getPbEpisode().setProgress(viewModel.getEpisodeProgress());
    }
}
