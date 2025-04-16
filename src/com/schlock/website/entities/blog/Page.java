package com.schlock.website.entities.blog;

import org.apache.commons.lang.StringUtils;

public class Page extends AbstractPost
{
    public static final String ABOUT_ME_UUID = "aboutme";
    public static final String CLUB_UUID = "club";
    public static final String COURSE_LIST_UUID = "courses";
    public static final String POST_ARCHIVE_UUID = "archive";
    public static final String PAGE_ARCHIVE_UUID = "page";

    public static final String KEYWORD_CLOUD_UUID = "keyword";
    public static final String LESSON_PLANS_UUID = "lessons";
    public static final String PROJECTS_UUID = "projects";
    public static final String PHOTO_UUID = "photo";
    public static final String MAP_UUID = "map";
    public static final String TODAYS_POSTS_UUID = "today";

    public static final String ERROR_PAGE_UUID = "errorpage";


    public boolean isPage()
    {
        return true;
    }


    public boolean isClub()
    {
        return StringUtils.equalsIgnoreCase(getUuid(), CLUB_UUID);
    }

    public boolean isCourseRelated()
    {
        return StringUtils.equalsIgnoreCase(getUuid(), LESSON_PLANS_UUID) ||
                StringUtils.equalsIgnoreCase(getUuid(), COURSE_LIST_UUID);
    }

    public boolean isProjects()
    {
        return StringUtils.equalsIgnoreCase(getUuid(), PROJECTS_UUID);
    }
}
