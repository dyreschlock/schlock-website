package com.schlock.website.services.apps.ps2;

public interface PlaystationService
{
    void createEntriesForGames(String driveName) throws Exception;

    void verifyGamesOnDrive(String driveName);

    void readConfigFiles() throws Exception;

    void writeConfigFiles() throws Exception;

    void copyConfigFilesToDrive(String driveName) throws Exception;
}
