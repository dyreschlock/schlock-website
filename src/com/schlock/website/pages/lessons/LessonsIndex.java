package com.schlock.website.pages.lessons;

import com.schlock.website.entities.blog.Page;
import com.schlock.website.services.blog.LessonsManagement;
import com.schlock.website.services.blog.PostManagement;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

public class LessonsIndex
{
    private static final String TITLE_SUFFIX = "-title";
    private static final String YEAR_SUFFIX = "-year";

    private static final String DEFAULT_YEAR = LessonsManagement.HEISEI26;

    @Inject
    private PostDAO postDAO;

    @Inject
    private PostManagement postManagement;

    @Inject
    private LessonsManagement lessonManagement;

    @Inject
    private Messages messages;


    @Persist
    private String selectedGrade;

    @Persist
    @Property
    private String selectedYear;


    @Property
    private int currentIndex;

    @Property
    private String currentYear;

    @Property
    private String currentGrade;


    @InjectComponent
    private Zone postZone;


    Object onActivate()
    {
        this.selectedGrade = null;
        this.selectedYear = DEFAULT_YEAR;

        lessonManagement.resetPostCache();
        return true;
    }

    Object onActivate(String p1)
    {
        this.selectedGrade = lessonManagement.getGradeFromParameters(p1);
        this.selectedYear = lessonManagement.getYearFromParameters(p1);

        if (StringUtils.isBlank(this.selectedYear))
        {
            this.selectedYear = DEFAULT_YEAR;
        }

        lessonManagement.resetPostCache();
        return true;
    }

    Object onActivate(String p1, String p2)
    {
        this.selectedGrade = lessonManagement.getGradeFromParameters(p1, p2);
        this.selectedYear = lessonManagement.getYearFromParameters(p1, p2);

        if (StringUtils.isBlank(this.selectedYear))
        {
            this.selectedYear = DEFAULT_YEAR;
        }

        lessonManagement.resetPostCache();
        return true;
    }


    public List<String> getTotalYears()
    {
        return lessonManagement.getTotalYears();
    }



    public String getExtraCss()
    {
        String css = "";
        if ((currentIndex + 1) % 4 == 0)
        {
            css += " fourColumnLast";
        }
        return css;
    }

    Object onSelectYear(String year)
    {
        this.selectedYear = year;

        return postZone;
    }

    public String getYearTitle()
    {
        return getTitle(currentYear + TITLE_SUFFIX);
    }


    public List<String> getGrades()
    {
        return lessonManagement.getGrades(selectedGrade);
    }

    private String getTitle(String nlsKey)
    {
        String message = messages.get(nlsKey);
        return postManagement.wrapJapaneseTextInTags(message);
    }



    private Page cachedPage;

    public Page getPage()
    {
        if (cachedPage == null)
        {
            cachedPage = (Page) postDAO.getByUuid(Page.LESSON_PLANS_UUID);
        }
        return cachedPage;
    }
}
