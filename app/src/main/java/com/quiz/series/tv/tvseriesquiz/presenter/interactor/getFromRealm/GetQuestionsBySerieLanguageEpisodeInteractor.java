package com.quiz.series.tv.tvseriesquiz.presenter.interactor.getFromRealm;

import android.support.annotation.NonNull;

import com.quiz.series.tv.tvseriesquiz.model.POJO.ADQuestion;
import com.quiz.series.tv.tvseriesquiz.model.realm.entityDAO.ADQuestionDAO;
import com.quiz.series.tv.tvseriesquiz.model.schema.ADQuestionSchema;
import com.quiz.series.tv.tvseriesquiz.utils.executor.MainThread;

import java.util.List;
import java.util.concurrent.Executor;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmQuery;

public class GetQuestionsBySerieLanguageEpisodeInteractor extends GetEntitiesInteractor<ADQuestion, ADQuestionDAO>{

    private final int serieCode;
    private final int season;
    private final int episode;
    private final String language;

    public GetQuestionsBySerieLanguageEpisodeInteractor(@NonNull final Executor executor, @NonNull final MainThread mainThread,
                                                        @NonNull final int season, @NonNull final int episode, @NonNull final int serieCode,
                                                        @NonNull final String language) {
        super(executor, mainThread, ADQuestion.class, ADQuestionDAO.class);

        this.season = season;
        this.episode = episode;
        this.serieCode = serieCode;
        this.language = language;
    }

    @Override
    public String getOrder() {
        return ADQuestionSchema.code;
    }

    @Override
    public RealmQuery<RealmModel> buildQuery() {
        RealmQuery<RealmModel> query;
        Realm realm = Realm.getDefaultInstance();

        final Class type = ADQuestionDAO.class;

        query = realm.where(type);
        query.equalTo(ADQuestionSchema.serieCode, serieCode);
        query.equalTo(ADQuestionSchema.season, season);
        query.equalTo(ADQuestionSchema.episode, episode);
        query.equalTo(ADQuestionSchema.language, language);

        return query;
    }

    @Override
    public List<ADQuestion> processResult(List<ADQuestion> entities) {
        return entities;
    }
}
