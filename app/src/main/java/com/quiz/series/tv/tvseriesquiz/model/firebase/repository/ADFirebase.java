package com.quiz.series.tv.tvseriesquiz.model.firebase.repository;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.quiz.series.tv.tvseriesquiz.model.firebase.entityJSON.ADEntityJSON;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ADFirebase {

    private DatabaseReference rootRef;

    private final Class typeJSON;

    public ADFirebase(@NonNull final Class typeJSON) {
        this.typeJSON = typeJSON;

        if (rootRef == null) {
            rootRef = FirebaseDatabase.getInstance().getReference();
        }
    }

    public DatabaseReference getRootRef() {
        return rootRef;
    }

    public List<ADEntityJSON> download(@NonNull Query childRef) {
        final CountDownLatch lock = new CountDownLatch(2);
        final List<ADEntityJSON> entitiesDownloaded = new ArrayList<>();

        childRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot entitySnapshot : dataSnapshot.getChildren()) {
                    try {
                        final ADEntityJSON json = (ADEntityJSON) entitySnapshot.getValue(typeJSON);
                        entitiesDownloaded.add(json);
                    } catch (com.google.firebase.database.DatabaseException e) {

                    }
                }

                lock.countDown();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                lock.countDown();
            }
        });

        lock.countDown();
        lock(lock);

        return entitiesDownloaded;
    }

    public void save (@NonNull final String database, final List<String> paths, @NonNull final List<ADEntityJSON> datas)  {
        DatabaseReference childRef = rootRef.child(database);

        if(paths != null) {
            for(final String path : paths){
                childRef = rootRef.child(path);
            }
        }

        for (final ADEntityJSON data : datas) {
            final String child = String.valueOf(data.getCode());
            childRef.child(child).setValue(data);
        }
    }

    private void lock(CountDownLatch lock) {
        try {
            lock.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
