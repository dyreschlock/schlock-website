package com.schlock.website.services.apps.pocket.impl;

import com.schlock.website.entities.apps.pocket.PocketGame;
import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.apps.pocket.PocketImageService;

public class PocketImageServiceImpl extends GameImageServiceImpl<PocketGame> implements PocketImageService
{
    public PocketImageServiceImpl(DeploymentContext context)
    {
        super(context);
    }

    protected String displayName(PocketGame game)
    {
        return game.getTitle();
    }

    protected String uniqueNameUsedForGameSelection(PocketGame game)
    {
        if (game.isOnMister())
        {
            return game.getUniqueName();
        }
        return null;
    }

    protected String imageLink(PocketGame game)
    {
        final String IMG_LINK = context.webDomain() + "img/pocket/%s/%s.bmp";

        String platform = game.getPlatformName();
        String filehash = game.getFileHash();

        String link = String.format(IMG_LINK, platform, filehash);
        return link;
    }

    protected String imageFilepath(PocketGame game)
    {
        final String IMG_FILE_PATH = context.webOutputDirectory() + "/img/pocket/%s/%s.bmp";

        String imageFilepath = String.format(IMG_FILE_PATH, game.getPlatform(), game.getFileHash());
        return imageFilepath;
    }
}
