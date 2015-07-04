package com.schlock.website.components.apps.notfibbage.content.player;

import com.schlock.website.services.apps.notfibbage.NotFibbageManagement;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;

public class PlayerRegisterPhase
{
    @Inject
    private NotFibbageManagement management;

    @Parameter(required = true)
    private String playerName;

}
