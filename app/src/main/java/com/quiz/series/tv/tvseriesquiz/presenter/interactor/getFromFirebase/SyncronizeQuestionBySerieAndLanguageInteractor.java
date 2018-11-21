package com.quiz.series.tv.tvseriesquiz.presenter.interactor.getFromFirebase;

import android.support.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.quiz.series.tv.tvseriesquiz.model.POJO.ADQuestion;
import com.quiz.series.tv.tvseriesquiz.model.POJO.ADSerie;
import com.quiz.series.tv.tvseriesquiz.model.firebase.entityJSON.ADEntityJSON;
import com.quiz.series.tv.tvseriesquiz.model.firebase.entityJSON.ADQuestionJSON;
import com.quiz.series.tv.tvseriesquiz.model.realm.entityDAO.ADQuestionDAO;
import com.quiz.series.tv.tvseriesquiz.model.realm.entityDAO.ADSerieDAO;
import com.quiz.series.tv.tvseriesquiz.model.realm.repository.RealmRepository;
import com.quiz.series.tv.tvseriesquiz.model.schema.ADQuestionSchema;
import com.quiz.series.tv.tvseriesquiz.utils.ADConstants;
import com.quiz.series.tv.tvseriesquiz.utils.executor.MainThread;

import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmQuery;

public class SyncronizeQuestionBySerieAndLanguageInteractor extends SyncronizeInteractor<ADQuestion, ADQuestionDAO, ADQuestionJSON> {
    private final int serieCode;
    private final String language;

    public SyncronizeQuestionBySerieAndLanguageInteractor(@NonNull final Executor executor, @NonNull final MainThread mainThread,
                                                          @NonNull final int serieCode, @NonNull final String language){
        super(executor, mainThread, ADQuestion.class, ADQuestionDAO.class, ADQuestionJSON.class);

        this.serieCode = serieCode;
        this.language = language;
    }

    @Override
    protected Query buildQuery(DatabaseReference rootRef) {
        final Query childRef = rootRef
                .child(ADConstants.question + "/")
                .child(serieCode + "/")
                .child(language + "/");

        return  childRef;
    }

    @Override
    protected List processEntities(@NonNull List<ADEntityJSON> list) {
        final List<ADQuestionDAO> daos = new ArrayList<>();
        final ModelMapper modelMapper = new ModelMapper();

        for(final ADEntityJSON json : list){
            final ADQuestionJSON jsonQuestion = (ADQuestionJSON) json;
            final ADQuestionDAO dao = modelMapper.map(jsonQuestion, ADQuestionDAO.class);
            daos.add(dao);
        }

        final RealmRepository<ADSerie, ADSerieDAO> repository = new RealmRepository<>(ADSerie.class, ADSerieDAO.class);
        ADSerie serie = repository.fetch(createQuery());

        serie.setDownloaded(true);

        repository.save(serie);

        return daos;
    }

    private RealmQuery<RealmModel> createQuery() {
        RealmQuery<RealmModel> query;
        Realm realm = Realm.getDefaultInstance();

        final Class type = ADSerieDAO.class;

        query = realm.where(type);
        query.equalTo(ADQuestionSchema.code, serieCode);

        return query;
    }
}
