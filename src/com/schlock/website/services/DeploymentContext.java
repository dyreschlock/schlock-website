package com.schlock.website.services;

public interface DeploymentContext
{
    public static final String IMAGE_DIR = "image/";
    public static final String PHOTO_DIR = "photo/";

    public boolean isLocal();

    public boolean isAcceptedUrlReferrer(String referrer);

    public String getHibernateProperty(String name);

    public String photoLocation();

    public String imageLocation();
}
