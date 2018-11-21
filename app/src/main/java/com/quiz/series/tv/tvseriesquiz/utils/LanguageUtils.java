package com.quiz.series.tv.tvseriesquiz.utils;

import android.content.res.Configuration;

import com.quiz.series.tv.tvseriesquiz.MyApp;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class LanguageUtils {

    static HashMap<String, String> languages = null;
    static String defaultLocale = null;
    static String currentLocale = null;
    static String language = null;

    public static void setPreferences(){
        String language = getLocale();

        List<String> language_country = Arrays.asList(language.split("_"));
        Locale locale = new Locale(language_country.get(0), language_country.get(1));
        Locale.setDefault(locale);

        Configuration config = new Configuration();
        config.locale = locale;
        MyApp.getContext().getResources().updateConfiguration(config, null);
    }

    public static String getLocale() {
        if (currentLocale == null) {
            currentLocale = MyApp.getContext().getResources().getConfiguration().locale.getLanguage() + "_" + MyApp.getContext().getResources().getConfiguration().locale.getCountry();
        }

        if (languages == null) {
            return currentLocale;
        } else {
            if (StringUtils.isNullOrEmpty(defaultLocale)) {
                return null;
            } else {
                return languages.containsKey(currentLocale) ? currentLocale : defaultLocale;
            }
        }
    }

    public static String getLanguage(){
        if(language == null){
            language = MyApp.getContext().getResources().getConfiguration().locale.getLanguage();
        }

        return language;
    }
}
