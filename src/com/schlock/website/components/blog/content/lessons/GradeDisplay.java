package com.schlock.website.components.blog.content.lessons;

import com.schlock.website.services.blog.LessonsManagement;
import com.schlock.website.services.blog.PostManagement;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

public class GradeDisplay
{
    private static final String TITLE_SUFFIX = "-title";


    @Parameter(required = true)
    @Property
    private String grade;

    @Parameter(required = true)
    @Property
    private String year;

    @Property
    private String currentLesson;


    @Inject
    private Messages messages;

    @Inject
    private PostManagement postManagement;

    @Inject
    private LessonsManagement lessonManagement;




    public boolean isFifthOrSixth()
    {
        return StringUtils.equalsIgnoreCase(LessonsManagement.SIXTH_GRADE, grade) ||
                StringUtils.equalsIgnoreCase(LessonsManagement.FIFTH_GRADE, grade);
    }

    public boolean isNotFifthOrSixth()
    {
        return !isFifthOrSixth();
    }

    public List<String> getLessons()
    {
        return lessonManagement.getLessons(grade, year);
    }

    public List<String> getYearlyItems()
    {
        return lessonManagement.getYearlyItems(grade);
    }

    public List<String> getSpecialLessons()
    {
        return lessonManagement.getSpecialLessons(grade, year);
    }


    public String getGradeTitle()
    {
        return getTitle(grade + TITLE_SUFFIX);
    }


    private String getTitle(String nlsKey)
    {
        String message = messages.get(nlsKey);
        return postManagement.wrapJapaneseTextInTags(message);
    }
}
