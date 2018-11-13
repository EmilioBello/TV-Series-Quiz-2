package com.quiz.series.tv.tvseriesquiz.presenter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

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
    private final RecyclerView rvSerie;

    private CommonAdapter<SeriesViewModel> adapter;
    private final List<SeriesViewModel> viewModels = new ArrayList<>();

    public SeriePresenter(@NonNull Series view) {
        this.view = view;

        rvSerie = view.getRvSerie();
    }

    @Override
    public void init() {
        initializeAdapter();

        GetEntitiesInterface<ADSerie> interactor = new GetAllSeriesInteractor(Executors.newSingleThreadExecutor(), new MainThreadImpl());
        interactor.execute(this);
    }

    private void initializeAdapter() {
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
    public void getItem(@NonNull SeriesViewModel viewModel, View cardView) {
        ActivityOptionsCompat options = transitionSharedElements(cardView);

        Intent i = new Intent(MyApp.getContext(), SeriesDetail.class);
        i.putExtra(ADConstants.serie, viewModel);

        view.startActivity(i, options.toBundle());
    }

    @NonNull
    private ActivityOptionsCompat transitionSharedElements(@NonNull View cardView) {
        final ImageView ivBackground = cardView.findViewById(R.id.ivImageBackground);
        final Pair<View, String> p1 = new Pair<>((View) ivBackground, ViewCompat.getTransitionName(ivBackground));

        final ImageView ivAvatar = cardView.findViewById(R.id.ivAvatar);
        final Pair<View, String> p2 = new Pair<>((View) ivAvatar, ViewCompat.getTransitionName(ivAvatar));

        return ActivityOptionsCompat
                .makeSceneTransitionAnimation(view, p1, p2);
    }
}
