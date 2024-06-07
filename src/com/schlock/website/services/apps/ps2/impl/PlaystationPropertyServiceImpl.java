package com.schlock.website.services.apps.ps2.impl;

import com.schlock.website.entities.apps.ps2.*;
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
//    private static final String PUBLISHER = "Publisher";

    private static final String RATING = "Rating";
    private static final String RATING_TEXT = "RatingText";

    private static final String PARENTAL = "Parental";
    private static final String PARENTAL_TEXT = "ParentalText";

    private static final String PLAYERS = "Players";
    private static final String PLAYERS_TEXT = "PlayersText";

    private static final String VMODE = "Vmode";
    private static final String VMODE_TEXT = "VmodeText";

    private static final String ASPECT = "Aspect";
    private static final String ASPECT_TEXT = "AspectText";

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

//        String publisher = configuration.getProperty(PUBLISHER);
//        if (game.getPublisher() == null && publisher != null)
//        {
//            game.setPublisher(publisher);
//        }

        String notes = configuration.getProperty(NOTES);
        if (game.getNotes() == null && notes != null)
        {
            game.setNotes(notes);
        }

        String description = configuration.getProperty(DESCRIPTION);
        if (game.getDeveloper() == null && description != null)
        {
            game.setDescription(description);
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

        String aspectText = configuration.getProperty(ASPECT);
        if (game.getAspect() == null && aspectText != null)
        {
            PlaystationGameAspect aspect = PlaystationGameAspect.getFromText(aspectText);
            game.setAspect(aspect);
        }

        String playersText = configuration.getProperty(PLAYERS);
        if (game.getPlayers() == null && playersText != null)
        {
            PlaystationGamePlayers players = PlaystationGamePlayers.getFromText(playersText);
            game.setPlayers(players);
        }

        String vmodeText = configuration.getProperty(VMODE);
        if (game.getVmode() == null && vmodeText != null)
        {
            PlaystationGameVmode vmode = PlaystationGameVmode.getFromText(vmodeText);
            game.setVmode(vmode);
        }

        String scanText = configuration.getProperty(SCAN);
        if (game.getScan() == null && scanText != null)
        {
            PlaystationGameScan scan = PlaystationGameScan.getFromText(scanText);
            game.setScan(scan);
        }

        String parentalText = configuration.getProperty(PARENTAL);
        if (game.getParental() == null && parentalText != null)
        {
            PlaystationGameParental parental = PlaystationGameParental.getFromText(parentalText);
            game.setParental(parental);
        }
    }

    public void writePropertiesFromGame(PlaystationGame game, Properties configuration)
    {
        setProperty(configuration, TITLE, game.getTitle());
        setProperty(configuration, GENRE, game.getGenre());

        String developer = game.getDeveloper();
        String publisher = game.getPublisher();
        if (developer != null && publisher != null && !developer.equalsIgnoreCase(game.getPublisher()))
        {
            developer += " / " + publisher;
        }
        setProperty(configuration, DEVELOPER, developer);

        setProperty(configuration, NOTES, game.getNotes());
        setProperty(configuration, DESCRIPTION, game.getDescription());

        if (game.getAspect() != null)
        {
            setProperty(configuration, ASPECT, game.getAspect().aspect());
            setProperty(configuration, ASPECT_TEXT, game.getAspect().aspectText());
        }

        if (game.getPlayers() != null)
        {
            setProperty(configuration, PLAYERS, game.getPlayers().players());
            setProperty(configuration, PLAYERS_TEXT, game.getPlayers().playersText());
        }

        if (game.getVmode() != null)
        {
            setProperty(configuration, VMODE, game.getVmode().vmode());
            setProperty(configuration, VMODE_TEXT, game.getVmode().vmodeText());
        }

        if (game.getScan() != null)
        {
            setProperty(configuration, SCAN, game.getScan().scan());
            setProperty(configuration, SCAN_TEXT, game.getScan().scanText());
        }

        if (game.getParental() != null)
        {
            setProperty(configuration, PARENTAL, game.getParental().parental());
            setProperty(configuration, PARENTAL_TEXT, game.getParental().parentalText());
        }

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
