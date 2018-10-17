package com.quiz.series.tv.tvseriesquiz.model.realm.repository;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ADRealm {
    private static final int REALM_SCHEMA_VERSION = 1;
    private static RealmConfiguration config0;

    public void init(Context context) {
        Realm.init(context);
        RealmConfiguration realmConfiguration = getConfig();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    private RealmConfiguration getConfig() {
        if (config0 == null) {
            config0 = new RealmConfiguration.Builder()
                    .schemaVersion(REALM_SCHEMA_VERSION) // Must be bumped when the schema changes
                    .migration(new ADMigration()) // Migration to run instead of throwing an exception
                    .build();
        }

        return config0;
    }
}
