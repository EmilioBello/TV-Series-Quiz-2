package com.quiz.series.tv.tvseriesquiz.presenter.interactor.getFromFirebase;

import android.support.annotation.NonNull;

public interface SyncronizeInterface {

    interface Callback {
        void notifyError(String message);
        void notifySuccess(boolean newDataAvailable);
    }

    void execute(@NonNull Callback callback);
}
