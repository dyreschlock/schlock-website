package com.schlock.website.services.apps.ps2.impl;

import com.schlock.website.entities.apps.ps2.PlaystationGame;
import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.apps.pocket.impl.GameImageServiceImpl;
import com.schlock.website.services.apps.ps2.PlaystationImageService;

public class PlaystationImageServiceImpl extends GameImageServiceImpl<PlaystationGame> implements PlaystationImageService
{
    public PlaystationImageServiceImpl(DeploymentContext context)
    {
        super(context);
    }

    protected String displayName(PlaystationGame game)
    {
        return game.getTitle();
    }

    protected String uniqueNameUsedForGameSelection(PlaystationGame game)
    {
        return null;
    }

    protected String imageLink(PlaystationGame game)
    {
        final String IMG_LINK = context.webDomain() + "img/ps2/%s";

        String link = String.format(IMG_LINK, game.getCoverImageFilename());
        return link;
    }

    protected String imageFilepath(PlaystationGame game)
    {
        final String IMG_FILE_PATH = context.webOutputDirectoryImageFolder() + "ps2/%s";

        String imageFilepath = String.format(IMG_FILE_PATH, game.getCoverImageFilename());
        return imageFilepath;
    }
}