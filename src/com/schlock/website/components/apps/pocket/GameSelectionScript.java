package com.schlock.website.components.apps.pocket;

import com.schlock.website.services.DeploymentContext;
import org.apache.tapestry5.ioc.annotations.Inject;

public class GameSelectionScript
{
    @Inject
    private DeploymentContext context;

    public String getWebhookURL()
    {
        return context.discordWebhookURL();
    }
}
