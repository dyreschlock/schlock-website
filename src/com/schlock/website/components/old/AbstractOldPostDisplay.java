package com.schlock.website.components.old;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.Image;
import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.services.DateFormatter;
import com.schlock.website.services.blog.ImageManagement;
import com.schlock.website.services.blog.PostManagement;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import java.util.List;

public abstract class AbstractOldPostDisplay
{
    @Parameter(required = true)
    @Property
    private AbstractPost post;

    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private PostManagement postManagement;

    @Inject
    private ImageManagement imageManagement;

    @Inject
    private DateFormatter dateFormatter;

    abstract protected SiteVersion getVersion();



    public String getPostTitle()
    {
        return post.getTitle();
    }

    public String getPostBodyHTML()
    {
        String html = postManagement.generatePostHTML(post, getVersion());
        return html;
    }

    public boolean isHasDate()
    {
        return post != null && post.isPost() && post.getCreated() != null;
    }

    public String getPostDate()
    {
        return dateFormatter.shortDateFormat(post.getCreated());
    }

    public String getPostLink()
    {
        Class indexClass = getVersion().indexClass();
        String uuid = post.getUuid();

        return linkSource.createPageRenderLinkWithContext(indexClass, uuid).toURI();
    }

    public String getImagesTableHTML()
    {
        final String TR_START = "<tr align=\"center\" valign=\"middle\" bgcolor=\"#FFFFFF\"> ";
        final String TR_END = "</tr>";

        final String WIDTH = "16.5%";
        final String TD_ENTRY = "<td width=\"%s\" height=\"110\"><a href=\"%s\"><img class=\"galleryImage\" src=\"%s\" border=\"0\"></a></td>";

        final String TD_EMPTY = "<td width=\"17%\" height=\"110\"><font>&nbsp;</font></td>";

        final int COL_MAX = 6;


        StringBuilder sb = new StringBuilder();
        int col = 0;

        sb.append(TR_START);

        List<Image> images = imageManagement.getGalleryImages(post);
        for(Image image : images)
        {
            if (col == COL_MAX)
            {
                sb.append(TR_END).append(TR_START);
                col = 0;
            }

            String thumbnail = image.getImageLink();
            String link = image.getImageLink();
            if (image.getParent() != null)
            {
                link = image.getParent().getImageLink();
            }

            String td = String.format(TD_ENTRY, WIDTH, link, thumbnail);
            sb.append(td);

            col++;
        }
        while(col < COL_MAX)
        {
            sb.append(TD_EMPTY);
            col++;
        }
        sb.append(TR_END);

        return sb.toString();
    }
}
