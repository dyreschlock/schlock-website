package com.schlock.website.components.apps.pokemon.raid;

import com.schlock.website.entities.apps.pokemon.RaidBossPokemon;
import com.schlock.website.entities.apps.pokemon.RaidCounterInstance;
import com.schlock.website.entities.apps.pokemon.CounterType;
import com.schlock.website.services.apps.pokemon.PokemonRaidCounterCalculationService;
import com.schlock.website.services.apps.pokemon.PokemonRaidCounterService;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

public class RaidCountersCustom
{
    @Inject
    private PokemonRaidCounterService counterService;

    @Inject
    private PokemonRaidCounterCalculationService calculationService;

    @Inject
    private Messages messages;

    @Property
    private RaidBossPokemon currentRaidBoss;

    @Property
    private RaidCounterInstance currentCounterPokemon;

    @Property
    private Integer currentIndex;

    public List<RaidBossPokemon> getLegendaryPokemon()
    {
        return counterService.getRaidBosses();
    }

    public List<RaidCounterInstance> getPrimeCounterPokemon()
    {
        return counterService.getCounterPokemon(currentRaidBoss, CounterType.CUSTOM1);
    }

    public List<RaidCounterInstance> getSecondCounterPokemon()
    {
        return counterService.getCounterPokemon(currentRaidBoss, CounterType.CUSTOM2);
    }

    public String getColumnIndex()
    {
        return "column" + (currentIndex +1);
    }

    public String getPrimeTotalDamageMessage()
    {
        List<RaidCounterInstance> party = getPrimeCounterPokemon();
        return getTotalDamageMessage(party);
    }

    public String getSecondTotalDamageMessage()
    {
        List<RaidCounterInstance> party = getSecondCounterPokemon();
        return getTotalDamageMessage(party);
    }

    private String getTotalDamageMessage(List<RaidCounterInstance> party)
    {
        Integer totalDamage = calculationService.getTotalDamageForParty(party);

        return messages.format("total-damage", totalDamage.toString());
    }

    public String getPrimeTotalTimeMessage()
    {
        List<RaidCounterInstance> party = getPrimeCounterPokemon();
        return getTotalTimeMessage(party);
    }

    public String getSecondTotalTimeMessage()
    {
        List<RaidCounterInstance> party = getSecondCounterPokemon();
        return getTotalTimeMessage(party);
    }

    private String getTotalTimeMessage(List<RaidCounterInstance> party)
    {
        Integer totalTime = calculationService.getTotalTimeForParty(party);

        return messages.format("total-time", totalTime.toString());
    }
}
