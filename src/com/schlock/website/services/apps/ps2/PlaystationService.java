package com.schlock.website.services.apps.ps2;

import com.schlock.website.entities.apps.ps2.PlaystationGame;

public interface PlaystationService
{
    void updateGameInventory();

    void readConfigFiles() throws Exception;

    void writeConfigFiles() throws Exception;

    void writeArtFilesToLocal() throws Exception;

    void copyLocalFilesToDrive() throws Exception;

    void updateGameSaveFiles();

    String getSaveFileLink(PlaystationGame game);
}
