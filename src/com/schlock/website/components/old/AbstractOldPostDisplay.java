package com.schlock.website.components.old;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.Image;
import com.schlock.website.services.blog.ImageManagement;

import java.util.List;

public abstract class AbstractOldPostDisplay
{
    public String getImagesTableHTML(final ImageManagement imageManagement,
                                     final AbstractPost post)
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