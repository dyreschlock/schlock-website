package com.schlock.website.services.blog;

import java.util.List;

public interface SitemapManagement
{
    List<String> getAllUrls();

    List<String> getPostsAndPages();

    List<String> getDirectoryPages();
}
