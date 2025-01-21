package com.schlock.website.components.apps.ps2;

import com.schlock.website.components.apps.games.DataPanel;
import com.schlock.website.entities.Icon;
import com.schlock.website.entities.apps.ps2.PlaystationPlatform;
import com.schlock.website.entities.apps.ps2.RetroGame;
import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.pages.apps.ps2.Index;
import com.schlock.website.services.apps.ps2.RetroGameImageService;
import com.schlock.website.services.apps.ps2.PlaystationService;
import com.schlock.website.services.blog.IconManagement;
import com.schlock.website.services.database.apps.ps2.RetroGameDAO;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

public class GamesPanel
{
    private static final String BLANK = "blank";

    @Inject
    private IconManagement iconManagement;

    @Inject
    private PostDAO postDAO;

    @Inject
    private RetroGameImageService imageService;

    @Inject
    private PlaystationService playstationService;

    @Inject
    private RetroGameDAO gameDAO;

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
    private RetroGame currentGame;

    @Property
    private Integer currentIndex;

    public List<RetroGame> getGames()
    {
        if (StringUtils.equals(Index.SAVES_GENRE_KEY, genre))
        {
            return gameDAO.getCombinedAvailableGamesWithSaves(platform);
        }
        return gameDAO.getCombinedAvailableGamesByPlatformGenre(platform, genre);
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

    public String getCurrentGameTitle()
    {
        final String DISC_1 = " (Disc 1)";

        String title = currentGame.getTitle();
        title = title.replace(DISC_1, "");
        if (StringUtils.isNotBlank(currentGame.getPostUUID()))
        {
            AbstractPost post = postDAO.getByUuid(currentGame.getPostUUID());
            if (post != null && post.isPublished())
            {
                String span = "<a href=\"%s\">%s</a>";

                title = String.format(span, "/" + currentGame.getPostUUID(), title);
            }
        }
        return title;
    }


    public String getCurrentGameSaveFileLink()
    {
        return currentGame.getSaveFileLink();
    }

    public String getSaveIconSrc()
    {
        return iconManagement.getIconLink(Icon.PS_MEM);
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
        List<RetroGame> games = getGames();

        String outputHTML = imageService.generateImageHTMLFromGames(games);
        return outputHTML;
    }
}
