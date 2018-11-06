package com.quiz.series.tv.tvseriesquiz.model.mapper;

import android.content.Context;

import com.quiz.series.tv.tvseriesquiz.MyApp;
import com.quiz.series.tv.tvseriesquiz.model.firebase.entityJSON.ADEntityJSON;
import com.quiz.series.tv.tvseriesquiz.model.firebase.entityJSON.ADSerieJSON;
import com.quiz.series.tv.tvseriesquiz.model.realm.entityDAO.ADSerieDAO;
import com.quiz.series.tv.tvseriesquiz.utils.ADConstants;
import com.quiz.series.tv.tvseriesquiz.utils.DisplayUtils;
import com.quiz.series.tv.tvseriesquiz.utils.StringUtils;

import io.realm.RealmList;
import io.realm.RealmModel;

public class ADSerieJSONToSerieDAO {

    private Context context;

    public ADSerieJSONToSerieDAO(Context context) {
        this.context = context;
    }

    public ADSerieJSONToSerieDAO() {
        this.context = MyApp.getContext();
    }

    public RealmModel convertFirebaseObjectToDAO(ADEntityJSON object) {

        ADSerieJSON json = (ADSerieJSON)object;
        ADSerieDAO dao = new ADSerieDAO();

        //ADEntityDAO
        dao.setCode(json.getCode());

        dao.setName(json.getName());
        dao.setSeason(json.getSeasons());
        dao.setTotalEpisodes(json.getTotalEpisodes());

        if(!StringUtils.isNullOrEmpty(json.getListEpisode())){
            RealmList<Integer> list = new RealmList<>();

            String listEpisode = json.getListEpisode();
            String[] array = listEpisode.split(",");

            for(String number : array){
                list.add(Integer.valueOf(number));
            }

            dao.setListEpisode(list);
        }

        dao.setComplete(json.isComplete());
        dao.setStatus(json.getStatus());

        //check resolution image
        String resolution = DisplayUtils.getDensity(context);

        if (ADConstants.IMAGE1X_VALUE.equals(resolution)) {
            if (json.getUrlAvatar1x() != null) {
                dao.setUrlAvatar(json.getUrlAvatar1x());
                dao.setUrlImageBackground(json.getUrlImageBackground1x());
            }
        } else if(ADConstants.IMAGE15X_VALUE.equals(resolution)) {
            if (json.getUrlAvatar15x() != null) {
                dao.setUrlAvatar(json.getUrlAvatar15x());
                dao.setUrlImageBackground(json.getUrlImageBackground15x());
            }
        } else if(ADConstants.IMAGE2X_VALUE.equals(resolution)) {
            if (json.getUrlAvatar2x() != null) {
                dao.setUrlAvatar(json.getUrlAvatar2x());
                dao.setUrlImageBackground(json.getUrlImageBackground2x());
            }
        } else if(ADConstants.IMAGE3X_VALUE.equals(resolution)) {
            if (json.getUrlAvatar3x() != null) {
                dao.setUrlAvatar(json.getUrlAvatar3x());
                dao.setUrlImageBackground(json.getUrlImageBackground3x());
            }
        } else if(resolution.startsWith("0")) {
            if (json.getUrlAvatar1x() != null) {
                dao.setUrlAvatar(json.getUrlAvatar1x());
                dao.setUrlImageBackground(json.getUrlImageBackground1x());
            }
        } else {
            if (json.getUrlAvatar3x() != null) {
                dao.setUrlAvatar(json.getUrlAvatar3x());
                dao.setUrlImageBackground(json.getUrlImageBackground3x());
            }
        }

        return dao;
    }
}
