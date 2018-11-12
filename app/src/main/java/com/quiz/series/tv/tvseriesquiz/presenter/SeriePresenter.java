package com.quiz.series.tv.tvseriesquiz.presenter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.quiz.series.tv.tvseriesquiz.BR;
import com.quiz.series.tv.tvseriesquiz.MyApp;
import com.quiz.series.tv.tvseriesquiz.R;
import com.quiz.series.tv.tvseriesquiz.model.POJO.ADSerie;
import com.quiz.series.tv.tvseriesquiz.presenter.interactor.getFromRealm.GetAllSeriesInteractor;
import com.quiz.series.tv.tvseriesquiz.presenter.interactor.getFromRealm.GetEntitiesInterface;
import com.quiz.series.tv.tvseriesquiz.utils.ADConstants;
import com.quiz.series.tv.tvseriesquiz.utils.executor.MainThreadImpl;
import com.quiz.series.tv.tvseriesquiz.view.activity.Series;
import com.quiz.series.tv.tvseriesquiz.view.activity.SeriesDetail;
import com.quiz.series.tv.tvseriesquiz.view.adapter.CommonAdapter;
import com.quiz.series.tv.tvseriesquiz.view.adapter.CommonAdapterListener;
import com.quiz.series.tv.tvseriesquiz.view.viewmodel.SeriesViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class SeriePresenter implements Presenter,
        GetEntitiesInterface.Callback<ADSerie>, CommonAdapterListener<SeriesViewModel> {

    private final Series view;

    private CommonAdapter<SeriesViewModel> adapter;
    private final List<SeriesViewModel> viewModels = new ArrayList<>();

    public SeriePresenter(@NonNull Series view) {
        this.view = view;
    }

    @Override
    public void init() {
        final RecyclerView rvSerie = view.getRvSerie();

        initializeAdapter(rvSerie);

        GetEntitiesInterface<ADSerie> interactor = new GetAllSeriesInteractor(Executors.newSingleThreadExecutor(), new MainThreadImpl());
        interactor.execute(this);
    }

    private void initializeAdapter(@NonNull RecyclerView rvSerie) {
        //adapter
        adapter = new CommonAdapter<>(this, viewModels, R.layout.row_serie, BR.serie);
        rvSerie.setAdapter(adapter);

        //layout
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MyApp.getContext(), LinearLayoutManager.VERTICAL, false);
        rvSerie.setLayoutManager(layoutManager);
    }

    @Override
    public void onSuccess(@NonNull List<ADSerie> entities) {

        viewModels.clear();

        for(ADSerie serie : entities){
            SeriesViewModel viewModel = SeriesViewModel.converterEntitytoViewModel(serie);
            viewModels.add(viewModel);
        }
        adapter.update();
    }

    @Override
    public void onConnectionError() {

    }

    @Override
    public void getItem(SeriesViewModel viewModel) {
        Intent i = new Intent(MyApp.getContext(), SeriesDetail.class);
        i.putExtra(ADConstants.serie, viewModel);

        view.startActivity(i);
    }
}
