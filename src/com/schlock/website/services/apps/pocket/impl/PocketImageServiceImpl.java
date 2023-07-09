package com.schlock.website.services.apps.pocket.impl;

import com.schlock.website.entities.apps.pocket.PocketGame;
import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.apps.pocket.PocketImageService;
import org.apache.commons.lang.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PocketImageServiceImpl implements PocketImageService
{
    private static final String IMG_LINK = "https://raw.githubusercontent.com/dyreschlock/dyreschlock.github.io/main/img/pocket/%s/%s.bmp";
    private static final String IMG_HTML_OLD = "<img src=\"%s\" alt=\"%s\" title=\"%s\" />";
    private static final String IMG_HTML = "<img height=\"%s\" width=\"%s\" src=\"%s\" alt=\"%s\" title=\"%s\" />";

    private final DeploymentContext context;

    private static final Integer STANDARD_LINE_HEIGHT = 145;
    private static final Integer CELL_WIDTH = 863;



    public PocketImageServiceImpl(DeploymentContext context)
    {
        this.context = context;
    }



    public String generateImageHTMLFromGames(List<PocketGame> games)
    {
        String outputHtml = "";

        List<Object[]> currentImages = new ArrayList<Object[]>();
        Integer currentTotalWidth = 0;

        for(PocketGame game : games)
        {
            if (StringUtils.isNotBlank(game.getFileHash()))
            {
                Object[] imageEntry = createImageEntry(game);
                currentImages.add(imageEntry);

                int width = (Integer) imageEntry[2];

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

    private Object[] createImageEntry(PocketGame game)
    {
        final String IMG_FILE_PATH = context.webOutputDirectory() + "/img/pocket/%s/%s.bmp";
        String imageFilepath = String.format(IMG_FILE_PATH, game.getPlatform(), game.getFileHash());

        BufferedImage image;
        try
        {
            image = ImageIO.read(new File(imageFilepath));
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }

        Integer height = STANDARD_LINE_HEIGHT;
        Integer width = getScaledWidth(image);

        Object[] imageEntry = new Object[3];
        imageEntry[0] = game;
        imageEntry[1] = height;
        imageEntry[2] = width;

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

            PocketGame game = (PocketGame) image[0];
            Integer originalHeight = (Integer) image[1];
            Integer originalWidth = (Integer) image[2];

            Double newWidth = originalWidth.doubleValue() / originalHeight.doubleValue() * newHeight;

            String platform = game.getPlatform();
            String filehash = game.getFileHash();
            String name = game.getGameName();

            String link = String.format(IMG_LINK, platform, filehash);

            int height = newHeight.intValue();
            int width = newWidth.intValue();

            imagesTotalWidth += width;
            if (i == images.size() - 1 && !finalRow)
            {
                width += CELL_WIDTH.intValue() - imagesTotalWidth;
            }

            String imgTag = String.format(IMG_HTML, height, width, link, name, name);

            imageHTML += imgTag;
        }
        return imageHTML;
    }

    public String generateImageHTMLFromGames2(List<PocketGame> games)
    {
        String outputHTML = "";
        for(PocketGame game : games)
        {
            String filehash = game.getFileHash();
            if (StringUtils.isNotBlank(filehash))
            {
                String platform = game.getPlatform();
                String link = String.format(IMG_LINK, platform, filehash);

                String name = game.getGameName();

                outputHTML += String.format(IMG_HTML_OLD, link, name, name);
            }
        }
        return outputHTML;
    }
}
