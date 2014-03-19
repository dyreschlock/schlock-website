package com.schlock.website.services.blog;

import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.FilenameFilter;

public class PhotoFileFilter implements FilenameFilter
{
    public boolean accept(File folder, String s)
    {
        if (StringUtils.endsWith(s, ".jpg"))
        {
            String thumb = s.replace(".jpg", "_t.jpg");
            boolean hasThumb = folderContainsFile(folder, thumb);

            return !hasThumb;
        }
        return false;
    }

    private boolean folderContainsFile(File folder, String name)
    {
        for (File file : folder.listFiles())
        {
            if (StringUtils.equals(name, file.getName()))
            {
                return true;
            }
        }
        return false;
    }
}
