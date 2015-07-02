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
    private static final String LOCATION = "com.schlock.website.deploy";

    private static final String WEBDIR_PARAM = "webdirectory.location";

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
        if (StringUtils.equalsIgnoreCase(location, LOCAL))
        {
            return LOCAL;
        }
        return HOSTED;
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
}
