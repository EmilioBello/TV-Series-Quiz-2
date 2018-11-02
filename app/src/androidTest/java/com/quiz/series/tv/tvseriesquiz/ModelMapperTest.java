package com.quiz.series.tv.tvseriesquiz;

import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;

import com.quiz.series.tv.tvseriesquiz.model.POJO.ADQuestion;
import com.quiz.series.tv.tvseriesquiz.model.POJO.ADSerie;
import com.quiz.series.tv.tvseriesquiz.model.POJO.ADVersion;
import com.quiz.series.tv.tvseriesquiz.model.firebase.entityJSON.ADSerieJSON;
import com.quiz.series.tv.tvseriesquiz.model.mapper.ADSerieJSONToSerieDAO;
import com.quiz.series.tv.tvseriesquiz.model.realm.entityDAO.ADQuestionDAO;
import com.quiz.series.tv.tvseriesquiz.model.realm.entityDAO.ADSerieDAO;
import com.quiz.series.tv.tvseriesquiz.model.realm.entityDAO.ADVersionDAO;
import com.quiz.series.tv.tvseriesquiz.utils.ADConstants;
import com.quiz.series.tv.tvseriesquiz.utils.DisplayUtils;

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

    @Test
    public void mapperSerieJSONToSerieDAO(){
        final ADSerieJSON entity = createSerieJSON();

        final ADSerieJSONToSerieDAO mapper = new ADSerieJSONToSerieDAO();

        final ADSerieDAO dao = (ADSerieDAO) mapper.convertFirebaseObjectToDAO(entity);

        assertEquals (dao.getCode(), entity.getCode());
        assertEquals (dao.getName(), entity.getName());
        assertEquals (dao.getSeason(), entity.getSeason());
        assertEquals (dao.getTotalEpisodes(), entity.getTotalEpisodes());
        assertEquals (dao.isComplete(), entity.isComplete());
        assertEquals (dao.getStatus(), entity.getStatus());

        //check resolution image
        String resolution = DisplayUtils.getDensity(InstrumentationRegistry.getContext());

        if (ADConstants.IMAGE1X_VALUE.equals(resolution)) {
            if (entity.getUrlAvatar1x() != null) {
                assertEquals (dao.getUrlAvatar() ,entity.getUrlAvatar1x());
                assertEquals (dao.getUrlImageBackground(), entity.getUrlImageBackground1x());
            }
        } else if(ADConstants.IMAGE15X_VALUE.equals(resolution)) {
            if (entity.getUrlAvatar15x() != null) {
                assertEquals (dao.getUrlAvatar() ,entity.getUrlAvatar15x());
                assertEquals (dao.getUrlImageBackground(), entity.getUrlImageBackground15x());
            }
        } else if(ADConstants.IMAGE2X_VALUE.equals(resolution)) {
            if (entity.getUrlAvatar2x() != null) {
                assertEquals (dao.getUrlAvatar() ,entity.getUrlAvatar2x());
                assertEquals (dao.getUrlImageBackground(), entity.getUrlImageBackground2x());
            }
        } else if(ADConstants.IMAGE3X_VALUE.equals(resolution)) {
            if (entity.getUrlAvatar3x() != null) {
                assertEquals (dao.getUrlAvatar() ,entity.getUrlAvatar3x());
                assertEquals (dao.getUrlImageBackground(), entity.getUrlImageBackground3x());
            }
        } else if(resolution.startsWith("0")) {
            if (entity.getUrlAvatar1x() != null) {
                assertEquals (dao.getUrlAvatar() ,entity.getUrlAvatar1x());
                assertEquals (dao.getUrlImageBackground(), entity.getUrlImageBackground1x());
            }
        } else {
            if (entity.getUrlAvatar3x() != null) {
                assertEquals (dao.getUrlAvatar() ,entity.getUrlAvatar3x());
                assertEquals (dao.getUrlImageBackground(), entity.getUrlImageBackground3x());
            }
        }
    }

    @Test
    public void mapperVersionToVersionDAO(){
        final ModelMapper modelMapper = new ModelMapper();

        final ADVersion entity = createVersion();

        final ADVersionDAO dao = modelMapper.map(entity, ADVersionDAO.class);

        assertEquals (dao.getCode(), entity.getCode());
        assertEquals (dao.getName(), entity.getName());
        assertEquals (dao.getVersion(), entity.getVersion());
    }

    private ADQuestion createQuestion(){
        ADQuestion question = new ADQuestion();

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
        ADSerie entity = new ADSerie();

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

    private ADSerieJSON createSerieJSON() {
        ADSerieJSON entity = new ADSerieJSON();

        entity.setCode(1);

        entity.setName("Game of Thrones");

        entity.setSeason(2);
        entity.setTotalEpisodes(3);

        entity.setSeasonProgress(4);
        entity.setEpisodeProgress(5);

        entity.setListEpisode("1,2,3,4");

        entity.setComplete(false);
        entity.setStatus(6);

        entity.setUrlAvatar1x("7");
        entity.setUrlAvatar15x("8");
        entity.setUrlAvatar2x("9");
        entity.setUrlAvatar3x("10");

        entity.setUrlImageBackground1x("11");
        entity.setUrlImageBackground15x("12");
        entity.setUrlImageBackground2x("13");
        entity.setUrlImageBackground3x("14");

        return entity;
    }

    private ADVersion createVersion() {
        ADVersion entity = new ADVersion();

        entity.setCode(1);
        entity.setName("ADSerie");
        entity.setVersion(2);

        return entity;
    }
}
