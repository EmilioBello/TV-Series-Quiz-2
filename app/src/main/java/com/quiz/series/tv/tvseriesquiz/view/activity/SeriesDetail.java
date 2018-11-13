package com.quiz.series.tv.tvseriesquiz.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.quiz.series.tv.tvseriesquiz.R;
import com.quiz.series.tv.tvseriesquiz.presenter.Presenter;
import com.quiz.series.tv.tvseriesquiz.presenter.SerieDetailPresenter;

public class SeriesDetail extends AppCompatActivity {

    private Button btPlay;
    private NumberProgressBar pbSeason, pbEpisode;
    private ImageView ivImageBackground, ivAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        transitionSetup();
        setContentView(R.layout.activity_series_detail);

        mapUI();
        init();
    }

    private void transitionSetup() {
        // inside your activity (if you did not enable transitions in your theme)
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        // set an exit transition
        getWindow().setExitTransition(new Explode());
        getWindow().setEnterTransition(new Explode());
    }

    private void mapUI() {
        btPlay = findViewById(R.id.btPlay);

        pbSeason = findViewById(R.id.pbSeason);
        pbEpisode = findViewById(R.id.pbEpisode);

        ivImageBackground = findViewById(R.id.ivImageBackground);
        ivAvatar = findViewById(R.id.ivAvatar);
    }

    private void init() {
        final Presenter presenter = new SerieDetailPresenter(this);
        presenter.init();
    }

    public NumberProgressBar getPbSeason() {
        return pbSeason;
    }

    public NumberProgressBar getPbEpisode() {
        return pbEpisode;
    }

    public ImageView getIvImageBackground() {
        return ivImageBackground;
    }

    public ImageView getIvAvatar() {
        return ivAvatar;
    }
}
