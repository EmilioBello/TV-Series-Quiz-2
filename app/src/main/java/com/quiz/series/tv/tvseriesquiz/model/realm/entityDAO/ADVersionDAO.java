package com.quiz.series.tv.tvseriesquiz.model.realm.entityDAO;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import lombok.Data;

@Data
@RealmClass
public class ADVersionDAO implements RealmModel{

    @PrimaryKey
    private int code;

    private String name;
    private int version;
}
