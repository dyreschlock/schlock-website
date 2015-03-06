package com.schlock.website.entities.blog;

public class LessonPost extends Post
{
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
