package com.schlock.website.services.apps.pokemon.impl;

import com.schlock.website.entities.apps.pokemon.UnownEvent;
import com.schlock.website.entities.apps.pokemon.UnownPokemon;
import com.schlock.website.entities.apps.pokemon.WorldRegion;
import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.apps.pokemon.PokemonUnownService;
import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class PokemonUnownServiceImpl implements PokemonUnownService
{
    /** References
     * http://ark42.com/pogo/unown.php
     * https://www.serebii.net/pokemongo/unownevents.shtml
     */

    private final static String CURRENT_UNOWN_POKEDEX = "AEFGIKMNOPRSTUVWY!";
    private final static String HAVE_TO_TRADE =         "AEFGIKMNPRSTUVWY!";
    private final static String HAVE_SHINY =            "U";

    private final static List[] UNOWN_EVENTS = {
            Arrays.asList("Chicago Go Fest", "CHIAGO", "7/22/2017", "7/24/2017", "Chicago, IL, USA", WorldRegion.NORTH_AMERICA),
            Arrays.asList("Europe Delayed Safari Zone", "EUROP", "8/4/2017", "8/21/2017", "Europe (various)", WorldRegion.EUROPE),
            Arrays.asList("Yokohama Pokemon Park", "YOKHAM", "8/9/2017", "8/15/2017", "Yokohama, Japan", WorldRegion.JAPAN),
            Arrays.asList("World Championships Anaheim", "WOLRDS", "8/18/2017", "8/20/2017", "Anaheim, CA, USA", WorldRegion.NORTH_AMERICA),
            Arrays.asList("Europe Safari Zones", "SAFRI", "9/16/2017", "10/14/2017", "Europe (various)", WorldRegion.EUROPE),
            Arrays.asList("TwitchCon", "TWICH", "10/20/2017", "10/20/2017", "Long Beach, CA, USA", WorldRegion.NORTH_AMERICA),
            Arrays.asList("Pokemon Go Week Korea", "SEOUL", "11/10/2017", "11/12/2017", "Souel, South Korea", WorldRegion.ASIA),
            Arrays.asList("Pokemon Go Safari Zone", "SAKYU", "11/24/2017", "11/26/2017", "Tottori Sand Dunes, Japan", WorldRegion.JAPAN),
            Arrays.asList("Syndey RTX", "RTX", "2/3/2018", "2/3/2018", "Sydney, Australia", WorldRegion.AUSTRAILIA),
            Arrays.asList("Pokemon Go Safari Zone", "CHIAYGXF", "2/26/2018", "3/3/2018", "Chiayi, Taiwan", WorldRegion.ASIA),
            Arrays.asList("South x South West", "SXW", "3/8/2018", "3/15/2018", "Austin, TX, USA", WorldRegion.NORTH_AMERICA),
            Arrays.asList("Games Developer Conference", "GDC", "3/21/2018", "4/1/2018", "San Francisco, CA, USA", WorldRegion.NORTH_AMERICA),
            Arrays.asList("Armageddon Expo NZ", "ARMGEDON", "3/30/2018", "4/1/2018", "Wellington, New Zealand", WorldRegion.AUSTRAILIA),
            Arrays.asList("Dutch Comic Con", "COMIN", "3/31/2018", "4/2/2018", "Utretch, Netherlands", WorldRegion.EUROPE),
            Arrays.asList("PAX East", "PAX", "4/6/2018", "4/8/2018", "Boston, MA, USA", WorldRegion.NORTH_AMERICA),
            Arrays.asList("Rio Creative Conference", "RIOTC", "4/7/2018", "4/8/2018", "Rio de Janiero, Brazil", WorldRegion.SOUTH_AMERICA),
            Arrays.asList("London Games Festival", "LOND", "4/14/2018", "4/14/2018", "London, England", WorldRegion.EUROPE),
            Arrays.asList("E3", "LETSGO!", "6/12/2018", "6/14/2018", "San Francisco, CA, USA", WorldRegion.NORTH_AMERICA),
            Arrays.asList("FIFA World Cup Argentina", "VAMOSRG", "6/16/2018", "6/17/2018", "Argentina", WorldRegion.SOUTH_AMERICA),
            Arrays.asList("FIFA World Cup Brazil", "VAMOSBR", "6/16/2018", "6/17/2018", "Brazil", WorldRegion.SOUTH_AMERICA),
            Arrays.asList("FIFA World Cup Peru", "VAMOSPER", "6/16/2018", "6/17/2018", "Peru", WorldRegion.SOUTH_AMERICA),
            Arrays.asList("FIFA World Cup Mexico", "VAMOSEX", "6/17/2018", "6/18/2018", "Mexico", WorldRegion.NORTH_AMERICA),
            Arrays.asList("FIFA World Cup Costa Rica", "VAMOSCRI", "6/17/2018", "6/18/2018", "Costa Rica", WorldRegion.SOUTH_AMERICA),
            Arrays.asList("FIFA World Cup Uruguay", "VAMOSURY", "6/17/2018", "6/18/2018", "Uruguay", WorldRegion.SOUTH_AMERICA),
            Arrays.asList("FIFA World Cup Panama", "VAMOSPN", "6/17/2018", "6/18/2018", "Panama", WorldRegion.SOUTH_AMERICA),
            Arrays.asList("FIFA World Cup Columbia", "VAMOSCL", "6/17/2018", "6/18/2018", "Columbia", WorldRegion.SOUTH_AMERICA),
            Arrays.asList("Dortmund Safari Zone", "DORTMUN", "6/30/2018", "7/1/2018", "Dortmund", WorldRegion.EUROPE),
            Arrays.asList("Japan Expo Paris", "JAPNEXO", "7/5/2018", "7/8/2018", "Paris, France", WorldRegion.EUROPE),
            Arrays.asList("Chicago Go Fest 2", "CELBI?", "7/14/2018", "7/15/2018", "Chicago, IL, USA", WorldRegion.NORTH_AMERICA),
            Arrays.asList("San Diego Comic Con", "SDCOMIN", "7/19/2018", "7/23/2018", "San Diego, CA, USA", WorldRegion.NORTH_AMERICA),
            Arrays.asList("Japan Special Weekend", "TJMDAE", "7/26/2018", "7/29/2018", "Japan", WorldRegion.JAPAN),
            Arrays.asList("Korea Special Weekend", "SLT", "7/29/2018", "7/29/2018", "South Korea", WorldRegion.ASIA),
            Arrays.asList("GamesCon Cologne", "GAMESCO", "8/21/2018", "8/24/2018", "Cologne, Germany", WorldRegion.EUROPE),
            Arrays.asList("World Championships Nashville", "NASHVILE", "8/24/2018", "8/26/2018", "Nashville, TN, USA", WorldRegion.NORTH_AMERICA),
            Arrays.asList("Japan Yokosuka Safari Zone", "YOKSUAG!", "8/29/2018", "9/2/2018", "Yokosuka, Japan", WorldRegion.JAPAN),
            Arrays.asList("PAX West", "PAX", "8/29/2018", "9/2/2018", "Seattle, WA, USA", WorldRegion.NORTH_AMERICA),
            Arrays.asList("Dragon Con", "DRAGONC", "8/30/2018", "9/3/2018", "", WorldRegion.NORTH_AMERICA),
            Arrays.asList("Comic Con Africa", "AFRIC", "9/14/2018", "9/16/2018", "Johannesburg, SA", WorldRegion.AFRICA),
            Arrays.asList("Pokemon Go Week Korea 2", "CHUSEOK", "9/21/2018", "9/23/2018", "Seoul, South Korea", WorldRegion.ASIA),
            Arrays.asList("NY Comic Con", "NYCOMI", "10/4/2018", "10/7/2018", "New York, NY, USA", WorldRegion.NORTH_AMERICA),
            Arrays.asList("Milan Games Week", "MGW", "10/5/2018", "10/7/2018", "Milan, Italy", WorldRegion.EUROPE),
            Arrays.asList("MAG 2018", "MAG!", "10/5/2018", "10/7/2018", "Erfurt, Germany", WorldRegion.EUROPE),
            Arrays.asList("PAX Australia", "PAX", "10/26/2018", "10/28/2018", "Melbourne, Australia", WorldRegion.AUSTRAILIA),
            Arrays.asList("Paris Games Week", "PGW", "10/26/2018", "10/30/2018", "Paris, France", WorldRegion.EUROPE),
            Arrays.asList("MCM London Comic Con", "MCLOND", "10/26/2018", "10/30/2018", "London, Englash", WorldRegion.EUROPE),
            Arrays.asList("Lucca Comics & Games Con", "LCG", "10/31/2018", "11/4/2018", "Lucca, Italy", WorldRegion.EUROPE),
            Arrays.asList("Tainan Safari Zone", "TAINZONE", "11/1/2018", "11/5/2018", "Tainan, Taiwan", WorldRegion.ASIA),
            Arrays.asList("Porto Alegre Safari Zone", "PORTALEG", "1/25/2019", "1/27/2019", "Porto Alegre, Brazil", WorldRegion.EUROPE),
//            Arrays.asList("Japan Special Weekend", "SB", "", "", "Japan", WorldRegion.JAPAN),
            Arrays.asList("Games Developer Conference", "GDC", "3/18/2019", "3/22/2019", "San Francisco, CA, USA", WorldRegion.NORTH_AMERICA),
            Arrays.asList("Japan Special Weekend", "INTYVS", "4/6/2019", "4/7/2019", "Japan", WorldRegion.JAPAN),
            Arrays.asList("Singapore Safari Zone", "SENTOA!", "4/18/2019", "4/22/2019", "Sentosa, Singapore", WorldRegion.ASIA),
            Arrays.asList("Pokemon Go Week Korea 2", "SEOUL", "5/4/2019", "5/6/2019", "Seoul, South Korea", WorldRegion.ASIA),
            Arrays.asList("Chicago Go Fest 3", "WAKEUP!", "6/13/2019", "6/16/2019", "Chicago, IL, USA", WorldRegion.NORTH_AMERICA),
            Arrays.asList("Dortmund Go Fest", "WAKEUP!", "7/4/2019", "7/7/2019", "Dortmund, Germany", WorldRegion.EUROPE),
            Arrays.asList("Yokohama Go Fest", "WAKEUP!", "8/5/2019", "8/12/2019", "Yokahama, Japan", WorldRegion.JAPAN),
            Arrays.asList("Go Fest Ultra Bonus", "ULTRA", "9/2/2019", "9/9/2019", "Global (in Eggs)", WorldRegion.GLOBAL),
            Arrays.asList("Montreal Safari Zone", "QUEBC", "9/20/2019", "9/22/2019", "Montreal, QB, Canada", WorldRegion.NORTH_AMERICA),
            Arrays.asList("New Taipei City Safari Zone", "NTPCSAFRI", "10/3/2019", "10/6/2019", "New Taipei City, Taiwan", WorldRegion.ASIA),
            Arrays.asList("St. Louis Safari Zone", "STLOUI", "3/27/2020", "3/29/2020", "St. Louis, MO, USA", WorldRegion.NORTH_AMERICA),
            Arrays.asList("Liverpool Safari Zone", "LIVERPO", "4/17/2020", "4/19/2020", "Liverpool, Englash", WorldRegion.EUROPE),
            Arrays.asList("Philadelphia Safari Zone", "PHILADE", "5/8/2020", "5/10/2020", "Philadelphia, PA, USA", WorldRegion.NORTH_AMERICA),
            Arrays.asList("Pokemon Go Fest 2020", "GO", "7/25/2020", "7/26/2020", "Global", WorldRegion.GLOBAL),
            Arrays.asList("Enigma Week", "ULTRA", "8/7/2020", "8/14/2020", "Global (in Raids)", WorldRegion.GLOBAL),
            Arrays.asList("US Special Weekend", "VZGH", "11/7/2020", "11/8/2020", "United States", WorldRegion.NORTH_AMERICA),
            Arrays.asList("Japan Special Weekend", "SBMK", "11/7/2020", "11/8/2020", "Japan", WorldRegion.JAPAN),
            Arrays.asList("Mexico Special Weekend", "SE", "11/7/2020", "11/8/2020", "Mexico", WorldRegion.NORTH_AMERICA),
            Arrays.asList("City Explorer", "C", "11/22/2020", "11/22/2020", "Auckland, New Zealand", WorldRegion.AUSTRAILIA),
            Arrays.asList("City Explorer", "C", "11/22/2020", "11/22/2020", "Busan / Tainan", WorldRegion.ASIA),
            Arrays.asList("Pokemon Go Special Weekend", "V", "5/29/2021", "5/29/2021", "US", WorldRegion.NORTH_AMERICA),
            Arrays.asList("Pokemon Go Special Weekend", "S", "5/29/2021", "5/29/2021", "Mexico", WorldRegion.NORTH_AMERICA),
            Arrays.asList("Pokemon Go Special Weekend", "Y", "5/29/2021", "5/29/2021", "Japan", WorldRegion.JAPAN),
            Arrays.asList("Pokmeon Go Fest", "FG", "7/17/2021", "7/18/2021", "Global", WorldRegion.GLOBAL),
            Arrays.asList("Ultra Unlock", "U", "7/23/2021", "8/31/2021", "Global", WorldRegion.GLOBAL),
            Arrays.asList("Safari Zone Liverpool", "LIVERPO", "10/15/2021", "10/17/2021", "Global", WorldRegion.GLOBAL),
            Arrays.asList("Safari Zone Philadelphia", "PHILADE", "10/29/2021", "10/31/2021", "Global", WorldRegion.GLOBAL),
            Arrays.asList("Safari Zone St Louis", "STLOUI", "11/12/2021", "11/14/2021", "Global", WorldRegion.GLOBAL),
            Arrays.asList("Pokemon Go Special Weekend", "IRFMSPETL", "12/10/2021", "12/12/2021", "Japan", WorldRegion.JAPAN),
            Arrays.asList("Johto Tour", "GOTURJ", "2/26/2022", "2/27/2022", "Global", WorldRegion.GLOBAL)
    };

    private List<UnownPokemon> listOfUnown = new ArrayList<UnownPokemon>();
    private List<UnownEvent> listOfEvents = new ArrayList<UnownEvent>();

    private final DeploymentContext deploymentContext;

    public PokemonUnownServiceImpl(DeploymentContext context)
    {
        this.deploymentContext = context;

        createUnowns();
    }

    private final int EVENT_NAME = 0;
    private final int EVENT_UNOWN = 1;
    private final int EVENT_START_DATE = 2;
    private final int EVENT_END_DATE = 3;
    private final int EVENT_LOCATION = 4;
    private final int EVENT_REGION = 5;

    public void createUnowns()
    {
        listOfUnown.clear();
        listOfEvents.clear();

        for (List eventStrings : UNOWN_EVENTS)
        {
            String eventName = (String) eventStrings.get(EVENT_NAME);
            String eventUnown = (String) eventStrings.get(EVENT_UNOWN);

            String eventStartStr = (String) eventStrings.get(EVENT_START_DATE);
            String eventEndStr = (String) eventStrings.get(EVENT_END_DATE);

            String eventLocation = (String) eventStrings.get(EVENT_LOCATION);
            WorldRegion eventRegion = (WorldRegion) eventStrings.get(EVENT_REGION);


            UnownEvent event = new UnownEvent();
            event.setEventName(eventName);
            event.setUnownAvailable(eventUnown);

            Date startDate = parseDateFromString(eventStartStr);
            event.setStartDate(startDate);

            Date endDate = parseDateFromString(eventEndStr);
            event.setEndDate(endDate);

            event.setLocation(eventLocation);
            event.setRegion(eventRegion);

            listOfEvents.add(event);

            addEventToUnown(event);
        }
    }

    private Date parseDateFromString(String dateString)
    {
        try
        {
            Date date = new SimpleDateFormat("MM/dd/yyyy").parse(dateString);
            return date;
        }
        catch(ParseException exception)
        {
        }
        return null;
    }

    private void addEventToUnown(UnownEvent event)
    {
        String unownString = event.getUnownAvailable();

        for (char letterChar : unownString.toCharArray())
        {
            String letter = Character.toString(letterChar);

            UnownPokemon pokemon = getUnownByLetter(letter);
            pokemon.getEvents().add(event);
        }
    }

    private UnownPokemon getUnownByLetter(String letter)
    {
        for (UnownPokemon pokemon : listOfUnown)
        {
            if (pokemon.getLetter().equals(letter))
            {
                return pokemon;
            }
        }

        UnownPokemon newUnown = new UnownPokemon(letter);
        listOfUnown.add(newUnown);

        return newUnown;
    }

    public List<UnownEvent> getListOfEvents()
    {
        return listOfEvents;
    }

    public List<UnownPokemon> getListOfUnownByLetter()
    {
        Collections.sort(listOfUnown);
        return listOfUnown;
    }

    public List<UnownPokemon> getListOfUnownByRarity()
    {
        Collections.sort(listOfUnown, new Comparator<UnownPokemon>()
        {
            @Override
            public int compare(UnownPokemon o1, UnownPokemon o2)
            {
                return o1.getEventCount() - o2.getEventCount();
            }
        });

        return listOfUnown;
    }

    public List<UnownPokemon> getListOfUnownByRarityAndNotOwned()
    {
        List<UnownPokemon> unown = getListOfUnownByRarity();

        List<UnownPokemon> unowned = new ArrayList<UnownPokemon>();

        for (UnownPokemon pokemon : unown)
        {
            String letter = pokemon.getLetter();
            if (!CURRENT_UNOWN_POKEDEX.contains(letter))
            {
                unowned.add(pokemon);
            }
        }
        return unowned;
    }

    private static final String COMMA = ", ";

    @Override
    public String getEventNamesForUnown(UnownPokemon pokemon)
    {
        List<String> names = new ArrayList<String>();
        for (UnownEvent event : pokemon.getEvents())
        {
            String html = getEventNameHTML(event);
            names.add(html);
        }

        return StringUtils.join(names, COMMA);
    }

    @Override
    public String getEventNamesForUnownByYear(UnownPokemon pokemon, String year)
    {
        List<String> names = new ArrayList<String>();
        for (UnownEvent event : pokemon.getEvents())
        {
            String eventYear = new SimpleDateFormat("yyyy").format(event.getStartDate());
            if (year.equals(eventYear))
            {
                String html = getEventNameHTML(event);
                names.add(html);
            }
        }

        return StringUtils.join(names, COMMA);
    }

    private String getEventNameHTML(UnownEvent event)
    {
        String spanStart = "<span class="+ event.getRegion().name() +" >";
        String spanEnd = "</span>";

        return spanStart + event.getEventName() + spanEnd;
    }
}
