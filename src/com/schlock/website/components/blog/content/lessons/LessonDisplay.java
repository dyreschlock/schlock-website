package com.schlock.website.components.blog.content.lessons;

import com.schlock.website.entities.blog.LessonPost;
import com.schlock.website.pages.Index;
import com.schlock.website.services.blog.LessonsManagement;
import com.schlock.website.services.blog.PostManagement;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import java.util.List;

public class LessonDisplay
{
    @Parameter(required = true)
    private String grade;

    @Parameter(required = true)
    private String lesson;

    @Parameter(required = true)
    private String year;


    @Parameter
    private boolean usePostName = false;


    @Inject
    private PostManagement postManagement;

    @Inject
    private LessonsManagement lessonManagement;

    @Inject
    private Messages messages;



    public boolean isYearlyItem()
    {
        List<String> yearlyItems = lessonManagement.getYearlyItems(grade);
        for (String item : yearlyItems)
        {
            if (StringUtils.equalsIgnoreCase(item, lesson))
            {
                return true;
            }
        }
        return false;
    }

    public boolean isHasLessonPlan()
    {
        LessonPost post = getPost();
        return post != null &&
                StringUtils.isNotBlank(post.getLessonPlanLink());
    }

    public boolean isHasFlashcards()
    {
        LessonPost post = getPost();
        return post != null &&
                StringUtils.isNotBlank(post.getFlashCardsLink());
    }

    public boolean isHasPost()
    {
        LessonPost post = getPost();
        return post != null &&
                post.isVisible();
    }

    public LessonPost getPost()
    {
        return lessonManagement.getPost(lesson, grade, year);
    }


    public String getLessonTitle()
    {
        if (usePostName)
        {
            LessonPost post = getPost();
            if (post != null)
            {
                String html = postManagement.wrapJapaneseTextInTags(post.getTitle());
                return html;
            }
        }
        return getTitle(lesson);
    }

    public String getLessonYear()
    {
        return getTitle(year);
    }

    public String getDownloadMessage()
    {
        String key = "flash-cards";
        if (isYearlyItem())
        {
            key = "download";
        }
        return getTitle(key);
    }


    private String getTitle(String nlsKey)
    {
        String message = messages.get(nlsKey);
        return postManagement.wrapJapaneseTextInTags(message);
    }
}
