package com.schlock.website.entities.apps.notfibbage;

import com.schlock.website.entities.Persisted;

import java.util.Set;

public class NotFibbageQuestion extends Persisted
{
    private String text;
    private String answer;
    private Set<String> fakeAnswers;

    private NotFibbageCategory category;

    private Integer orderInCat;

    private String source;

    public NotFibbageQuestion()
    {
        Double number = 0.0;


    }


    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public String getAnswer()
    {
        return answer;
    }

    public void setAnswer(String answer)
    {
        this.answer = answer;
    }

    public Set<String> getFakeAnswers()
    {
        return fakeAnswers;
    }

    public void setFakeAnswers(Set<String> fakeAnswers)
    {
        this.fakeAnswers = fakeAnswers;
    }

    public NotFibbageCategory getCategory()
    {
        return category;
    }

    public void setCategory(NotFibbageCategory category)
    {
        this.category = category;
    }

    public String getSource()
    {
        return source;
    }

    public void setSource(String source)
    {
        this.source = source;
    }
}
