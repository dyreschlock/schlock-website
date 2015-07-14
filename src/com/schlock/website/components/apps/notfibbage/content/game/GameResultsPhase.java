package com.schlock.website.components.apps.notfibbage.content.game;

import com.schlock.website.pages.apps.notfibbage.Game;
import com.schlock.website.services.apps.notfibbage.NotFibbageController;
import org.apache.tapestry5.ioc.annotations.Inject;

public class GameResultsPhase
{
    @Inject
    private NotFibbageController controller;



    Object onNext()
    {
        controller.next();

        return Game.class;
    }
}
