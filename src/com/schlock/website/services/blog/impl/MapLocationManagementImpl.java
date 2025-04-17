package com.schlock.website.services.blog.impl;

import com.schlock.website.entities.blog.Page;
import com.schlock.website.entities.blog.Post;
import com.schlock.website.pages.Index;
import com.schlock.website.services.DateFormatter;
import com.schlock.website.services.blog.ImageManagement;
import com.schlock.website.services.blog.JavaScriptCache;
import com.schlock.website.services.blog.MapLocationManagement;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.services.PageRenderLinkSource;

import java.util.List;

public class MapLocationManagementImpl implements MapLocationManagement
{
    private final DateFormatter dateFormatter;
    private final JavaScriptCache javaScriptCache;
    private final ImageManagement imageManagement;
    private final PostDAO postDAO;
    private final PageRenderLinkSource linkSource;

    public MapLocationManagementImpl(DateFormatter dateFormatter,
                                     JavaScriptCache javaScriptCache,
                                     ImageManagement imageManagement,
                                     PostDAO postDAO,
                                     PageRenderLinkSource linkSource)
    {
        this.dateFormatter = dateFormatter;
        this.javaScriptCache = javaScriptCache;
        this.imageManagement = imageManagement;
        this.postDAO = postDAO;
        this.linkSource = linkSource;
    }

    private final String DELIM = "/";


    public String generateMapJavascript()
    {
        String baseScript = javaScriptCache.getCustomJavascript(Page.MAP_UUID);

        String markers = generateMapMarkers();

        return String.format(baseScript, markers);
    }

    private String generateMapMarkers()
    {
        StringBuilder script = new StringBuilder();

        String MARKER = "var marker%s = " +
                            "L.marker([%s]).bindPopup('" +
                                "<p><b>%s</b></p>" +
                                "<p>%s</p>" +
                                "<p><img src=\"%s\" /></p>" +
                                "<p><a href=\"%s\">View Post</a></p>" +
                            "', {maxWidth : 220})" +
                            ".addTo(%s);\r\n";

        List<Post> posts = postDAO.getAllPublishedWithMapLocation();
        for (int i = 0; i < posts.size(); i++)
        {
            Post post = posts.get(i);

            String title = post.getTitle().replace("'", "\\\'");
            String date = dateFormatter.dateFormat(post.getCreated());
            String coverImage = imageManagement.getPostPreviewMetadataLink(post.getUuid());
            String link = linkSource.createPageRenderLinkWithContext(Index.class, post.getUuid()).toURI();
            String layer = post.getLocationType().name().toLowerCase();

            String location = post.getLocationCoords();
            String[] coords = location.split(DELIM);

            for (int j = 0; j < coords.length; j++)
            {
                String mark = i + "_" + j;
                String coord = coords[j];

                if (coord.contains(","))
                {
                    String marker = String.format(MARKER, mark, coord, title, date, coverImage, link, layer);
                    script.append(marker);
                }
            }
        }
        return script.toString();
    }
}
