package com.schlock.website.services.blog.impl;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.services.blog.JavaScriptCache;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.services.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class JavaScriptCacheImpl implements JavaScriptCache
{
    private final static String EXTRA_JS_FILE = "js/%s.js";


    private final Context context;

    public JavaScriptCacheImpl(Context context)
    {
        this.context = context;
    }

    public boolean isHasCustomJavascript(AbstractPost post)
    {
        String filepath = String.format(EXTRA_JS_FILE, post.getUuid());
        File javascript = context.getRealFile(filepath);
        return javascript.exists();
    }

    public String getCustomJavascript(AbstractPost post)
    {
        return getCustomJavascript(post.getUuid());
    }

    public String getCustomJavascript(String uuid)
    {
        String filepath = String.format(EXTRA_JS_FILE, uuid);
        return getFileAsString(filepath);
    }

    private String getFileAsString(String path)
    {
        try
        {
            String css = "";

            File resource = context.getRealFile(path);
            if (resource.exists())
            {
                BufferedReader io = new BufferedReader(new FileReader(resource));

                String line = io.readLine();
                while (line != null)
                {
                    css += StringUtils.trim(line);

                    line = io.readLine();
                }
            }
            return css;
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
