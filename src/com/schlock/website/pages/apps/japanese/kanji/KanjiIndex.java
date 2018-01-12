package com.schlock.website.pages.apps.japanese.kanji;

import com.schlock.website.services.apps.japanese.KanjiQuizRandomizer;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

public class KanjiIndex
{
    private static final String LESSON_FLAG_DEFAULT = null;
    private static final String ONLY_FLAG_DEFAULT = null;

    @Inject
    private KanjiQuizRandomizer kanjiService;

    @Inject
    private Messages messages;


    private List<String> questions;

    @Property
    private String question;

    @Property
    private Integer questionIndex;


    @Persist
    private String lessonFlag;

    @Persist
    private String onlyFlag;


    Object onActivate()
    {
        return onActivate(LESSON_FLAG_DEFAULT);
    }

    Object onActivate(String parameter)
    {
        return onActivate(parameter, ONLY_FLAG_DEFAULT);
    }

    Object onActivate(String p1, String p2)
    {
        this.lessonFlag = p1;
        this.onlyFlag = p2;

        return true;
    }


    public List<String> getQuestions()
    {
        if(questions == null)
        {
            questions = kanjiService.getKanjiList(lessonFlag, onlyFlag);
        }
        return questions;
    }


    public String getQuestionNumber()
    {
        String message = messages.get("number-format");
        String number = String.valueOf(questionIndex +1);

        return String.format(message, number);
    }
}
