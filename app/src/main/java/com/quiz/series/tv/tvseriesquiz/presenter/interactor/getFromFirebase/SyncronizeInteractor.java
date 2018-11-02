package com.quiz.series.tv.tvseriesquiz.presenter.interactor.getFromFirebase;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;
import com.quiz.series.tv.tvseriesquiz.MyApp;
import com.quiz.series.tv.tvseriesquiz.model.firebase.entityJSON.ADEntityJSON;
import com.quiz.series.tv.tvseriesquiz.model.firebase.repository.ADFirebase;
import com.quiz.series.tv.tvseriesquiz.model.realm.repository.RealmRepository;
import com.quiz.series.tv.tvseriesquiz.utils.executor.MainThread;

import java.util.List;
import java.util.concurrent.Executor;

//In = POJO, Out = EntityDAO
public abstract class SyncronizeInteractor<In, Out> implements SyncronizeInterface,Runnable {

    private final Executor executor;
    private final MainThread mainThread;
    private final Class<In> typeIn;
    private final Class<Out> typeOut;

    private Callback callback;

    //error
    private boolean error;
    private String messageError;

    //Context
    protected final Context context;

    public SyncronizeInteractor(@NonNull final Executor executor,@NonNull final MainThread mainThread,@NonNull final Class<In> typeIn, @NonNull final Class<Out> typeOut) {
        this.executor = executor;
        this.mainThread = mainThread;
        this.typeIn = typeIn;
        this.typeOut = typeOut;

        this.error = false;
        this.messageError = "";
        context = MyApp.getContext();
    }

    @Override
    public void execute(@NonNull Callback callback) {
        this.callback = callback;
        executor.execute(this);
    }

    @Override
    public void run() {
        if (isNetworkAvailable()) {
            startFirebase();
        } else {
            notifyError();
        }
    }

    private void startFirebase() {
        ADFirebase firebase = new ADFirebase(typeOut);
        List<ADEntityJSON> jsons = firebase.download(buildQuery());

        List<Out> entities = processEntities(jsons);

        RealmRepository<In, Out> repository = new RealmRepository<>(typeIn, typeOut);
        repository.saveDAO(entities);

        notifySuccess();
    }

    protected abstract DatabaseReference buildQuery();

    protected abstract List<Out> processEntities(@NonNull List<ADEntityJSON> jsons);

    private void notifyError() {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.notifyError(messageError);
            }
        });
    }

    private void notifySuccess() {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.notifySuccess(true);
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = null;
        if (connectivityManager != null) {
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        }

        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            return true;
        } else {
            error = true;
            messageError = "Impossible connecting to data";
            return false;
        }
    }
}
