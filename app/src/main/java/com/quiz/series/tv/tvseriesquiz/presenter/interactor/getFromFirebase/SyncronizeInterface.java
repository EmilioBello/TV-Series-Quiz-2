package com.quiz.series.tv.tvseriesquiz.presenter.interactor.getFromFirebase;

import android.support.annotation.NonNull;

/**
 * Created by Emilio on 30/06/2016.
 */

public interface SyncronizeInterface {

    interface Callback {
        void notifyError(String message);
        void notifySuccess(boolean newDataAvailable);
    }

    void execute(@NonNull Callback callback);
}
