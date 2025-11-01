package com.schlock.website.services;

public interface DeploymentContext
{
    String IMAGE_DIR = "image/";
    String PHOTO_DIR = "photo/";
    String PHOTO_EX_DIR = "photo_ex/";
    String COVER_DIR = "assets/cover/";
    String IMG_DIR = "assets/";

    String MISC_DIR = "misc/";
    String SPAMM_DIR = "spamm/";
    String PAGE_DIR = "pages/";

    boolean isLocal();

    boolean isAcceptedUrlReferrer(String referrer);

    boolean isAcceptedUserAgent(String userAgent);

    String getHibernateProperty(String name);

    String googleCredentialsFilepath();
    String indexnowApiKey();

    String webDomain();

    String webDirectory();
    String dataDirectory();
    String githubDirectory();

    String photoLocation();
    String photoExLocation();

    String imageLocation();

    String blogContentInputDirectory();
    String imageOutputDirectory();
    String webOutputDirectory();
    String webOutputDirectoryImageFolder();

    String pokemonLocalDirectory();

    String memcardSavesOnlineDirectory();
    String memcardSavesLocalDirectory();

    String playstationDriveDirectory();
    String playstationDataDirectory();
    String playstationLocalDirectory();

    String dreamcastDriveDirectory();
    String dreamcastDataDirectory();

    String gamecubeDriveDirectory();
    String gamecubeDataDirectory();

    String gameBoxartSourceUrl();

    String coverImageLocationLocal();
    String coverImageLocationInternet();

    String discordWebhookURL();

    boolean isCachingPokemonRaidCounters();
}
