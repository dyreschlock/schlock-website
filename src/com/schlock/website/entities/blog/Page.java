package com.schlock.website.entities.blog;

import org.apache.commons.lang.StringUtils;

public class Page extends AbstractPost
{
    public static final String ABOUT_ME_UUID = "aboutme";
    public static final String CLUB_UUID = "club";
    public static final String LESSON_PLANS_UUID = "lessons";
    public static final String PROJECTS_UUID = "projects";
    public static final String ERROR_PAGE_UUID = "errorpage";


    public boolean isPage()
    {
        return true;
    }


    public boolean isClub()
    {
        return StringUtils.equalsIgnoreCase(getUuid(), CLUB_UUID);
    }

    public boolean isAlt()
    {
        return StringUtils.equalsIgnoreCase(getUuid(), LESSON_PLANS_UUID);
    }

    public boolean isProjects()
    {
        return StringUtils.equalsIgnoreCase(getUuid(), PROJECTS_UUID);
    }
}
