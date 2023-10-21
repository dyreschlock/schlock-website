package com.schlock.website.pages.apps.pokemon;

import com.schlock.website.entities.apps.pokemon.CounterType;
import com.schlock.website.entities.apps.pokemon.RaidBossPokemon;
import com.schlock.website.services.apps.pokemon.PokemonRaidCounterService;
import com.schlock.website.services.blog.CssCache;
import com.sun.tools.javac.util.StringUtils;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

public class PokemonRaidCounter
{
    private static final String POST_UUID = "pokemon-go-raid-counters";

    @Inject
    private PokemonRaidCounterService counterService;

    @Inject
    private CssCache cssCache;

    @Inject
    private Messages messages;

    private CounterType counterType;

    @Property
    private RaidBossPokemon selectedBoss;


    Object onActivate()
    {
        this.counterType = CounterType.defaultType();
        return true;
    }

    Object onActivate(String parameter)
    {
        if(CounterType.CUSTOM.toString().equalsIgnoreCase(parameter))
        {
            this.counterType = CounterType.CUSTOM;
        }
        else if(CounterType.GENERAL.toString().equalsIgnoreCase(parameter))
        {
            this.counterType = CounterType.GENERAL;
        }
        else if (CounterType.TOP.toString().equalsIgnoreCase(parameter))
        {
            this.counterType = CounterType.TOP;
        }
        else
        {
            String nameId = StringUtils.toUpperCase(parameter);

            this.selectedBoss = counterService.getRaidBossByNameId(nameId);
            this.counterType = CounterType.GENERAL;
        }
        return true;
    }

    public String getPageTitle()
    {
        String param = messages.get(counterType.name().toLowerCase());
        if (selectedBoss != null)
        {
            param = selectedBoss.getName();
        }
        return messages.format("page-title", param);
    }

    public String getPostUuid()
    {
        return POST_UUID;
    }

    public String getCss()
    {
        return cssCache.getCssForPokemon();
    }

    public CounterType getCounterType()
    {
        return counterType;
    }

    public boolean isRaidBossSelected()
    {
        return selectedBoss != null;
    }


    public static String getPageLink(RaidBossPokemon boss)
    {
        String link = "/apps/pokemon/raidcounter";
        if (boss != null)
        {
            link += "/" + boss.getNameId().toLowerCase();
        }
        return link;
    }
}
