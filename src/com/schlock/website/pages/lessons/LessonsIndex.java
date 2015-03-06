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

import java.util.Arrays;
import java.util.List;

public class LessonsIndex
{
    private static final String TITLE_SUFFIX = "-title";

    private static final String DEFAULT_YEAR = LessonsManagement.HEISEI26;
    private static final String DEFAULT_GRADE = null;

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
        this.selectedGrade = DEFAULT_GRADE;
        this.selectedYear = DEFAULT_YEAR;

        lessonManagement.resetPostCache();
        return true;
    }

    Object onActivate(String p1)
    {
        this.selectedGrade = lessonManagement.getGrade(p1);
        this.selectedYear = lessonManagement.getYear(p1);

        if (StringUtils.isBlank(this.selectedGrade))
        {
            this.selectedGrade = DEFAULT_GRADE;
        }
        if (StringUtils.isBlank(this.selectedYear))
        {
            this.selectedYear = DEFAULT_YEAR;
        }

        lessonManagement.resetPostCache();
        return true;
    }

    Object onActivate(String p1, String p2)
    {
        this.selectedGrade = lessonManagement.getGrade(p1, p2);
        this.selectedYear = lessonManagement.getYear(p1, p2);

        if (StringUtils.isBlank(this.selectedGrade))
        {
            this.selectedGrade = DEFAULT_GRADE;
        }
        if (StringUtils.isBlank(this.selectedYear))
        {
            this.selectedYear = DEFAULT_YEAR;
        }

        lessonManagement.resetPostCache();
        return true;
    }

    Object onActivate(String p1, String p2, String p3)
    {
        this.selectedGrade = lessonManagement.getGrade(p1, p2, p3);
        this.selectedYear = lessonManagement.getYear(p1, p2);

        if (StringUtils.isNotBlank(this.selectedYear))
        {
            //get lesson

            //get post

            //redirect to post
        }
        else
        {
            this.selectedYear = DEFAULT_YEAR;
        }

        lessonManagement.resetPostCache();
        return true;
    }


    public List<String> getYears()
    {
        return lessonManagement.getYears();
    }

    public List<String> getTotalGrades()
    {
        return lessonManagement.getGrades();
    }


    public String getFourColumnExtraCss()
    {
        String css = "";
        if ((currentIndex + 1) % 4 == 0)
        {
            css += " fourColumnLast";
        }
        return css;
    }

    public String getSixColumnExtraCss()
    {
        String css = "";
        if ((currentIndex + 1) % 6 == 0)
        {
            css += " sixColumnLast";
        }
        return css;
    }

    public String getYearCssClass()
    {
        String css = currentYear;
        if (!StringUtils.equalsIgnoreCase(currentYear, selectedYear))
        {
            css += "Unselected";
        }
        return css;
    }

    public String getGradeCssClass()
    {
        String css = currentGrade;
        if (StringUtils.isNotBlank(selectedGrade) &&
                !StringUtils.equalsIgnoreCase(currentGrade, selectedGrade))
        {
            css += "Unselected";
        }
        return css;
    }

    Object onSelectYear(String year)
    {
        this.selectedYear = year;

        return postZone;
    }

    private static final String NULL_VALUE = "null";

    Object onSelectGrade(String grade)
    {
        if (StringUtils.equalsIgnoreCase(grade, NULL_VALUE))
        {
            this.selectedGrade = null;
        }
        else
        {
            this.selectedGrade = grade;
        }
        return postZone;
    }

    public String getCurrentGradeValue()
    {
        if (StringUtils.equalsIgnoreCase(selectedGrade, currentGrade))
        {
            return NULL_VALUE;
        }
        return currentGrade;
    }

    public String getYearTitle()
    {
        return getTitle(currentYear + TITLE_SUFFIX);
    }

    public String getGradeTitle()
    {
        return getTitle(currentGrade + TITLE_SUFFIX);
    }


    public List<String> getGrades()
    {
        if (StringUtils.isNotBlank(selectedGrade))
        {
            return Arrays.asList(selectedGrade);
        }
        return lessonManagement.getGrades();
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
