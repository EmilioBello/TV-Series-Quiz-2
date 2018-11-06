package com.quiz.series.tv.tvseriesquiz;

import android.support.test.filters.MediumTest;
import android.support.test.runner.AndroidJUnit4;

import com.quiz.series.tv.tvseriesquiz.model.POJO.ADSerie;
import com.quiz.series.tv.tvseriesquiz.model.realm.entityDAO.ADSerieDAO;
import com.quiz.series.tv.tvseriesquiz.model.realm.repository.RealmRepository;
import com.quiz.series.tv.tvseriesquiz.model.schema.ADSerieSchema;
import com.quiz.series.tv.tvseriesquiz.presenter.interactor.getFromFirebase.SyncronizeInteractor;
import com.quiz.series.tv.tvseriesquiz.presenter.interactor.getFromFirebase.SyncronizeSeriesInteractor;
import com.quiz.series.tv.tvseriesquiz.utils.executor.MainThreadImpl;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.concurrent.Executors;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmQuery;

import static junit.framework.Assert.*;

@RunWith(AndroidJUnit4.class)
@MediumTest
public class InteractorFirebaseTest {

    @Test
    public void SyncroniceSerieInteractor(){
        SyncronizeInteractor interactor = new SyncronizeSeriesInteractor(
                Executors.newSingleThreadExecutor(),
                new MainThreadImpl()
        );

        interactor.startFirebase();

        RealmRepository<ADSerie, ADSerieDAO> repository = new RealmRepository<>(ADSerie.class, ADSerieDAO.class);
        List<ADSerie> entities = repository.fetchAll(buildQueryAll());

        assertTrue(!entities.isEmpty());
    }

    private RealmQuery<RealmModel> buildQueryAll() {
        RealmQuery<RealmModel> query;
        Realm realm = Realm.getDefaultInstance();

        final Class type = ADSerieDAO.class;

        query = realm.where(type)
                .sort(ADSerieSchema.code);

        return query;
    }
}
