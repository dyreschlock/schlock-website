package com.schlock.website.services;

public interface DeploymentContext
{
    public static final String IMAGE_DIR = "image/";
    public static final String PHOTO_DIR = "photo/";
    public static final String COVER_DIR = "img/cover/";
    public static final String IMG_DIR = "img/";

    public static final String MISC_DIR = "misc/";
    public static final String SPAMM_DIR = "spamm/";
    public static final String PAGE_DIR = "pages/";


    public boolean isLocal();

    public boolean isAcceptedUrlReferrer(String referrer);

    public boolean isAcceptedUserAgent(String userAgent);

    public String getHibernateProperty(String name);

    public String googleCredentialsFilepath();

    public String webDomain();

    public String webDirectory();
    public String dataDirectory();

    public String photoLocation();

    public String imageLocation();

    public String imageOutputDirectory();
    public String webOutputDirectory();
    public String webOutputDirectoryImageFolder();

    public String playstationDriveDirectory();
    public String playstationDataDirectory();
    public String playstationLocalDirectory();

    public String coverImageLocationLocal();
    public String coverImageLocationInternet();
}
