package com.quiz.series.tv.tvseriesquiz.presenter;


import com.quiz.series.tv.tvseriesquiz.presenter.interactor.getFromFirebase.SyncronizeInteractor;
import com.quiz.series.tv.tvseriesquiz.presenter.interactor.getFromFirebase.SyncronizeInterface;
import com.quiz.series.tv.tvseriesquiz.presenter.interactor.getFromFirebase.SyncronizeSeriesInteractor;
import com.quiz.series.tv.tvseriesquiz.utils.executor.MainThreadImpl;
import com.quiz.series.tv.tvseriesquiz.view.activity.Launch;

import java.util.concurrent.Executors;

public class LaunchPresenter implements SyncronizeInterface.Callback{

    private final Launch view;

    public LaunchPresenter(Launch view) {
        this.view = view;
    }

    public void init(){
        view.progressStart();

        SyncronizeInteractor interactor = new SyncronizeSeriesInteractor(
                Executors.newSingleThreadExecutor(),
                new MainThreadImpl()
        );

        interactor.execute(this);
    }

    @Override
    public void notifyError(String message) {
        view.progressEnd();
    }

    @Override
    public void notifySuccess(boolean newDataAvailable) {
        view.progressEnd();
    }
}
