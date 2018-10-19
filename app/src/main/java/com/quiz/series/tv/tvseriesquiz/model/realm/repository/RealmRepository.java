package com.quiz.series.tv.tvseriesquiz.model.realm.repository;

import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class RealmRepository<In, Out> {

    private final Class<In> typeIn;
    private final Class<Out> typeOut;

    public RealmRepository(final Class<In> typeIn, final Class<Out> typeOut){
        this.typeIn = typeIn;
        this.typeOut = typeOut;
    }

    public void save(final In entity){
        if(entity != null){
            final Realm realm = getRealm();

            final ModelMapper modelMapper = new ModelMapper();
            final Out entityRealm = modelMapper.map(entity, typeOut);

            realm.beginTransaction();

            realm.insertOrUpdate((RealmModel) entityRealm);

            realm.commitTransaction();
            realm.close();
        }
    }

    public void save(final List<In> entities){
        if(!entities.isEmpty()){

            List<Out> entitiesOut = new ArrayList<>();

            final ModelMapper modelMapper = new ModelMapper();

            for(In entity : entities){
                final Out entityRealm = modelMapper.map(entity, typeOut);
                entitiesOut.add(entityRealm);
            }

            Realm realm = getRealm();
            realm.beginTransaction();

            realm.insertOrUpdate((Collection<? extends RealmModel>) entitiesOut);

            realm.commitTransaction();
            realm.close();
        }
    }

    public List<In> fetchAll(RealmQuery<Out> query) {
        return fetchQuery(query);
    }

    public In fetch(RealmQuery<Out> query) {
        List<In> entities;
        entities = fetchQuery(query);

        if (entities.size() > 0) {
            return entities.get(0);
        } else {
            return null;
        }
    }

    private List<In> fetchQuery(RealmQuery<Out> query) {

        RealmResults<Out> entitiesDAO = query.findAll();

        final ModelMapper modelMapper = new ModelMapper();

        List<In> entities = new ArrayList<>();
        for(Out entityDAO : entitiesDAO){
            final In entity = modelMapper.map(entityDAO, typeIn);
            entities.add(entity);
        }

        return entities;
    }

    public void update(final In entity){
        save(entity);
    }

    public void update(final List<In> entities){
        save(entities);
    }

    public void delete(RealmQuery<Out> query){
        final Realm realm = getRealm();

        realm.beginTransaction();

        RealmResults<Out> result = query.findAll();
        result.deleteAllFromRealm();

        realm.commitTransaction();
        realm.close();
    }

    private Realm getRealm() {
        return Realm.getDefaultInstance();
    }
}
