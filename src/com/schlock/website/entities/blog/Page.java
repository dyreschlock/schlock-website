package com.schlock.website.entities.blog;

import org.apache.commons.lang.StringUtils;

public class Page extends AbstractPost
{
    public static final String ABOUT_ME_UUID = "about-me";
    public static final String CLUB_UUID = "club";
    public static final String ALT_MATERIALS_UUID = "alt-materials";

    public static final String SUBTITLES_UUID = "subtitles";


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
        return StringUtils.equalsIgnoreCase(getUuid(), ALT_MATERIALS_UUID);
    }
}
