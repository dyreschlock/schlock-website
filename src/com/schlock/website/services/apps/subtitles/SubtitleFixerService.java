package com.schlock.website.services.apps.subtitles;

public interface SubtitleFixerService
{
    public String offsetSubtitles(String originalText, int offsetInSeconds);
}
