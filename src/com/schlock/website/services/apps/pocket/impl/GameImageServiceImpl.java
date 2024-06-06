package com.schlock.website.services.apps.pocket.impl;

import com.schlock.website.entities.apps.pocket.ImagedGame;
import com.schlock.website.services.DeploymentContext;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class GameImageServiceImpl<T extends ImagedGame>
{
    protected final DeploymentContext context;

    private static final Integer STANDARD_LINE_HEIGHT = 175;// 160;
    private static final Integer CELL_WIDTH = 863;

    protected GameImageServiceImpl(DeploymentContext context)
    {
        this.context = context;
    }

    protected abstract String displayName(T game);

    protected abstract String imageLink(T game);

    protected abstract String imageFilepath(T game);

    public String generateImageHTMLFromGames(List<T> games)
    {
        String outputHtml = "";

        List<Object[]> currentImages = new ArrayList<Object[]>();
        Integer currentTotalWidth = 0;

        for(T game : games)
        {
            Object[] imageEntry = createImageEntry(game);
            if (imageEntry != null)
            {
                currentImages.add(imageEntry);

                int width = (Integer) imageEntry[3];

                currentTotalWidth += width;
                if (currentTotalWidth > CELL_WIDTH)
                {
                    outputHtml += serializeImagesIntoHTML(currentImages, currentTotalWidth);
                    currentImages = new ArrayList<Object[]>();
                    currentTotalWidth = 0;
                }
            }
        }

        if (!currentImages.isEmpty())
        {
            outputHtml += serializeImagesIntoHTML(currentImages, currentTotalWidth);
        }
        return outputHtml;
    }

    private Object[] createImageEntry(T game)
    {
        String imageFilepath = imageFilepath(game);
        BufferedImage image;
        try
        {
            image = ImageIO.read(new File(imageFilepath));
        }
        catch(Exception e)
        {
            return null;
        }

        String displayName = displayName(game);
        String link = imageLink(game);

        Integer height = STANDARD_LINE_HEIGHT;
        Integer width = getScaledWidth(image);


        Object[] imageEntry = new Object[4];
        imageEntry[0] = displayName;
        imageEntry[1] = link;
        imageEntry[2] = height;
        imageEntry[3] = width;

        return imageEntry;
    }

    private int getScaledWidth(BufferedImage image)
    {
        Integer originalHeight = image.getHeight();
        Integer originalWidth = image.getWidth();

        Integer newHeight = STANDARD_LINE_HEIGHT;

        Double newWidth = originalWidth.doubleValue() * newHeight.doubleValue() / originalHeight.doubleValue();

        if (newWidth / newHeight.doubleValue() > 1.5d)
        {
            newWidth = newWidth * 0.93d;
        }
        if (newWidth / newHeight.doubleValue() < 0.80d)
        {
            newWidth = newWidth * 1.08d;
        }

        return newWidth.intValue();
    }

    private String serializeImagesIntoHTML(List<Object[]> images, Integer currentTotalWidth)
    {
        final String IMG_HTML = "<img height=\"%s\" width=\"%s\" src=\"%s\" alt=\"%s\" title=\"%s\" />";

        Double newHeight = STANDARD_LINE_HEIGHT.doubleValue() / currentTotalWidth.doubleValue() * CELL_WIDTH.doubleValue();
        boolean finalRow = false;
        if (currentTotalWidth < CELL_WIDTH)
        {
            newHeight = STANDARD_LINE_HEIGHT.doubleValue();
            finalRow = true;
        }

        String imageHTML = "";
        int imagesTotalWidth = 0;

        for(int i = 0; i < images.size(); i++)
        {
            Object[] image = images.get(i);

            String displayName = (String) image[0];
            String imgLink = (String) image[1];
            Integer originalHeight = (Integer) image[2];
            Integer originalWidth = (Integer) image[3];

            Double newWidth = originalWidth.doubleValue() / originalHeight.doubleValue() * newHeight;

            int height = newHeight.intValue();
            int width = newWidth.intValue();

            imagesTotalWidth += width;
            if (i == images.size() - 1 && !finalRow)
            {
                width += CELL_WIDTH.intValue() - imagesTotalWidth;
            }

            String imgTag = String.format(IMG_HTML, height, width, imgLink, displayName, displayName);

            imageHTML += imgTag;
        }
        return imageHTML;
    }

}
