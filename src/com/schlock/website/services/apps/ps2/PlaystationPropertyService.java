package com.schlock.website.services.apps.ps2;

import com.schlock.website.entities.apps.ps2.PlaystationGame;

import java.util.Properties;

public interface PlaystationPropertyService
{
    void loadPropertiesFromCFG(PlaystationGame game, Properties configuration);

    void writePropertiesFromGame(PlaystationGame game, Properties configuration);
}
