package com.schlock.website.services.impl;


import com.schlock.website.services.DeploymentContext;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class DeploymentContextImpl implements DeploymentContext
{
    private static final String WEB_DOMAIN = "web.domain";

    private static final String LOCATION = "com.schlock.website.deploy";

    private static final String GOOGLE_CREDENTIALS_FILEPATH = "google.credentials.filepath";
    private static final String INDEXNOW_API_KEY = "indexnow.api.key";

    private static final String WEBDIR_PARAM = "webdirectory.location";
    private static final String DATADIR_PARAM = "datadirectory.location";
    private static final String GITHUBDIR_PARAM = "githubdirectory.location";

    private static final String PS_DRIVE_PARAM = "playstation.directory.drive";
    private static final String PS_DATA_PARAM = "playstation.directory.data";

    private static final String DC_DRIVE_PARAM = "dreamcast.directory.drive";
    private static final String DC_DATA_PARAM = "dreamcast.directory.data";

    private static final String GAME_BOXART_URL_PARAM = "boxart.source.url";

    private static final String GITHUB_HTML_PARAM = "github.html.repo";
    private static final String GITHUB_PHOTOS_PARAM = "github.photos.repo";
    private static final String GITHUB_PS2_PARAM = "github.ps2.repo";
    private static final String GITHUB_POKEMON_PARAM = "github.pokemon.repo";

    private static final String DISCORD_WEBHOOK_PARAM = "discord.webhook";

    private static final String CACHING_POKEMON_RAIDCOUNTERS = "cache.pokemon.raidcounters";

    private static final String LOCAL = "local";
    private static final String HOSTED = "hosted";


    private static final List<String> ACCEPTED_REFERRERS =
            Arrays.asList(
                    "theschlock.com",
                    "localhost",
                    "jhendricks.local",
                    "feedly.com",
                    "facebook.com"
            );

    private static final List<String> ACCEPTED_USER_AGENTS =
            Arrays.asList(
                    "facebook.com",
                    "visionutils"
            );


    private Properties deployProperties;

    private Properties getDeployProperties()
    {
        if(deployProperties == null)
        {
            loadProperties("/deploy.properties");
        }
        return deployProperties;
    }

    private void loadProperties(String file)
    {
        InputStream in = getClass().getResourceAsStream(file);

        deployProperties = new Properties();
        try
        {
            deployProperties.load(in);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }


    private String getContext()
    {
        String location = System.getProperty(LOCATION);
        if (StringUtils.equalsIgnoreCase(location, HOSTED))
        {
            return HOSTED;
        }
        return LOCAL;
    }

    public boolean isLocal()
    {
        String context = getContext();
        return StringUtils.equalsIgnoreCase(LOCAL, context);
    }

    public boolean isAcceptedUrlReferrer(String referrer)
    {
        for (String host : ACCEPTED_REFERRERS)
        {
            if (StringUtils.containsIgnoreCase(referrer, host))
            {
                return true;
            }
        }
        return false;
    }

    public boolean isAcceptedUserAgent(String userAgent)
    {
        for (String agent : ACCEPTED_USER_AGENTS)
        {
            if (StringUtils.containsIgnoreCase(userAgent, agent))
            {
                return true;
            }
        }
        return false;
    }

    public String getHibernateProperty(String name)
    {
        String context = getContext();
        return getDeployProperties().getProperty(name + "." + context);
    }


    public String webDomain()
    {
        return getDeployProperties().getProperty(WEB_DOMAIN);
    }

    public String googleCredentialsFilepath()
    {
        return getDeployProperties().getProperty(GOOGLE_CREDENTIALS_FILEPATH);
    }

    public String indexnowApiKey()
    {
        return getDeployProperties().getProperty(INDEXNOW_API_KEY);
    }

    public String imageLocation()
    {
        return webDirectory() + IMAGE_DIR;
    }

    public String photoLocation()
    {
        return webDirectory() + PHOTO_DIR;
    }

    public String photoExLocation()
    {
        return webDirectory() + PHOTO_EX_DIR;
    }

    public String webDirectory()
    {
        String context = getContext();
        return getDeployProperties().getProperty(WEBDIR_PARAM + "." + context);
    }

    public String dataDirectory()
    {
        String context = getContext();
        return getDeployProperties().getProperty(DATADIR_PARAM + "." + context);
    }

    public String githubDirectory()
    {
        String context = getContext();
        return getDeployProperties().getProperty(GITHUBDIR_PARAM + "." + context);
    }

    public String imageOutputDirectory()
    {
        String photoRepo = getDeployProperties().getProperty(GITHUB_PHOTOS_PARAM);
        return githubDirectory() + photoRepo;
    }

    public String webOutputDirectory()
    {
        String htmlRepo = getDeployProperties().getProperty(GITHUB_HTML_PARAM);
        return githubDirectory() + htmlRepo;
    }

    public String pokemonLocalDirectory()
    {
        String pokemonRepo = getDeployProperties().getProperty(GITHUB_POKEMON_PARAM);
        return githubDirectory() + pokemonRepo;
    }

    public String playstationDriveDirectory()
    {
        String playstationDrive = getDeployProperties().getProperty(PS_DRIVE_PARAM);
        return playstationDrive;
    }

    public String playstationDataDirectory()
    {
        String playstationData = getDeployProperties().getProperty(PS_DATA_PARAM);
        return playstationData;
    }

    public String playstationLocalDirectory()
    {
        String ps2Repo = getDeployProperties().getProperty(GITHUB_PS2_PARAM);
        return githubDirectory() + ps2Repo + "/";
    }

    public String dreamcastDriveDirectory()
    {
        String dreamcastDrive = getDeployProperties().getProperty(DC_DRIVE_PARAM);
        return dreamcastDrive;
    }

    public String dreamcastDataDirectory()
    {
        String dreamcastData = getDeployProperties().getProperty(DC_DATA_PARAM);
        return dreamcastData;
    }

    public String gameBoxartSourceUrl()
    {
        String boxartUrl = getDeployProperties().getProperty(GAME_BOXART_URL_PARAM);
        return boxartUrl;
    }

    public String webOutputDirectoryImageFolder()
    {
        return webOutputDirectory() + "/" + IMG_DIR;
    }

    public String coverImageLocationLocal()
    {
        return webOutputDirectory() + "/" + COVER_DIR;
    }

    public String coverImageLocationInternet()
    {
        String domain = getDeployProperties().getProperty(WEB_DOMAIN);
        return domain + COVER_DIR;
    }

    public String discordWebhookURL()
    {
        return getDeployProperties().getProperty(DISCORD_WEBHOOK_PARAM);
    }


    public boolean isCachingPokemonRaidCounters()
    {
        String value = getDeployProperties().getProperty(CACHING_POKEMON_RAIDCOUNTERS);
        return StringUtils.equalsIgnoreCase("true", value);
    }
}
