package com.schlock.website.services.blog.impl;

import com.schlock.website.entities.blog.Page;
import com.schlock.website.entities.blog.Post;
import com.schlock.website.pages.Index;
import com.schlock.website.services.blog.ImageManagement;
import com.schlock.website.services.blog.JavaScriptCache;
import com.schlock.website.services.blog.MapLocationManagement;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.services.PageRenderLinkSource;

import java.util.List;

public class MapLocationManagementImpl implements MapLocationManagement
{
    private final JavaScriptCache javaScriptCache;
    private final ImageManagement imageManagement;
    private final PostDAO postDAO;
    private final PageRenderLinkSource linkSource;

    public MapLocationManagementImpl(JavaScriptCache javaScriptCache,
                                     ImageManagement imageManagement,
                                     PostDAO postDAO,
                                     PageRenderLinkSource linkSource)
    {
        this.javaScriptCache = javaScriptCache;
        this.imageManagement = imageManagement;
        this.postDAO = postDAO;
        this.linkSource = linkSource;
    }

    public String generateMapJavascript()
    {
        StringBuilder script = new StringBuilder();

        script.append(javaScriptCache.getCustomJavascript(Page.MAP_UUID));


        String MARKER = "var marker%s = L.marker([%s]).addTo(map); marker%s.bindPopup('<p><b>%s</b></p><p><img src=\"%s\" /></p><p><a href=\"%s\">View Post</a></p>', {maxWidth : 220});";

        List<Post> posts = postDAO.getAllPublishedWithMapLocation();
        for(int i = 0; i < posts.size(); i++)
        {
            Post post = posts.get(0);
            String location = post.getMapLocation();

            String title = post.getTitle();
            String coverImage = imageManagement.getPostPreviewMetadataLink(post.getUuid());

            String link = linkSource.createPageRenderLinkWithContext(Index.class, post.getUuid()).toURI();


            String marker = String.format(MARKER, i, location, i, title, coverImage, link);
            script.append(marker);
        }
        return script.toString();
    }
}
