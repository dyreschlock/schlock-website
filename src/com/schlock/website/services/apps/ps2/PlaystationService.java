package com.schlock.website.services.apps.ps2;

public interface PlaystationService extends RetroConsoleService
{
    void readConfigFiles() throws Exception;

    void writeConfigFiles() throws Exception;

    void copyLocalFilesToDrive() throws Exception;
}
