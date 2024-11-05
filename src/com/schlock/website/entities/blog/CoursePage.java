package com.schlock.website.entities.blog;

import com.schlock.website.services.blog.LessonsManagement;
import org.apache.commons.lang.StringUtils;

public class CoursePage extends Page
{
    private static final String SIXTH = "6th";
    private static final String FIFTH = "5th";
    private static final String THIRD_FOURTH = "3rd-4th";
    private static final String FIRST_SECOND = "1st-2nd";

    private static final String ENGLISH_COMM = "english-comm";

    public boolean isCoursePage()
    {
        return true;
    }

    public boolean isLessonCourse()
    {
        return StringUtils.endsWithIgnoreCase(getUuid(), ENGLISH_COMM) &&
                StringUtils.startsWithAny(getUuid(), new String[]{SIXTH, FIFTH, THIRD_FOURTH, FIRST_SECOND});
    }

    public String getGrade()
    {
        if (isLessonCourse())
        {
            if (StringUtils.startsWithIgnoreCase(getUuid(), SIXTH))
            {
                return LessonsManagement.SIXTH_GRADE;
            }
            if (StringUtils.startsWithIgnoreCase(getUuid(), FIFTH))
            {
                return LessonsManagement.FIFTH_GRADE;
            }
            if (StringUtils.startsWithIgnoreCase(getUuid(), THIRD_FOURTH))
            {
                return LessonsManagement.THIRD_GRADE + LessonsManagement.CONJUNCTION + LessonsManagement.FOURTH_GRADE;
            }
            if (StringUtils.startsWithIgnoreCase(getUuid(), FIRST_SECOND))
            {
                return LessonsManagement.FIRST_GRADE + LessonsManagement.CONJUNCTION + LessonsManagement.SECOND_GRADE;
            }
        }
        return null;
    }

    public String getYear()
    {
        return LessonsManagement.FAKE_YEAR;
    }
}
