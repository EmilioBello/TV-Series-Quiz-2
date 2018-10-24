package com.quiz.series.tv.tvseriesquiz.presenter.interactor.getFromRealm;

import java.util.List;

import io.realm.RealmModel;
import io.realm.RealmQuery;

public interface GetEntitiesInterface<In> {
    void execute(Callback<In> callback);
    String getOrder();
    RealmQuery<RealmModel> buildQuery();
    List<In> processResult(List<In> entities);

    interface Callback<In> {
        void onSuccess(final List<In> entities);
        void onConnectionError();
    }
}
