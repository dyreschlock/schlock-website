package com.schlock.website.services.apps.ps2;

import com.schlock.website.entities.apps.ps2.RetroGame;

public interface RetroConsoleService
{
    void updateGameInventory();

    void updateGameSaveFiles();

    String getSaveFileLink(RetroGame game);

    void writeArtFilesToLocal();
}
