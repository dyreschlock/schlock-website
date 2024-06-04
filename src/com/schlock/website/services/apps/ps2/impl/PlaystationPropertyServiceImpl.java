package com.schlock.website.services.apps.ps2.impl;

import com.schlock.website.entities.apps.ps2.PlaystationGame;
import com.schlock.website.services.apps.ps2.PlaystationPropertyService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class PlaystationPropertyServiceImpl implements PlaystationPropertyService
{
    private static final String TITLE = "Title";
    private static final String GENRE = "Genre";
    private static final String RELEASE_DATE = "Release";
    private static final String DEVELOPER = "Developer";
    private static final String PUBLISHER = "Publisher";

    private static final String RATING = "Rating";
    private static final String RATING_TEXT = "RatingText";

    private static final String PARENTAL = "Parental";
    private static final String PARENTAL_TEXT = "ParentalText";

    private static final String PLAYERS = "Players";
    private static final String PLAYERS_TEXT = "PlayersText";

    private static final String VMODE = "Vmode";
    private static final String VMODE_TEXT = "VmodeText";

    private static final String SCAN = "Scan";
    private static final String SCAN_TEXT = "ScanText";

    private static final String NOTES = "Notes";
    private static final String DESCRIPTION = "Description";

    private static final String CONFIG_VERSION = "CfgVersion";
    private static final String CONFIG_VERSION_VALUE = "8";
    private static final String CONFIG_SOURCE = "$ConfigSource";
    private static final String CONFIG_SOURCE_VALUE = "1";


    public void loadPropertiesFromCFG(PlaystationGame game, Properties configuration)
    {
        String title = configuration.getProperty(TITLE);
        if (game.getTitle() == null && title != null)
        {
            game.setTitle(title);
        }

        String genre = configuration.getProperty(GENRE);
        if (game.getGenre() == null && genre != null)
        {
            game.setGenre(genre);
        }

        String developer = configuration.getProperty(DEVELOPER);
        if (game.getDeveloper() == null && developer != null)
        {
            game.setDeveloper(developer);
        }

        String publisher = configuration.getProperty(PUBLISHER);
        if (game.getPublisher() == null && publisher != null)
        {
            game.setPublisher(publisher);
        }

        String dateText = configuration.getProperty(RELEASE_DATE);
        if (game.getReleaseDate() == null && dateText != null)
        {
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");  // 10-22-2001

            try
            {
                Date date = format.parse(dateText);
                game.setReleaseDate(date);
            }
            catch(Exception e)
            {
            }
        }
    }

    public void writePropertiesFromGame(PlaystationGame game, Properties configuration)
    {
        setProperty(configuration, TITLE, game.getTitle());
        setProperty(configuration, GENRE, game.getGenre());
        setProperty(configuration, DEVELOPER, game.getDeveloper());
        setProperty(configuration, PUBLISHER, game.getPublisher());

        setProperty(configuration, CONFIG_VERSION, CONFIG_VERSION_VALUE);
        setProperty(configuration, CONFIG_SOURCE, CONFIG_SOURCE_VALUE);
    }

    private void setProperty(Properties configuration, String key, String value)
    {
        if (value != null)
        {
            configuration.setProperty(key, value);
        }
    }


}
