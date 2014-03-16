package com.schlock.website.services;

public interface DeploymentContext
{
    public boolean isLocal();

    public boolean isAcceptedUrlReferrer(String referrer);

    public String getHibernateProperty(String name);
}
