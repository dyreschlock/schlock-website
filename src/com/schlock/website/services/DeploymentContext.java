package com.schlock.website.services;

public interface DeploymentContext
{
    String IMAGE_DIR = "image/";
    String PHOTO_DIR = "photo/";
    String COVER_DIR = "img/cover/";
    String IMG_DIR = "img/";

    String MISC_DIR = "misc/";
    String SPAMM_DIR = "spamm/";
    String PAGE_DIR = "pages/";

    String HTML_GITHUB_REPO = "dyreschlock.github.io";
    String PHOTOS_GITHUB_REPO = "dyreschlock.github.photos";
    String PS2_GITHUB_REPO = "ps2-opl-loadout";
    String POKEGM_GITHUB_REPO = "game_masters";

    boolean isLocal();

    boolean isAcceptedUrlReferrer(String referrer);

    boolean isAcceptedUserAgent(String userAgent);

    String getHibernateProperty(String name);

    String googleCredentialsFilepath();

    String webDomain();

    String webDirectory();
    String dataDirectory();
    String githubDirectory();

    String photoLocation();

    String imageLocation();

    String imageOutputDirectory();
    String webOutputDirectory();
    String webOutputDirectoryImageFolder();

    String pokemonLocalDirectory();

    String playstationDriveDirectory();
    String playstationDataDirectory();
    String playstationLocalDirectory();

    String coverImageLocationLocal();
    String coverImageLocationInternet();
}
