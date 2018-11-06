package com.quiz.series.tv.tvseriesquiz.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import com.quiz.series.tv.tvseriesquiz.MyApp;
import com.quiz.series.tv.tvseriesquiz.R;
import com.quiz.series.tv.tvseriesquiz.presenter.LaunchPresenter;

public class Launch extends AppCompatActivity {

    private ProgressBar progressBar;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.launch);
        
        mapUI();
        init();
    }

    private void mapUI() {
        progressBar = findViewById(R.id.progressBar);
    }

    private void init() {
        final LaunchPresenter presenter = new LaunchPresenter(this);
        presenter.init();
    }

    public void progressStart(){
        progressBar.setProgress(0);
    }

    public void progressEnd(){
        progressBar.setProgress(1);

        Intent intent = new Intent(MyApp.getContext(), Series.class);
        startActivity(intent);
    }
}
