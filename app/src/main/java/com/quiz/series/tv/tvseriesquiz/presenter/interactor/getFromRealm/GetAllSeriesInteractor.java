package com.quiz.series.tv.tvseriesquiz.presenter.interactor.getFromRealm;

import android.support.annotation.NonNull;

import com.quiz.series.tv.tvseriesquiz.model.realm.entityDAO.ADSerieDAO;
import com.quiz.series.tv.tvseriesquiz.model.schema.ADSerieSchema;
import com.quiz.series.tv.tvseriesquiz.utils.executor.MainThread;

import java.util.List;
import java.util.concurrent.Executor;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmQuery;

public class GetAllSeriesInteractor<In, Out> extends GetEntitiesInteractor<In, Out>{

    public GetAllSeriesInteractor(@NonNull final Executor executor, @NonNull final MainThread mainThread, @NonNull final Class<In> typeIn, @NonNull final Class<Out> typeOut) {
        super(executor, mainThread, typeIn, typeOut);
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
        query.equalTo(ADSerieSchema.active, true);

        return query;
    }

    @Override
    public List<In> processResult(List<In> entities) {
        return entities;
    }
}
