package com.quiz.series.tv.tvseriesquiz.presenter.interactor.getFromFirebase;

import android.support.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.quiz.series.tv.tvseriesquiz.model.POJO.ADSerie;
import com.quiz.series.tv.tvseriesquiz.model.firebase.entityJSON.ADEntityJSON;
import com.quiz.series.tv.tvseriesquiz.model.firebase.entityJSON.ADSerieJSON;
import com.quiz.series.tv.tvseriesquiz.model.mapper.ADSerieJSONToSerieDAO;
import com.quiz.series.tv.tvseriesquiz.model.realm.entityDAO.ADSerieDAO;
import com.quiz.series.tv.tvseriesquiz.utils.ADConstants;
import com.quiz.series.tv.tvseriesquiz.utils.executor.MainThread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class SyncronizeSeriesInteractor extends SyncronizeInteractor<ADSerie, ADSerieDAO, ADSerieJSON> {

    public SyncronizeSeriesInteractor(@NonNull Executor executor, @NonNull MainThread mainThread) {
        super(executor, mainThread, ADSerie.class, ADSerieDAO.class, ADSerieJSON.class);
    }

    @Override
    protected Query buildQuery(@NonNull DatabaseReference rootRef) {
        final Query childRef = rootRef
                .child(ADConstants.serie + "/");

        return  childRef;
    }

    @Override
    protected List<ADSerieDAO> processEntities(@NonNull List<ADEntityJSON> seriesJSON) {
        final List<ADSerieDAO> daos = new ArrayList<>();
        final ADSerieJSONToSerieDAO mapper = new ADSerieJSONToSerieDAO();

        for(final ADEntityJSON json : seriesJSON){
            ADSerieDAO dao = (ADSerieDAO) mapper.convertFirebaseObjectToDAO(json);
            daos.add(dao);
        }

        return daos;
    }
}
