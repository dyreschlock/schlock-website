package com.schlock.website.services.applications.subtitles;

public interface SubtitleFixerService
{
    public String offsetSubtitles(String originalText, int offsetInSeconds);
}
