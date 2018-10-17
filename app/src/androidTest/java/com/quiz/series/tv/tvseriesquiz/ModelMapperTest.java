package com.quiz.series.tv.tvseriesquiz;

import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;

import com.quiz.series.tv.tvseriesquiz.model.POJO.ADQuestion;
import com.quiz.series.tv.tvseriesquiz.model.POJO.ADSerie;
import com.quiz.series.tv.tvseriesquiz.model.realm.entityDAO.ADQuestionDAO;
import com.quiz.series.tv.tvseriesquiz.model.realm.entityDAO.ADSerieDAO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class ModelMapperTest {

    @Test
    public void mapperQuestionToQuestionDAO(){
        final ModelMapper modelMapper = new ModelMapper();

        final ADQuestion  question = createQuestion();

        final ADQuestionDAO dao = modelMapper.map(question, ADQuestionDAO.class);

        assertEquals (dao.getCode(), question.getCode());
        assertEquals (dao.getQuestion(), question.getQuestion());
        assertEquals (dao.getAnswer1(), question.getAnswer1());
        assertEquals (dao.getAnswer2(), question.getAnswer2());
        assertEquals (dao.getAnswer3(), question.getAnswer3());
        assertEquals (dao.getAnswer4(), question.getAnswer4());
        assertEquals (dao.getCorrect(), question.getCorrect());

        assertEquals (dao.getSeason(), question.getSeason());
        assertEquals (dao.getEpisode(), question.getEpisode());

        assertEquals (dao.getLanguage(), question.getLanguage());

        assertEquals (dao.getSerieCode(), question.getSerieCode());
    }

    @Test
    public void mapperSerieToSerieDAO(){
        final ModelMapper modelMapper = new ModelMapper();

        final ADSerie entity = createSerie();

        final ADSerieDAO dao = modelMapper.map(entity, ADSerieDAO.class);

        assertEquals (dao.getCode(), entity.getCode());
        assertEquals (dao.getName(), entity.getName());
        assertEquals (dao.getSeason(), entity.getSeason());
        assertEquals (dao.getTotalEpisodes(), entity.getTotalEpisodes());
        assertEquals (dao.isDownloaded(), entity.isDownloaded());
        assertEquals (dao.getListEpisode(), entity.getListEpisode());
        assertEquals (dao.isComplete(), entity.isComplete());
        assertEquals (dao.getStatus(), entity.getStatus());
        assertEquals (dao.getUrlAvatar(), entity.getUrlAvatar());
        assertEquals (dao.getUrlImageBackground(), entity.getUrlImageBackground());
    }

    private ADQuestion createQuestion(){
        ADQuestion question = ADQuestion.builder().build();

        question.setCode(1);

        question.setQuestion("What time is it?");
        question.setAnswer1("1");
        question.setAnswer2("2");
        question.setAnswer3("3");
        question.setAnswer4("4");
        question.setCorrect(1);

        question.setSeason(5);
        question.setEpisode(6);

        question.setLanguage("en");

        question.setSerieCode(7);

        return question;
    }

    private ADSerie createSerie() {
        ADSerie entity = ADSerie.builder().build();

        entity.setCode(1);

        entity.setName("Game of Thrones");

        entity.setSeason(2);
        entity.setTotalEpisodes(3);

        entity.setSeasonProgress(4);
        entity.setEpisodeProgress(5);

        entity.setDownloaded(true);

        ArrayList<Integer> episodes = new ArrayList<>();
        episodes.add(1);
        episodes.add(2);
        entity.setListEpisode(episodes);

        entity.setComplete(false);
        entity.setStatus(6);

        entity.setUrlAvatar("7");
        entity.setUrlImageBackground("8");

        return entity;
    }
}
