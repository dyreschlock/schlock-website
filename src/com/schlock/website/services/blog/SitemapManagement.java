package com.schlock.website.services.blog;

import java.util.List;

public interface SitemapManagement
{
    List<String> getAllUrlsToIndex();

    List<String> getPostsAndPages();

    List<String> getDirectoryPages();
}
