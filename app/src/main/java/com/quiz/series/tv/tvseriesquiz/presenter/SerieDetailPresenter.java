package com.quiz.series.tv.tvseriesquiz.presenter;

import android.content.Intent;
import android.view.View;

import com.quiz.series.tv.tvseriesquiz.MyApp;
import com.quiz.series.tv.tvseriesquiz.model.schema.ADQuestionSchema;
import com.quiz.series.tv.tvseriesquiz.presenter.interactor.getFromFirebase.SyncronizeInterface;
import com.quiz.series.tv.tvseriesquiz.presenter.interactor.getFromFirebase.SyncronizeQuestionBySerieAndLanguageInteractor;
import com.quiz.series.tv.tvseriesquiz.utils.ADConstants;
import com.quiz.series.tv.tvseriesquiz.utils.LanguageUtils;
import com.quiz.series.tv.tvseriesquiz.utils.executor.MainThreadImpl;
import com.quiz.series.tv.tvseriesquiz.view.activity.QuestionsGame;
import com.quiz.series.tv.tvseriesquiz.view.activity.SeriesDetail;
import com.quiz.series.tv.tvseriesquiz.view.viewmodel.SeriesViewModel;

import java.util.concurrent.Executors;

public class SerieDetailPresenter implements Presenter,SyncronizeInterface.Callback {

    private final SeriesDetail view;
    private SeriesViewModel viewModel;

    public SerieDetailPresenter(SeriesDetail view) {
        this.view = view;
    }

    @Override
    public void init() {
        viewModel = (SeriesViewModel) view.getIntent().getSerializableExtra(ADConstants.serie);
        viewModel.loadImage(view.getIvImageBackground(), viewModel.getUrlImageBackground());
        viewModel.loadImage(view.getIvAvatar(), viewModel.getUrlAvatar());

        view.getPbSeason().setMax(viewModel.getSeason());
        view.getPbSeason().setProgress(viewModel.getSeasonProgress());

        view.getPbEpisode().setMax(viewModel.getTotalEpisodes());
        view.getPbEpisode().setProgress(viewModel.getEpisodeProgress());

        view.getBtPlay().setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(viewModel.isDownloaded()) {
                    gameActivity();
                }
                else {
                    downloadQuestions();
                }

            }
        });
    }

    private void gameActivity() {
        Intent i = new Intent(MyApp.getContext(), QuestionsGame.class);
        i.putExtra(ADQuestionSchema.season, viewModel.getSeasonProgress());
        i.putExtra(ADQuestionSchema.episode, viewModel.getEpisodeProgress());
        i.putExtra(ADQuestionSchema.serieCode, viewModel.getCode());

        view.startActivity(i);
    }

    private void downloadQuestions() {
        final SyncronizeInterface interactor = new SyncronizeQuestionBySerieAndLanguageInteractor(Executors.newSingleThreadExecutor(), new MainThreadImpl(),
                viewModel.getCode(), LanguageUtils.getLanguage());
        interactor.execute(this);
    }

    @Override
    public void notifyError(String message) {

    }

    @Override
    public void notifySuccess(boolean newDataAvailable) {
        gameActivity();
    }
}
