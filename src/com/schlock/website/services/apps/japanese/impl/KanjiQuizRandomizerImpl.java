package com.schlock.website.services.apps.japanese.impl;

import com.schlock.website.entities.apps.japanese.Kanji;
import com.schlock.website.services.apps.japanese.KanjiQuizRandomizer;
import com.schlock.website.services.database.apps.japanese.KanjiDAO;

import java.util.ArrayList;
import java.util.List;

public class KanjiQuizRandomizerImpl implements KanjiQuizRandomizer
{
    private static final Integer LOWEST_LESSON_NUMBER = 1;
    private static final Integer CURRENT_TEST_SIZE = 12;

    private KanjiDAO kanjiDAO;

    public KanjiQuizRandomizerImpl(KanjiDAO kanjiDAO)
    {
        this.kanjiDAO = kanjiDAO;
    }

    public List<String> getKanjiList()
    {
        return getKanjiList(LOWEST_LESSON_NUMBER);
    }


    public List<String> getKanjiList(int lesson)
    {
        List<String> list = new ArrayList<String>();

        List<Kanji> items = kanjiDAO.getAllFromLesson(lesson);
        while (list.size() < CURRENT_TEST_SIZE)
        {
            double random = Math.random();
            int selection = (int) (random * items.size());

            String kanji = items.get(selection).getText();

            if (!list.contains(kanji))
            {
                list.add(kanji);
            }
        }
        return list;


//        List<String> list = new ArrayList<String>();
//
//        list.add("六月");
//        list.add("七日");
//        list.add("日本");
//        list.add("人");
//        list.add("男の人");
//        list.add("今日");
//        list.add("一万");
//
//        list.add("見る");
//        list.add("行く");
//        list.add("食べる");
//        list.add("飲む");
//        list.add("元気");
//
//        return list;
    }
}
