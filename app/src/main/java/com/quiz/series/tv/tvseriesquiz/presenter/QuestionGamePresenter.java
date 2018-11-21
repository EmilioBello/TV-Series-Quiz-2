package com.quiz.series.tv.tvseriesquiz.presenter;

import android.content.Intent;

import com.quiz.series.tv.tvseriesquiz.BR;
import com.quiz.series.tv.tvseriesquiz.R;
import com.quiz.series.tv.tvseriesquiz.model.POJO.ADQuestion;
import com.quiz.series.tv.tvseriesquiz.model.schema.ADQuestionSchema;
import com.quiz.series.tv.tvseriesquiz.presenter.interactor.getFromRealm.GetEntitiesInterface;
import com.quiz.series.tv.tvseriesquiz.presenter.interactor.getFromRealm.GetQuestionsBySerieLanguageEpisodeInteractor;
import com.quiz.series.tv.tvseriesquiz.utils.LanguageUtils;
import com.quiz.series.tv.tvseriesquiz.utils.executor.MainThreadImpl;
import com.quiz.series.tv.tvseriesquiz.view.activity.QuestionsGame;
import com.quiz.series.tv.tvseriesquiz.view.adapter.CustomPageAdapter;
import com.quiz.series.tv.tvseriesquiz.view.viewmodel.QuestionViewModel;
import com.quiz.series.tv.tvseriesquiz.view.viewpager.NonSwipeableViewPager;
import com.quiz.series.tv.tvseriesquiz.view.viewpager.animation.DepthPageTransformer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class QuestionGamePresenter implements Presenter,
        GetEntitiesInterface.Callback<ADQuestion> {

    private final QuestionsGame view;
    private final NonSwipeableViewPager vpQuestion;
    private CustomPageAdapter<QuestionViewModel> adapter;

    private int serieCode, season, episode;

    private List<ADQuestion> items;

    public QuestionGamePresenter(final QuestionsGame view) {
        this.view = view;

        vpQuestion = view.getVpQuestion();
    }

    @Override
    public void init() {
        getValues();

        GetEntitiesInterface<ADQuestion> interactor = new GetQuestionsBySerieLanguageEpisodeInteractor(Executors.newSingleThreadExecutor(), new MainThreadImpl(),
                season, episode, serieCode, LanguageUtils.getLanguage());

        interactor.execute(this);
    }

    private void getValues(){
        Intent intent = view.getIntent();

        season = intent.getIntExtra(ADQuestionSchema.season, 1);
        episode = intent.getIntExtra(ADQuestionSchema.episode, 1);
        serieCode = intent.getIntExtra(ADQuestionSchema.serieCode, 1);

        if(season == 0){
            season = 1;
        }

        if(episode == 0){
            episode = 1;
        }
    }

    private void initializeViewPage(List<ADQuestion> entities) {

        final List<QuestionViewModel> viewModels = mapADQuestionToQuestionViewmodel(entities);

        //adapter
        adapter = new CustomPageAdapter(viewModels, null,
                R.layout.page_season, BR.viewmodel);
        vpQuestion.setAdapter(adapter);
        vpQuestion.setPageTransformer(true, new DepthPageTransformer());
    }

    private List<QuestionViewModel> mapADQuestionToQuestionViewmodel(List<ADQuestion> entities) {
        final List<QuestionViewModel> viewModels = new ArrayList<>();

        for(final ADQuestion entity : entities){
            QuestionViewModel viewModel = QuestionViewModel.converterEntitytoViewModel(entity);
            viewModels.add(viewModel);
        }

        return viewModels;
    }

    public void addQuestions(List<QuestionViewModel> viewModels){
        //Load adapter
        adapter.addAll(viewModels);
    }

    @Override
    public void onSuccess(List<ADQuestion> entities) {
        items = entities;
        initializeViewPage(entities);
    }

    @Override
    public void onConnectionError() {

    }
}
