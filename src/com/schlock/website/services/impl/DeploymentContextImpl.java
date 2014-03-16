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
    private static final String LOCATION = "com.schlock.website.location";

    private static final String LOCAL = "local";
    private static final String HOSTED = "hosted";


    private static final List<String> ACCEPTED_REFERRERS =
            Arrays.asList(
                    "theschlock.com",
                    "localhost"
            );

    private static DeploymentContextImpl INSTANCE;

    public static DeploymentContextImpl getInstance()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new DeploymentContextImpl();
        }
        return INSTANCE;
    }


    private Properties hibernateProperties;

    private Properties getHibernateProperties()
    {
        if(hibernateProperties == null)
        {
            InputStream in = getClass().getResourceAsStream("/hibernate.properties");

            hibernateProperties = new Properties();
            try
            {
                hibernateProperties.load(in);
            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }
        return hibernateProperties;
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
        String location = System.getProperty(LOCATION);

        return StringUtils.equalsIgnoreCase(LOCAL, location);
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

    public String getHibernateProperty(String name)
    {
        String context = getContext();
        return getHibernateProperties().getProperty(name + "." + context);
    }
}
