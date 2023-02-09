package com.schlock.website.services;

public interface DeploymentContext
{
    public static final String IMAGE_DIR = "image/";
    public static final String PHOTO_DIR = "photo/";

    public static final String MISC_DIR = "misc/";
    public static final String SPAMM_DIR = "spamm/";
    public static final String PAGE_DIR = "pages/";


    public boolean isLocal();

    public boolean isAcceptedUrlReferrer(String referrer);

    public boolean isAcceptedUserAgent(String userAgent);

    public String getHibernateProperty(String name);

    public String googleCredentialsFilepath();

    public String webDirectory();

    public String photoLocation();

    public String imageLocation();
}
