package com.schlock.website.services.apps.japanese.impl;

import com.schlock.website.entities.apps.japanese.Kanji;
import com.schlock.website.services.apps.japanese.KanjiQuizRandomizer;
import com.schlock.website.services.database.apps.japanese.KanjiDAO;

import java.util.ArrayList;
import java.util.List;

public class KanjiQuizRandomizerImpl implements KanjiQuizRandomizer
{
    private static final Integer LOWEST_LESSON_NUMBER = 3;
    private static final Integer CURRENT_TEST_SIZE = 15;

    private static final Boolean DEFAULT_ONLY = false;

    private KanjiDAO kanjiDAO;

    public KanjiQuizRandomizerImpl(KanjiDAO kanjiDAO)
    {
        this.kanjiDAO = kanjiDAO;
    }

    public List<String> getKanjiList(String lessonFlag, String onlyFlag)
    {
        int lesson = parseLessonFlag(lessonFlag);
        boolean only = parseOnlyFlag(onlyFlag);

        return kanjiList(lesson, only);
    }

    private List<String> kanjiList(int lesson, boolean only)
    {
        List<String> list = new ArrayList<String>();

        List<Kanji> items = kanjiDAO.getAllFromLesson(lesson, only);
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
    }

    private static final String LESSON_THREE = "three";
    private static final String LESSON_FOUR = "four";
    private static final String LESSON_FIVE = "five";
    private static final String LESSON_SIX = "six";
    private static final String LESSON_SEVEN = "seven";

    private int parseLessonFlag(String parameter)
    {
        if (parameter != null)
        {
            if (parameter.equals(LESSON_THREE))
            {
                return 3;
            }
            if (parameter.equals(LESSON_FOUR))
            {
                return 4;
            }
            if (parameter.equals(LESSON_FIVE))
            {
                return 5;
            }
            if (parameter.equals(LESSON_SIX))
            {
                return 6;
            }
            if (parameter.equals(LESSON_SEVEN))
            {
                return 7;
            }
        }
        return LOWEST_LESSON_NUMBER;
    }


    private static final String ONLY_FLAG = "only";

    private boolean parseOnlyFlag(String p)
    {
        return p != null && p.equalsIgnoreCase(ONLY_FLAG);
    }
}
