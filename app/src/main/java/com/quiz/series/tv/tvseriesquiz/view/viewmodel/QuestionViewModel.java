package com.quiz.series.tv.tvseriesquiz.view.viewmodel;

import com.quiz.series.tv.tvseriesquiz.model.POJO.ADQuestion;

import lombok.Data;

@Data
public class QuestionViewModel {
    private String question;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private int correct;
    private int season;
    private int episode;

    public static QuestionViewModel converterEntitytoViewModel(ADQuestion question) {
        QuestionViewModel viewModel = new QuestionViewModel();

        viewModel.setQuestion(question.getQuestion());
        viewModel.setAnswer1(question.getAnswer1());
        viewModel.setAnswer2(question.getAnswer2());
        viewModel.setAnswer3(question.getAnswer3());
        viewModel.setAnswer4(question.getAnswer4());
        viewModel.setCorrect(question.getCorrect());
        viewModel.setSeason(question.getSeason());
        viewModel.setEpisode(question.getEpisode());

        return viewModel;
    }
}
