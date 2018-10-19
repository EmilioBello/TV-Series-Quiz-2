package com.quiz.series.tv.tvseriesquiz.model.firebase.entityJSON;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ADVersionJSON extends ADEntityJSON{

    public ADVersionJSON() {}

    private String name;
    private int version;
}
