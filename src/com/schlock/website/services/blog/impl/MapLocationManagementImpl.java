package com.schlock.website.services.blog.impl;

import com.schlock.website.entities.blog.LocationType;
import com.schlock.website.entities.blog.Page;
import com.schlock.website.entities.blog.Post;
import com.schlock.website.pages.Index;
import com.schlock.website.services.DateFormatter;
import com.schlock.website.services.blog.ImageManagement;
import com.schlock.website.services.blog.JavaScriptCache;
import com.schlock.website.services.blog.MapLocationManagement;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.services.PageRenderLinkSource;

import java.util.List;

public class MapLocationManagementImpl implements MapLocationManagement
{
    private final DateFormatter dateFormatter;
    private final JavaScriptCache javaScriptCache;
    private final ImageManagement imageManagement;
    private final PostDAO postDAO;
    private final PageRenderLinkSource linkSource;
    private final Messages messages;

    public MapLocationManagementImpl(DateFormatter dateFormatter,
                                     JavaScriptCache javaScriptCache,
                                     ImageManagement imageManagement,
                                     PostDAO postDAO,
                                     PageRenderLinkSource linkSource,
                                     Messages messages)
    {
        this.dateFormatter = dateFormatter;
        this.javaScriptCache = javaScriptCache;
        this.imageManagement = imageManagement;
        this.postDAO = postDAO;
        this.linkSource = linkSource;
        this.messages = messages;
    }

    private final String DELIM = "/";


    public String generateMapJavascript()
    {
        String baseScript = javaScriptCache.getCustomJavascript(Page.MAP_UUID);

        String shadowIconPath = LocationType.getShadowIconPath();
        String declaration = generateLayerDeclaration();
        String markers = generateMapMarkers();

        return String.format(baseScript, shadowIconPath, declaration, markers);
    }

    private String generateMapMarkers()
    {
        StringBuilder script = new StringBuilder();

        String MARKER = "L.marker([%s],{icon: %sIcon}).bindPopup('" +
                            "<p><b>%s</b></p>" +
                            "<p>%s</p>" +
                            "<p><img src=\"%s\" /></p>" +
                            "<p><a href=\"%s\">View Post</a></p>" +
                        "', {maxWidth : 220})" +
                        ".addTo(%s);\r\n";

        List<Post> posts = postDAO.getAllPublishedWithMapLocation();
        for(Post post : posts)
        {
            String title = post.getTitle().replace("'", "\\\'");
            String date = dateFormatter.dateFormat(post.getCreated());
            String coverImage = imageManagement.getPostPreviewMetadataLink(post.getUuid());
            String link = linkSource.createPageRenderLinkWithContext(Index.class, post.getUuid()).toURI();
            String layer = post.getLocationType().name().toLowerCase();

            String location = post.getLocationCoords();
            String[] coords = location.split(DELIM);

            for(String coord: coords)
            {
                if (coord.contains(","))
                {
                    String marker = String.format(MARKER, coord, layer, title, date, coverImage, link, layer);
                    script.append(marker);
                }
            }
        }
        return script.toString();
    }

    private String generateLayerDeclaration()
    {
        StringBuilder declarations = new StringBuilder();
        StringBuilder overlays = new StringBuilder();
        StringBuilder icons = new StringBuilder();

        String DECLARE = "const %s =  L.layerGroup().addTo(map);\r\n";

        String OVERLAYS = "const overlays = {" +
                            "%s" +
                          "};\r\n";

        String OVERLAY_ENTRY = "'%s': %s";

        String ICON = "const %sIcon = new LeafIcon({iconUrl: '%s'});\r\n";

        for(LocationType type : LocationType.values())
        {
            String name = type.name().toLowerCase();
            String description = messages.get(name);

            String declare = String.format(DECLARE, name);
            declarations.append(declare);

            if (overlays.length() != 0)
            {
                overlays.append(",");
            }
            String overlay = String.format(OVERLAY_ENTRY, description, name);
            overlays.append(overlay);

            String iconUrl = type.getIconPath();
            String icon = String.format(ICON, name, iconUrl);
            icons.append(icon);
        }

        String overlay = String.format(OVERLAYS, overlays);
        return declarations + overlay + icons;
    }
}
