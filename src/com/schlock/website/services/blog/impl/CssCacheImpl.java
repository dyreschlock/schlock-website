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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CssCacheImpl implements CssCache
{
    private final static List<String> PRIMARY_CSS_FILES =
            Arrays.asList("layout/primary.less");

    private final static List<String> SECONDARY_CSS_FILES =
            Arrays.asList("layout/secondary.less", "layout/layout.css");

    //apps
    private final static String NOT_FIBBAGE_CSS_FILE = "layout/apps/notfibbage.less";
    private final static String GAMES_CSS_FILE = "layout/apps/games.less";
    private final static String POKEMON_CSS_FILE = "layout/apps/pokemon.less";


    private final Context context;

    private String cachedPrimary;
    private String cachedSecondary;

    private String cachedNotFibbage;

    public CssCacheImpl(Context context)
    {
        this.context = context;
    }

    public String getPrimaryCss()
    {
        if (StringUtils.isBlank(cachedPrimary))
        {
            cachedPrimary = createCss(PRIMARY_CSS_FILES);
        }
        return cachedPrimary;
    }

    public String getSecondaryCss()
    {
        if (StringUtils.isBlank(cachedSecondary))
        {
            cachedSecondary = createCss(SECONDARY_CSS_FILES);
        }
        return cachedSecondary;
    }

    public String getAllCss()
    {
        String primary = getPrimaryCss();
        String secondary = getSecondaryCss();

        return primary + secondary;
    }

    public String getCssForNotFibbage()
    {
        if (StringUtils.isBlank(cachedNotFibbage))
        {
            List<String> files = new ArrayList<String>();

            files.addAll(PRIMARY_CSS_FILES);
            files.addAll(SECONDARY_CSS_FILES);
            files.add(NOT_FIBBAGE_CSS_FILE);

            cachedNotFibbage = createCss(files);
        }
        return cachedNotFibbage;
    }

    public String getCssForGames()
    {
        List<String> files = Arrays.asList(GAMES_CSS_FILE);

        return createCss(files);
    }

    public String getCssForPokemon()
    {
        List<String> files = Arrays.asList(POKEMON_CSS_FILE);

        return createCss(files);
    }

    private String createCss(final List<String> cssFiles)
    {
        String css = "";

        try
        {
            for (String file : cssFiles)
            {
                css += getFileAsString(file);
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        css = convertLessToCss(css);
        css = StringUtils.remove(css, "\\n");

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
                css += StringUtils.trim(line);

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
