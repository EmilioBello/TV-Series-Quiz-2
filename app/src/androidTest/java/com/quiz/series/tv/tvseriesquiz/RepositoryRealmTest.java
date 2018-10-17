package com.quiz.series.tv.tvseriesquiz;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.MediumTest;
import android.support.test.runner.AndroidJUnit4;

import com.quiz.series.tv.tvseriesquiz.model.POJO.ADQuestion;
import com.quiz.series.tv.tvseriesquiz.model.realm.entityDAO.ADQuestionDAO;
import com.quiz.series.tv.tvseriesquiz.model.realm.repository.ADRealm;
import com.quiz.series.tv.tvseriesquiz.model.realm.repository.RealmRepository;
import com.quiz.series.tv.tvseriesquiz.model.schema.ADQuestionSchema;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertNotEquals;


@RunWith(AndroidJUnit4.class)
@MediumTest
public class RepositoryRealmTest {

    @BeforeClass
    public static void setUpClass() {
        Context context = InstrumentationRegistry.getTargetContext();
        ADRealm realm = new ADRealm();

        realm.init(context);
    }

    @Test
    public void testSaveAndFetchEntity(){
        ADQuestion question = createQuestion();

        RealmRepository<ADQuestion, ADQuestionDAO> repository = new RealmRepository<>(ADQuestion.class, ADQuestionDAO.class);

        repository.save(question);
        ADQuestion questionSaved = repository.fetch(buildQueryOneValue());

        assertEquals (questionSaved.getCode(), question.getCode());
        assertEquals (questionSaved.getQuestion(), question.getQuestion());
        assertEquals (questionSaved.getAnswer1(), question.getAnswer1());
        assertEquals (questionSaved.getAnswer2(), question.getAnswer2());
        assertEquals (questionSaved.getAnswer3(), question.getAnswer3());
        assertEquals (questionSaved.getAnswer4(), question.getAnswer4());
        assertEquals (questionSaved.getCorrect(), question.getCorrect());

        assertEquals (questionSaved.getSeason(), question.getSeason());
        assertEquals (questionSaved.getEpisode(), question.getEpisode());

        assertEquals (questionSaved.getLanguage(), question.getLanguage());

        assertEquals (questionSaved.getSerieCode(), question.getSerieCode());
    }

    @Test
    public void testSaveAndFetchEntities(){
        List<ADQuestion> entities = createQuestionList();

        RealmRepository<ADQuestion, ADQuestionDAO> repository = new RealmRepository<>(ADQuestion.class, ADQuestionDAO.class);

        repository.save(entities);
        List<ADQuestion> entitiesSaved = repository.fetchAll(buildQueryAll());

        for(int i = 0; i < entities.size(); i++){
            assertEquals (entities.get(i).getCode(), entitiesSaved.get(i).getCode());
            assertEquals (entities.get(i).getQuestion(), entitiesSaved.get(i).getQuestion());
            assertEquals (entities.get(i).getAnswer1(), entitiesSaved.get(i).getAnswer1());
            assertEquals (entities.get(i).getAnswer2(), entitiesSaved.get(i).getAnswer2());
            assertEquals (entities.get(i).getAnswer3(), entitiesSaved.get(i).getAnswer3());
            assertEquals (entities.get(i).getAnswer4(), entitiesSaved.get(i).getAnswer4());
            assertEquals (entities.get(i).getCorrect(), entitiesSaved.get(i).getCorrect());

            assertEquals (entities.get(i).getSeason(), entitiesSaved.get(i).getSeason());
            assertEquals (entities.get(i).getEpisode(), entitiesSaved.get(i).getEpisode());

            assertEquals (entities.get(i).getLanguage(), entitiesSaved.get(i).getLanguage());

            assertEquals (entities.get(i).getSerieCode(), entitiesSaved.get(i).getSerieCode());
        }
    }

