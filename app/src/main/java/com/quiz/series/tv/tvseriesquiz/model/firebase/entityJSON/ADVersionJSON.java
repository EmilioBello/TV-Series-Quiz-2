package com.quiz.series.tv.tvseriesquiz.model.firebase.entityJSON;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ADVersionJSON{

    private int code;
    private boolean active;
    private long updatedAt;

    private String name;
    private int version;
}
