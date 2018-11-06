package com.quiz.series.tv.tvseriesquiz.presenter.interactor.getFromRealm;


import android.support.annotation.NonNull;

import com.quiz.series.tv.tvseriesquiz.model.realm.repository.RealmRepository;
import com.quiz.series.tv.tvseriesquiz.utils.executor.MainThread;

import java.util.List;
import java.util.concurrent.Executor;

public abstract class GetEntitiesInteractor<In, Out> implements Runnable, GetEntitiesInterface<In>{
    private final Executor executor;
    private GetEntitiesInterface.Callback<In> callback;
    private final MainThread mainThread;

    private final Class<In> typeIn;
    private final Class<Out> typeOut;

    public GetEntitiesInteractor(@NonNull final Executor executor,@NonNull final MainThread mainThread,@NonNull final Class<In> typeIn,@NonNull final Class<Out> typeOut) {
        this.executor = executor;
        this.mainThread = mainThread;

        this.typeIn = typeIn;
        this.typeOut = typeOut;
    }

    @Override
    public void execute(@NonNull GetEntitiesInterface.Callback<In> callback) {
        this.callback = callback;

        //call method override "run" from interface runnable
        this.executor.execute(this);
    }


    /**
     * notify result to UI thread
     */
    @Override
    public void run() {
        if (checkParameters()) {
            notifyError();
        } else {
            final RealmRepository<In, Out> repository = new RealmRepository<>(typeIn, typeOut);
            final List<In> entities = repository.fetchAll(buildQuery());
            nofityEntitiesLoaded(processResult(entities));
        }
    }


    private boolean checkParameters() {
        return  this.executor == null || this.callback == null;
    }

    private void notifyError() {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onConnectionError();
            }
        });
    }

    private void nofityEntitiesLoaded(@NonNull final List<In> entities) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(entities);
            }
        });
    }
}
