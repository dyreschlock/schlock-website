package com.schlock.website.services.blog;

import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.FilenameFilter;

public class PhotoFileFilter implements FilenameFilter
{
    public boolean accept(File file, String s)
    {
        return StringUtils.endsWith(s, ".jpg");
    }
}
