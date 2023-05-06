package com.schlock.website.components.apps.games;

import com.schlock.website.entities.apps.games.Condition;
import com.schlock.website.entities.apps.games.Region;
import com.schlock.website.entities.apps.games.VideoGame;
import com.schlock.website.entities.apps.games.VideoGameConsole;
import com.schlock.website.services.database.apps.games.VideoGameDAO;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.text.SimpleDateFormat;
import java.util.*;

public class PlatformGamesPanel
{
    @Inject
    private VideoGameDAO gameDAO;

    @Inject
    private Messages messages;

    @Parameter
    @Property
    private VideoGameConsole console;

    @Parameter
    private Condition condition;

    @Parameter
    private Region region;

    @Property
    private VideoGame currentGame;

    @Property
    private Integer currentIndex;

    public List<VideoGame> getGames()
    {
        List<VideoGame> games;
        if (condition != null)
        {
            games = gameDAO.getByConsoleCondition(console, condition);
        }
        else if (region != null)
        {
            games = gameDAO.getByConsoleRegion(console, region);
        }
        else
        {
            games = new ArrayList<VideoGame>();
            games.addAll(console.getGames());
        }

        Collections.sort(games, new Comparator<VideoGame>()
        {
            @Override
            public int compare(VideoGame o1, VideoGame o2)
            {
                String t1 = o1.getTitle();
                String t2 = o2.getTitle();

                return t1.compareTo(t2);
            }
        });

        return games;
    }

    public String getCurrentGameReleaseDate()
    {
        Date date = currentGame.getReleaseDate();
        if (date != null)
        {
            String pattern = "yyyy-MM-dd";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

            return simpleDateFormat.format(date);
        }
        return "";
    }

    public String getCurrentGameRegion()
    {
        String key = currentGame.getRegion().name().toLowerCase();
        return messages.get(key);
    }

    public String getCurrentGameCondition()
    {
        String key = currentGame.getCondition().name().toLowerCase();
        return messages.get(key);
    }

    public String getEvenOdd()
    {
        if (currentIndex % 2 == 0)
        {
            return PlatformPanel.EVEN;
        }
        return PlatformPanel.ODD;
    }
}