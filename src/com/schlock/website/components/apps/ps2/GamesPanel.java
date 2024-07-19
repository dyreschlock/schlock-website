package com.schlock.website.components.apps.ps2;

import com.schlock.website.components.apps.games.DataPanel;
import com.schlock.website.entities.Icon;
import com.schlock.website.entities.apps.ps2.PlaystationGame;
import com.schlock.website.entities.apps.ps2.PlaystationPlatform;
import com.schlock.website.services.apps.ps2.PlaystationImageService;
import com.schlock.website.services.apps.ps2.PlaystationService;
import com.schlock.website.services.blog.IconManagement;
import com.schlock.website.services.database.apps.ps2.PlaystationGameDAO;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.text.SimpleDateFormat;
import java.util.List;

public class GamesPanel
{
    private static final String BLANK = "blank";

    @Inject
    private IconManagement iconManagement;

    @Inject
    private PlaystationImageService imageService;

    @Inject
    private PlaystationService playstationService;

    @Inject
    private PlaystationGameDAO gameDAO;

    @Inject
    private Messages messages;

    @Parameter
    private PlaystationPlatform platform;

    @Parameter
    private String genre;

    @Parameter
    @Property
    private Boolean imageView;


    @Property
    private PlaystationGame currentGame;

    @Property
    private Integer currentIndex;

    public List<PlaystationGame> getGames()
    {
        return gameDAO.getAvailableGamesByPlatformGenre(platform, genre);
    }


    public boolean isPlatformSelected()
    {
        return platform != null;
    }

    public String getPlatformName()
    {
        if (platform == null)
        {
            return BLANK;
        }
        return platform.name().toLowerCase();
    }

    public String getCurrentGamePlatformName()
    {
        return currentGame.getPlatform().name().toLowerCase();
    }

    public String getCurrentGamePlatformText()
    {
        return currentGame.getPlatform().name();
    }

    public String getCurrentGameGenre()
    {
        if (currentGame.getGenre() != null)
        {
            String genre = currentGame.getGenre().toLowerCase().replaceAll(" ", "");
            return messages.get(genre);
        }
        return "";
    }


    public String getCurrentGameReleaseDate()
    {
        if (currentGame.getReleaseDate() != null)
        {
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            return format.format(currentGame.getReleaseDate());
        }
        return "";
    }

    public String getEvenOdd()
    {
        if (currentIndex % 2 == 0)
        {
            return DataPanel.EVEN;
        }
        return DataPanel.ODD;
    }

    public String getImageHTML()
    {
        List<PlaystationGame> games = getGames();

        String outputHTML = imageService.generateImageHTMLFromGames(games);
        return outputHTML;
    }

    public String getCurrentGameSaveFileLink()
    {
        return playstationService.getSaveFileLink(currentGame);
    }

    public String getIconSrc()
    {
        return iconManagement.getIconLink(Icon.PS_MEM);
    }
}