    @Test
    public void testUpdateAndFetchEntity(){
        ADQuestion question = updateQuestion();

        RealmRepository<ADQuestion, ADQuestionDAO> repository = new RealmRepository<>(ADQuestion.class, ADQuestionDAO.class);

        repository.update(question);
        ADQuestion questionSaved = repository.fetch(buildQueryOneValue());

        question = createQuestion();

        assertEquals (questionSaved.getCode(), question.getCode());
        assertEquals (questionSaved.getQuestion(), question.getQuestion());
        assertEquals (questionSaved.getAnswer1(), question.getAnswer1());
        assertEquals (questionSaved.getAnswer2(), question.getAnswer2());
        assertEquals (questionSaved.getAnswer3(), question.getAnswer3());
        assertEquals (questionSaved.getAnswer4(), question.getAnswer4());
        assertEquals (questionSaved.getCorrect(), question.getCorrect());

        assertEquals (questionSaved.getSeason(), question.getSeason());
        assertEquals (questionSaved.getEpisode(), question.getEpisode());

        assertEquals (questionSaved.getLanguage(), question.getLanguage());

        assertNotEquals (questionSaved.getSerieCode(), question.getSerieCode());
    }

    @Test
    public void testUpdateAndFetchEntities(){
        List<ADQuestion> entities = updateQuestionList();

        RealmRepository<ADQuestion, ADQuestionDAO> repository = new RealmRepository<>(ADQuestion.class, ADQuestionDAO.class);

        repository.update(entities);
        List<ADQuestion> entitiesSaved = repository.fetchAll(buildQueryAll());

        entities = createQuestionList();

        for(int i = 0; i < entities.size(); i++){
            assertEquals (entities.get(i).getCode(), entitiesSaved.get(i).getCode());
            assertNotEquals (entities.get(i).getQuestion(), entitiesSaved.get(i).getQuestion());
            assertEquals (entities.get(i).getAnswer1(), entitiesSaved.get(i).getAnswer1());
            assertEquals (entities.get(i).getAnswer2(), entitiesSaved.get(i).getAnswer2());
            assertEquals (entities.get(i).getAnswer3(), entitiesSaved.get(i).getAnswer3());
            assertEquals (entities.get(i).getAnswer4(), entitiesSaved.get(i).getAnswer4());
            assertEquals (entities.get(i).getCorrect(), entitiesSaved.get(i).getCorrect());

            assertEquals (entities.get(i).getSeason(), entitiesSaved.get(i).getSeason());
            assertEquals (entities.get(i).getEpisode(), entitiesSaved.get(i).getEpisode());

            assertEquals (entities.get(i).getLanguage(), entitiesSaved.get(i).getLanguage());

            assertEquals (entities.get(i).getSerieCode(), entitiesSaved.get(i).getSerieCode());
        }
    }

    @Test
    public void testDeleteAndFetchEntity(){
        RealmRepository<ADQuestion, ADQuestionDAO> repository = new RealmRepository<>(ADQuestion.class, ADQuestionDAO.class);

        repository.delete(buildQueryOneValue());
        ADQuestion entityDeleted = repository.fetch(buildQueryOneValue());

        assertNull (entityDeleted);
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

    private ADQuestion updateQuestion() {
        ADQuestion question = createQuestion();
        question.setSerieCode(1);

        return question;
    }

    private List<ADQuestion> createQuestionList(){
        List<ADQuestion> entities = new ArrayList<>();

        for(int i = 0; i < 10; i++) {
            ADQuestion question = createQuestion();

            question.setCode(1 + i);
            entities.add(question);
        }

        return entities;
    }

    private List<ADQuestion> updateQuestionList(){
        List<ADQuestion> entities = createQuestionList();

        for(int i = 0; i < entities.size(); i++) {
            entities.get(i).setQuestion(entities.get(i).getQuestion() + i);
        }

        return entities;
    }

    private RealmQuery<ADQuestionDAO> buildQueryOneValue() {
        RealmQuery<ADQuestionDAO> query;
        Realm realm = Realm.getDefaultInstance();

        query = realm.where(ADQuestionDAO.class);
        query.equalTo(ADQuestionSchema.code, 1);

        return query;
    }

    private RealmQuery<ADQuestionDAO> buildQueryAll() {
        RealmQuery<ADQuestionDAO> query;
        Realm realm = Realm.getDefaultInstance();

        query = realm.where(ADQuestionDAO.class).sort(ADQuestionSchema.code);

        return query;
    }
}
