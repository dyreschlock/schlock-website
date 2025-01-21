package com.schlock.website.services.apps.ps2.impl;

import com.schlock.website.entities.apps.ps2.RetroGame;
import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.apps.pocket.impl.GameImageServiceImpl;
import com.schlock.website.services.apps.ps2.RetroGameImageService;

public class RetroGameImageServiceImpl extends GameImageServiceImpl<RetroGame> implements RetroGameImageService
{
    public RetroGameImageServiceImpl(DeploymentContext context)
    {
        super(context);
    }

    protected String displayName(RetroGame game)
    {
        return game.getTitle();
    }

    protected String uniqueNameUsedForGameSelection(RetroGame game)
    {
        return null;
    }

    protected String imageLink(RetroGame game)
    {
        final String IMG_LINK = context.webDomain() + "img/%s/%s";

        String folder = game.getPlatform().gameImageFolder();
        String filename = game.getCoverImageFilename();

        String link = String.format(IMG_LINK, folder, filename);
        return link;
    }

    protected String imageFilepath(RetroGame game)
    {
        final String IMG_FILE_PATH = context.webOutputDirectoryImageFolder() + "%s/%s";

        String folder = game.getPlatform().gameImageFolder();
        String filename = game.getCoverImageFilename();

        String imageFilepath = String.format(IMG_FILE_PATH, folder, filename);
        return imageFilepath;
    }
}