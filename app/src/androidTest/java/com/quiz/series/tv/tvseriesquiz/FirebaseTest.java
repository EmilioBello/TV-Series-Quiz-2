package com.quiz.series.tv.tvseriesquiz;

import android.support.test.filters.MediumTest;
import android.support.test.runner.AndroidJUnit4;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.quiz.series.tv.tvseriesquiz.model.firebase.entityJSON.ADEntityJSON;
import com.quiz.series.tv.tvseriesquiz.model.firebase.entityJSON.ADQuestionJSON;
import com.quiz.series.tv.tvseriesquiz.model.firebase.repository.ADFirebase;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
@MediumTest
public class FirebaseTest {

    @Test
    public void saveAndGetEntity(){
        List<ADEntityJSON> jsons = createQuestionList();

        ADFirebase firebase = new ADFirebase(ADQuestionJSON.class);

        firebase.save("test", null, jsons);

        List<ADEntityJSON> jsonsSaved = firebase.download(createQuery(firebase.getRootRef()));

        List<ADQuestionJSON> questionJSONS = toADQuestionJSON(jsons);
        List<ADQuestionJSON> questionJsonsSaved = toADQuestionJSON(jsonsSaved);

        for(int i = 0; i < jsons.size(); i++){
            assertEquals (questionJSONS.get(i).getCode(), questionJsonsSaved.get(i).getCode());
            assertEquals (questionJSONS.get(i).getQuestion(), questionJsonsSaved.get(i).getQuestion());
            assertEquals (questionJSONS.get(i).getAnswer1(), questionJsonsSaved.get(i).getAnswer1());
            assertEquals (questionJSONS.get(i).getAnswer2(), questionJsonsSaved.get(i).getAnswer2());
            assertEquals (questionJSONS.get(i).getAnswer3(), questionJsonsSaved.get(i).getAnswer3());
            assertEquals (questionJSONS.get(i).getAnswer4(), questionJsonsSaved.get(i).getAnswer4());
            assertEquals (questionJSONS.get(i).getCorrect(), questionJsonsSaved.get(i).getCorrect());

            assertEquals (questionJSONS.get(i).getSeason(), questionJsonsSaved.get(i).getSeason());
            assertEquals (questionJSONS.get(i).getEpisode(), questionJsonsSaved.get(i).getEpisode());

            assertEquals (questionJSONS.get(i).getLanguage(), questionJsonsSaved.get(i).getLanguage());

            assertEquals (questionJSONS.get(i).getSerie_code(), questionJsonsSaved.get(i).getSerie_code());
        }
    }

    private ADQuestionJSON createQuestion() {
        ADQuestionJSON question = new ADQuestionJSON();

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

        question.setSerie_code(7);

        return question;
    }

    private List<ADEntityJSON> createQuestionList(){
        List<ADEntityJSON> entities = new ArrayList<>();

        for(int i = 0; i < 10; i++) {
            ADQuestionJSON question = createQuestion();

            question.setCode(1 + i);
            entities.add(question);
        }

        return entities;
    }

    private Query createQuery(DatabaseReference rootRef){
        Query childRef = rootRef
                .child("test")
                .orderByKey();

        return childRef;
    }

    private List<ADQuestionJSON> toADQuestionJSON (final List<ADEntityJSON> jsons){
        List<ADQuestionJSON> questions = new ArrayList<>();

        for(ADEntityJSON json : jsons) {
            questions.add((ADQuestionJSON) json);
        }

        return questions;
    }
}
