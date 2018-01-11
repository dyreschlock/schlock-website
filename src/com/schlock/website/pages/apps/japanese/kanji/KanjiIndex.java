package com.schlock.website.pages.apps.japanese.kanji;

import com.schlock.website.services.apps.japanese.KanjiQuizRandomizer;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

public class KanjiIndex
{
    @Inject
    private KanjiQuizRandomizer kanjiService;

    @Inject
    private Messages messages;


    private List<String> questions;

    @Property
    private String question;

    @Property
    private Integer questionIndex;


    public List<String> getQuestions()
    {
        if(questions == null)
        {
            questions = kanjiService.getKanjiList();
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
