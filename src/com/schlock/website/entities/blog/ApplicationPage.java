package com.schlock.website.entities.blog;

public class ApplicationPage extends AbstractPost
{
    public static final String SUBTITLES_UUID = "subtitles";
    public static final String CODEJAM_MAY2012_UUID = "codejam-may-2012";

    public static final String KENDO_UUID = "kendo-written-exam";

    public boolean isPage()
    {
        return true;
    }
}
