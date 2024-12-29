package com.schlock.website.services.blog.impl;

import com.asual.lesscss.LessEngine;
import com.asual.lesscss.LessException;
import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.CoursePage;
import com.schlock.website.entities.blog.Page;
import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.blog.CssCache;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.services.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CssCacheImpl implements CssCache
{
    private final static String LESS_VARIABLES_FILE = "layout/variables.less";
    private final static String PRIMARY_CSS_FILE = "layout/primary.less";
    private final static String SECONDARY_CSS_FILE = "layout/secondary.less";

    //extras
    private final static String NINTENDO_MUSEUM_UUID = "nintendo-museum";
    private final static String CULTURE_SHOCK_UUID = "culture-shock";
    private final static String GHIBLI_PARK_UUID = "ghibli-park";

    private final static List<String> BLOG_UUID_WITH_EXTRA_CSS =
                                                    Arrays.asList(Page.ABOUT_ME_UUID,
                                                                    Page.PROJECTS_UUID,
                                                                    Page.CLUB_UUID,
                                                                    Page.ERROR_PAGE_UUID,
                                                                    Page.COURSE_LIST_UUID,
                                                                    Page.LESSON_PLANS_UUID,
                                                                    AbstractPost.KENDO_UUID,
                                                                    AbstractPost.SUBTITLES_UUID,
                                                                    NINTENDO_MUSEUM_UUID,
                                                                    CULTURE_SHOCK_UUID,
                                                                    GHIBLI_PARK_UUID,
                                                                    "the-secret-of-crystania",
                                                                    "history-of-kiyomi-schools-part-2",
                                                                    "dune");

    private final static String EXTRA_CSS_FILE = "layout/extra/%s.less";

    private final static String OLD_VERSION_CSS_FILE = "layout/old/%s.less";
    private final static String OLD_COMMON_CSS_FILE = "layout/old/old-common.less";

    //fonts
    private final static String EB_GARAMOND_FONT = "layout/font/EBGaramond.css";
    private final static String NOTO_SANS_FONT = "layout/font/NotoSansJP.css";
    private final static String PERFECT_DOS_VGA_FONT = "layout/font/PerfectDOS-VGA-437.css";

    private final static String IMAGES_CSS_FILES = "layout/images.less";

    //apps
    private final static String GAMES_CSS_FILE = "layout/apps/games.less";
    private final static String POKEMON_CSS_FILE = "layout/apps/pokemon.less";
    private final static String NOT_FIBBAGE_CSS_FILE = "layout/apps/notfibbage.less";

    private final static String TWITTER_CSS_FILE = "layout/apps/twitter.less";


    private final DeploymentContext deploymentContext;
    private final Context context;

    private String cached;

    private String cachedTwitter;
    private String cachedNotFibbage;
    private String cachedGames;
    private String cachedPokemon;

    private HashMap<String, String> cachedOld = new HashMap<>();

    public CssCacheImpl(DeploymentContext deploymentContext,
                        Context context)
    {
        this.deploymentContext = deploymentContext;
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

    public String getCssOldVersions(AbstractPost post, SiteVersion version)
    {
        String v = version.name().toLowerCase();
        if (!cachedOld.containsKey(v))
        {
            String cssFile = String.format(OLD_VERSION_CSS_FILE, v);
            String css = createCss(IMAGES_CSS_FILES, OLD_COMMON_CSS_FILE, cssFile);

            cachedOld.put(v, css);
        }

        String css = cachedOld.get(v);
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

        if (NINTENDO_MUSEUM_UUID.equals(uuid))
        {
            String fontCss = getFileAsString(NOTO_SANS_FONT);
            String extraCss = createExtraCss(uuid).replaceAll("unicode-range:-9", "unicode-range:U+0030-0039");

            return fontCss + extraCss;
        }

        if (Page.ERROR_PAGE_UUID.equals(uuid))
        {
            return createExtraCss(PERFECT_DOS_VGA_FONT, uuid);
        }

        if (post.isHasLessonLinks())
        {
            return createExtraCss(Page.LESSON_PLANS_UUID);
        }

        if (post instanceof CoursePage)
        {
            return createExtraCss(Page.COURSE_LIST_UUID, Page.LESSON_PLANS_UUID);
        }

        //somewhat catch-all / individual special pages should come before this
        if (BLOG_UUID_WITH_EXTRA_CSS.contains(uuid))
        {
            return createExtraCss(uuid);
        }
        return "";
    }

    private String createExtraCss(String... filenames)
    {
        String[] files = new String[filenames.length+1];

        files[0] = LESS_VARIABLES_FILE;
        for(int i = 0; i < filenames.length; i++)
        {
            String file = filenames[i];
            if (!file.startsWith("layout"))
            {
                file = String.format(EXTRA_CSS_FILE, file);
            }
            files[i +1] = file;
        }
        return createCss(files);
    }

    public String getCssForTwitter()
    {
        if (StringUtils.isBlank(cachedTwitter))
        {
            cachedTwitter = createCss(TWITTER_CSS_FILE);
        }
        return cachedTwitter;
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
        if (StringUtils.isBlank(cachedGames))
        {
            cachedGames = createCss(IMAGES_CSS_FILES, GAMES_CSS_FILE);
        }
        return cachedGames;
    }

    public String getCssForPokemon()
    {
        if (StringUtils.isBlank(cachedPokemon))
        {
            cachedPokemon = createCss(POKEMON_CSS_FILE);
        }
        return cachedPokemon;
    }

    private String createCss(String... cssFiles)
    {
        boolean containsVariables = false;

        StringBuilder sb = new StringBuilder();
        for (String file : cssFiles)
        {
            if (LESS_VARIABLES_FILE.equals(file))
            {
                containsVariables = true;
            }

            String less = getFileAsString(file);
            if (IMAGES_CSS_FILES.equals(file))
            {
                String domain = deploymentContext.webDomain();

                less = String.format(less, domain, domain, domain);
            }

            sb.append(less);
        }

        String css = sb.toString();

        css = convertLessToCss(css);
        css = StringUtils.remove(css, "\\n");

        if (containsVariables)
        {
            String garamond = getFileAsString(EB_GARAMOND_FONT);
            css = garamond + css;
        }

        return css;
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
