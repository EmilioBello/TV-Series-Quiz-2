package com.quiz.series.tv.tvseriesquiz.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.quiz.series.tv.tvseriesquiz.R;
import com.quiz.series.tv.tvseriesquiz.presenter.Presenter;
import com.quiz.series.tv.tvseriesquiz.presenter.QuestionGamePresenter;
import com.quiz.series.tv.tvseriesquiz.view.viewpager.NonSwipeableViewPager;

public class QuestionsGame extends AppCompatActivity {

    private NonSwipeableViewPager vpQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_game);

        mapUI();
        init();
    }

    private void mapUI() {
        vpQuestion = findViewById(R.id.vpQuestions);
    }

    private void init() {
        final Presenter presenter = new QuestionGamePresenter(this);
        presenter.init();
    }

    public NonSwipeableViewPager getVpQuestion() {
        return vpQuestion;
    }
}
