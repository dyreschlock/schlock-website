package com.schlock.website.services.blog.impl;

import com.asual.lesscss.LessEngine;
import com.asual.lesscss.LessException;
import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.Page;
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

    private final static List<String> BLOG_UUID_WITH_EXTRA_CSS =
                                                    Arrays.asList(Page.ABOUT_ME_UUID,
                                                                    Page.PROJECTS_UUID,
                                                                    Page.CLUB_UUID,
                                                                    Page.ERROR_PAGE_UUID,
                                                                    AbstractPost.KENDO_UUID,
                                                                    NINTENDO_MUSEUM_UUID,
                                                                    "the-secret-of-crystania",
                                                                    "history-of-kiyomi-schools-part-2",
                                                                    "dune");

    private final static String EXTRA_CSS_FILE = "layout/extra/%s.less";

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

    public String getAllCss(AbstractPost post)
    {
        if (StringUtils.isBlank(cached))
        {
            cached = createCss(LESS_VARIABLES_FILE, PRIMARY_CSS_FILE, SECONDARY_CSS_FILE);
        }

        String css = cached;
        if (post != null)
        {
            css += getExtraCSS(post);
        }
        return css;
    }

    private String getExtraCSS(AbstractPost post)
    {
        if (post == null)
        {
            return "";
        }

        String uuid = post.getUuid();

        if (BLOG_UUID_WITH_EXTRA_CSS.contains(uuid))
        {
            String file = String.format(EXTRA_CSS_FILE, uuid);
            String extraCss = createExtraCss(file);

            if (NINTENDO_MUSEUM_UUID.equals(uuid))
            {
                extraCss = extraCss.replaceAll("unicode-range: -9", "unicode-range: U+0030-0039");
            }
            return extraCss;
        }

        if (post.isHasLessonLinks())
        {
            String file = String.format(EXTRA_CSS_FILE, Page.LESSON_PLANS_UUID);
            return createExtraCss(file);
        }

        return "";
    }

    private String createExtraCss(String file)
    {
        return createCss(LESS_VARIABLES_FILE, file);
    }

    public String getCssForNotFibbage()
    {
        if (StringUtils.isBlank(cachedNotFibbage))
        {
            cachedNotFibbage = createCss(LESS_VARIABLES_FILE, PRIMARY_CSS_FILE, SECONDARY_CSS_FILE, NOT_FIBBAGE_CSS_FILE);
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

    private String createCss(String... cssFiles)
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
