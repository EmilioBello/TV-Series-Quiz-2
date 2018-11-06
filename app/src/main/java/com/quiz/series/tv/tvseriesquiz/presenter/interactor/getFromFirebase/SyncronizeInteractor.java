package com.quiz.series.tv.tvseriesquiz.presenter.interactor.getFromFirebase;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.quiz.series.tv.tvseriesquiz.MyApp;
import com.quiz.series.tv.tvseriesquiz.model.firebase.entityJSON.ADEntityJSON;
import com.quiz.series.tv.tvseriesquiz.model.firebase.repository.ADFirebase;
import com.quiz.series.tv.tvseriesquiz.model.realm.repository.RealmRepository;
import com.quiz.series.tv.tvseriesquiz.utils.executor.MainThread;

import java.util.List;
import java.util.concurrent.Executor;

public abstract class SyncronizeInteractor<POJO, DAO, JSON> implements SyncronizeInterface,Runnable {

    private final Executor executor;
    private final MainThread mainThread;
    private final Class<POJO> typePOJO;
    private final Class<DAO> typeDAO;
    private final Class<JSON> typeJSON;

    private Callback callback;

    //error
    private boolean error;
    private String messageError;

    //Context
    protected final Context context;

    public SyncronizeInteractor(@NonNull final Executor executor, @NonNull final MainThread mainThread,
                                @NonNull final Class<POJO> typePOJO, @NonNull final Class<DAO> typeDAO, @NonNull final Class<JSON> typeJSON) {
        this.executor = executor;
        this.mainThread = mainThread;
        this.typePOJO = typePOJO;
        this.typeDAO = typeDAO;
        this.typeJSON = typeJSON;

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

    //It is public to be easyly testeable
    public void startFirebase() {
        ADFirebase firebase = new ADFirebase(typeJSON);
        List<ADEntityJSON> jsons = firebase.download(buildQuery(firebase.getRootRef()));

        List<DAO> entities = processEntities(jsons);

        RealmRepository<POJO, DAO> repository = new RealmRepository<>(typePOJO, typeDAO);
        repository.saveDAO(entities);

        notifySuccess();
    }

    protected abstract Query buildQuery(DatabaseReference rootRef);

    protected abstract List<DAO> processEntities(@NonNull List<ADEntityJSON> jsons);

    private void notifyError() {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                if(callback != null){
                    callback.notifyError(messageError);
                }
            }
        });
    }

    private void notifySuccess() {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                if(callback != null){
                    callback.notifySuccess(true);
                }
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
