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
    private final static String LESS_VARIABLES_FILE = "layout/variables.less";
    private final static String PRIMARY_CSS_FILE = "layout/primary.less";
    private final static String SECONDARY_CSS_FILE = "layout/secondary.less";

    //extras
    private final static String NINTENDO_MUSEUM_UUID = "nintendo-museum";
    private final static String NINTENDO_CSS_FILE = "layout/extra/nintendo-museum.less";

    private final static String ERROR_PAGE_UUID = "errorpage";
    private final static String ERROR_PAGE_CSS_FILE = "layout/extra/error.less";

    private final static String CLUB_PAGE_UUID = "club";
    private final static String CLUB_PAGE_CSS_FILE = "layout/extra/club.less";

    private final static String ABOUT_ME_UUID = "aboutme";
    private final static String ABOUT_ME_CSS_FILE = "layout/extra/aboutme.less";

    //apps
    private final static String NOT_FIBBAGE_CSS_FILE = "layout/apps/notfibbage.less";
    private final static String GAMES_CSS_FILE = "layout/apps/games.less";
    private final static String POKEMON_CSS_FILE = "layout/apps/pokemon.less";


    private final Context context;

    private String cached;

    private String cachedNotFibbage;

    public CssCacheImpl(Context context)
    {
        this.context = context;
    }

    public String getAllCss(String blogPostUUid)
    {
        if (StringUtils.isBlank(cached))
        {
            cached = createCss(Arrays.asList(LESS_VARIABLES_FILE, PRIMARY_CSS_FILE, SECONDARY_CSS_FILE));
        }

        String extra = getExtraCSS(blogPostUUid);

        return cached + extra;
    }

    private String getExtraCSS(String blogPostUUid)
    {
        String extraCss = "";
        if (NINTENDO_MUSEUM_UUID.equals(blogPostUUid))
        {
            extraCss = createCss(NINTENDO_CSS_FILE);
            extraCss = extraCss.replaceAll("unicode-range: -9", "unicode-range: U+0030-0039");
        }
        else if (ERROR_PAGE_UUID.equals(blogPostUUid))
        {
            extraCss = createCss(ERROR_PAGE_CSS_FILE);
        }
        else if(CLUB_PAGE_UUID.equals(blogPostUUid))
        {
            extraCss = createCss(CLUB_PAGE_CSS_FILE);
        }
        else if(ABOUT_ME_UUID.equals(blogPostUUid))
        {
            extraCss = createCss(ABOUT_ME_CSS_FILE);
        }
        return extraCss;
    }

    public String getCssForNotFibbage()
    {
        if (StringUtils.isBlank(cachedNotFibbage))
        {
            List<String> files = Arrays.asList(LESS_VARIABLES_FILE, PRIMARY_CSS_FILE, SECONDARY_CSS_FILE, NOT_FIBBAGE_CSS_FILE);
            cachedNotFibbage = createCss(files);
        }
        return cachedNotFibbage;
    }

    public String getCssForGames()
    {
        return createCss(GAMES_CSS_FILE);
    }

    public String getCssForPokemon()
    {
        return createCss(POKEMON_CSS_FILE);
    }

    private String createCss(String file)
    {
        return createCss(Arrays.asList(file));
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
            String css = engine.compile(less);
            return css;
        }
        catch (LessException e)
        {
            throw new RuntimeException(e);
        }
    }
}
