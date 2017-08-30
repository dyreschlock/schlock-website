package com.schlock.website.entities.apps.games;

import com.schlock.website.entities.Persisted;

public class VideoGameConsole extends Persisted
{
    private String name;
    private String code;

    public VideoGameConsole()
    {
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getCode() { return code; }

    public void setCode(String code) { this.code = code; }
}
