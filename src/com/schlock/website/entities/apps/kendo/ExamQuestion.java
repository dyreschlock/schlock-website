package com.schlock.website.entities.apps.kendo;

import com.schlock.website.entities.Persisted;

public class ExamQuestion extends Persisted
{
    public static final int LEVEL_SHODAN = 1;
    public static final int LEVEL_NIDAN = 2;
    public static final int LEVEL_SANDAN = 3;

    private ExamCategory category;
    private int number;

    private int level;

    private String japaneseTitle;
    private String englishTitle;

    private String japaneseQuestion;
    private String englishQuestion;

    private String japaneseAnswer;
    private String englishAnswer;



    public ExamCategory getCategory()
    {
        return category;
    }

    public void setCategory(ExamCategory category)
    {
        this.category = category;
    }

    public int getNumber()
    {
        return number;
    }

    public void setNumber(int number)
    {
        this.number = number;
    }

    public int getLevel()
    {
        return level;
    }

    public void setLevel(int level)
    {
        this.level = level;
    }

    public String getJapaneseTitle()
    {
        return japaneseTitle;
    }

    public void setJapaneseTitle(String japaneseTitle)
    {
        this.japaneseTitle = japaneseTitle;
    }

    public String getEnglishTitle()
    {
        return englishTitle;
    }

    public void setEnglishTitle(String englishTitle)
    {
        this.englishTitle = englishTitle;
    }

    public String getEnglishAnswer()
    {
        return englishAnswer;
    }

    public void setEnglishAnswer(String englishAnswer)
    {
        this.englishAnswer = englishAnswer;
    }

    public String getEnglishQuestion()
    {
        return englishQuestion;
    }

    public void setEnglishQuestion(String englishQuestion)
    {
        this.englishQuestion = englishQuestion;
    }

    public String getJapaneseAnswer()
    {
        return japaneseAnswer;
    }

    public void setJapaneseAnswer(String japaneseAnswer)
    {
        this.japaneseAnswer = japaneseAnswer;
    }

    public String getJapaneseQuestion()
    {
        return japaneseQuestion;
    }

    public void setJapaneseQuestion(String japaneseQuestion)
    {
        this.japaneseQuestion = japaneseQuestion;
    }
}
