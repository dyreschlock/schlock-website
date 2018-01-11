package com.schlock.website.entities.apps.japanese;

import com.schlock.website.entities.Persisted;

public class Kanji extends Persisted
{
    private String text;
    private int lesson;


    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public int getLesson()
    {
        return lesson;
    }

    public void setLesson(int lesson)
    {
        this.lesson = lesson;
    }
}
