package com.quiz.series.tv.tvseriesquiz.view.adapter;

import android.support.annotation.NonNull;
import android.view.View;

public interface CommonAdapterListener<ViewModel> {
    void getItem(@NonNull ViewModel viewModel, View view);
}
