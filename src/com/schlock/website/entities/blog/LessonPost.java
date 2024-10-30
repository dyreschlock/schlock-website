package com.schlock.website.entities.blog;

public class LessonPost extends Post
{
    private CoursePage coursePage;

    private String lessonPlanLink;
    private String flashCardsLink;

    public boolean isLessonPost()
    {
        return true;
    }

    public CoursePage getCoursePage()
    {
        return coursePage;
    }

    public void setCoursePage(CoursePage coursePage)
    {
        this.coursePage = coursePage;
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
