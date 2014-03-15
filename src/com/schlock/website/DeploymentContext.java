package com.schlock.website;


import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.List;

public class DeploymentContext
{
    private static final String LOCATION = "com.schlock.website.location";

    private static final String LOCAL = "local";
    private static final String HOSTED = "hosted";


    private static final List<String> ACCEPTED_REFERRERS =
            Arrays.asList(
                    "theschlock.com",
                    "localhost"
            );


    public static boolean isLocal()
    {
        String location = System.getProperty(LOCATION);

        return StringUtils.equalsIgnoreCase(LOCAL, location);
    }

    public static boolean isAcceptedUrlReferrer(String referrer)
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
}
