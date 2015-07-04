package com.schlock.website.entities.apps.notfibbage;

import com.schlock.website.entities.Persisted;

public class NotFibbageQuestion extends Persisted
{
    private String text;
    private String answer;

    private NotFibbageCategory category;


    public NotFibbageQuestion()
    {
    }


    public String getAnswer()
    {
        return answer;
    }

    public void setAnswer(String answer)
    {
        this.answer = answer;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public NotFibbageCategory getCategory()
    {
        return category;
    }

    public void setCategory(NotFibbageCategory category)
    {
        this.category = category;
    }
}
