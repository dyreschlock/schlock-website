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

    private static final String WEBDIR_PARAM = "webdirectory.location";
    private static final String DATADIR_PARAM = "datadirectory.location";

    private static final String OUT_DIR_HTML_PARAM = "output.directory.html";
    private static final String OUT_DIR_PHOTO_PARAM = "output.directory.photos";

    private static final String PS_DRIVE_PARAM = "playstation.directory.drive";
    private static final String PS_DATA_PARAM = "playstation.directory.data";
    private static final String PS_LOCAL_PARAM = "playstation.directory.local";

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
            InputStream in = getClass().getResourceAsStream("/deploy.properties");

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
        return deployProperties;
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

    public String imageLocation()
    {
        return webDirectory() + IMAGE_DIR;
    }

    public String photoLocation()
    {
        return webDirectory() + PHOTO_DIR;
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

    public String imageOutputDirectory()
    {
        String outputDirectory = getDeployProperties().getProperty(OUT_DIR_PHOTO_PARAM);
        return outputDirectory;
    }

    public String webOutputDirectory()
    {
        String outoutDirectory = getDeployProperties().getProperty(OUT_DIR_HTML_PARAM);
        return outoutDirectory;
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
        String playstationLocal = getDeployProperties().getProperty(PS_LOCAL_PARAM);
        return playstationLocal;
    }

    public String webOutputDirectoryImageFolder()
    {
        String outputDirectory = getDeployProperties().getProperty(OUT_DIR_HTML_PARAM);
        return outputDirectory + "/" + IMG_DIR;
    }

    public String coverImageLocationLocal()
    {
        String outputDirectory = getDeployProperties().getProperty(OUT_DIR_HTML_PARAM);
        return outputDirectory + "/" + COVER_DIR;
    }

    public String coverImageLocationInternet()
    {
        String domain = getDeployProperties().getProperty(WEB_DOMAIN);
        return domain + COVER_DIR;
    }
}
