package com.quiz.series.tv.tvseriesquiz.presenter.interactor.getFromRealm;

import android.support.annotation.NonNull;

import com.quiz.series.tv.tvseriesquiz.model.POJO.ADSerie;
import com.quiz.series.tv.tvseriesquiz.model.realm.entityDAO.ADSerieDAO;
import com.quiz.series.tv.tvseriesquiz.model.schema.ADSerieSchema;
import com.quiz.series.tv.tvseriesquiz.utils.executor.MainThread;

import java.util.List;
import java.util.concurrent.Executor;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmQuery;

public class GetAllSeriesInteractor extends GetEntitiesInteractor<ADSerie, ADSerieDAO>{

    public GetAllSeriesInteractor(@NonNull final Executor executor, @NonNull final MainThread mainThread) {
        super(executor, mainThread, ADSerie.class, ADSerieDAO.class);
    }

    @Override
    public String getOrder() {
        return ADSerieSchema.code;
    }

    @Override
    public RealmQuery<RealmModel> buildQuery() {
        RealmQuery<RealmModel> query;
        Realm realm = Realm.getDefaultInstance();

        final Class type = ADSerieDAO.class;

        query = realm.where(type);

        return query;
    }

    @Override
    public List<ADSerie> processResult(@NonNull List<ADSerie> entities) {
        return entities;
    }
}
