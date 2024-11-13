package com.schlock.website.pages.sitemap;

import com.schlock.website.services.DateFormatter;
import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.blog.SitemapManagement;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.Date;
import java.util.List;

public class SiteMapIndex
{
    @Inject
    private SitemapManagement sitemap;

    @Inject
    private DateFormatter dateFormatter;

    @Inject
    private DeploymentContext context;


    @Property
    private String currentPage;


    public String getDomain()
    {
        return context.webDomain();
    }


    @Persist
    private String currentTime;

    public String getCurrentTime()
    {
        if (currentTime == null)
        {
            currentTime = dateFormatter.w3DateFormat(new Date());
        }
        return currentTime;
    }


    public List<String> getAllPostsAndPages()
    {
        return sitemap.getPostsAndPages();
    }

    public List<String> getDirectoryPages()
    {
        return sitemap.getDirectoryPages();
    }
}
