package com.schlock.website.entities.blog;

public class LessonPost extends Post
{
    public static final String SIX_UUID_PREFIX = "6nen";
    public static final String FIVE_UUID_PREFIX = "5nen";
    public static final String THREE_FOUR_UUID_PREFIX = "34nen";
    public static final String ONE_TWO_UUID_PREFIX = "12nen";
    public static final String SPECIAL_UUID_PREFIX = "special";


    private String lessonPlanLink;
    private String flashCardsLink;


    public boolean isLessonPost()
    {
        return true;
    }


    public String getLessonPlanLink()
    {
        return lessonPlanLink;
    }

    public void setLessonPlanLink(String lessonPlanLink)
    {
        this.lessonPlanLink = lessonPlanLink;
    }

    public String getFlashCardsLink()
    {
        return flashCardsLink;
    }

    public void setFlashCardsLink(String flashCardsLink)
    {
        this.flashCardsLink = flashCardsLink;
    }
}
