package com.schlock.website.services.blog.impl;

import com.asual.lesscss.LessEngine;
import com.asual.lesscss.LessException;
import com.schlock.website.services.blog.CssCache;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.services.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class CssCacheImpl implements CssCache
{
    private final static List<String> CSS_FILES =
            Arrays.asList("layout/blog.less", "layout/layout.css");

    private final Context context;

    private String cachedCss;

    public CssCacheImpl(Context context)
    {
        this.context = context;
    }

    public String getCss()
    {
        if (StringUtils.isBlank(cachedCss))
        {
            cachedCss = createCss();
        }
        return cachedCss;
    }


    private String createCss()
    {
        String css = "";

        try
        {
            for (String file : CSS_FILES)
            {
                css += getFileAsString(file);
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        css = convertLessToCss(css);

        return css;
    }

    private String getFileAsString(String path) throws IOException
    {
        String css = "";

        File resource = context.getRealFile(path);
        if (resource.exists())
        {
            BufferedReader io = new BufferedReader(new FileReader(resource));

            String line = io.readLine();
            while (line != null)
            {
                css += line;
                css += "\r\n";

                line = io.readLine();
            }
        }
        return css;
    }

    private String convertLessToCss(String less)
    {
        LessEngine engine = new LessEngine();
        try
        {
            String css = engine.compile(less, true);
            return css;
        }
        catch (LessException e)
        {
            throw new RuntimeException(e);
        }
    }
}
