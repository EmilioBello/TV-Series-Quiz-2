package com.quiz.series.tv.tvseriesquiz.model.POJO;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by jose on 30/6/15.
 */
@Data
@Builder
@EqualsAndHashCode(callSuper=false)
public class ADVersion{

    private int code;

    private String name;
    private int version;
}
