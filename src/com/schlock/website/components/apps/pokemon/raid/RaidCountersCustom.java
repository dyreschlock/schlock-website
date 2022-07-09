package com.schlock.website.components.apps.pokemon.raid;

import com.schlock.website.entities.apps.pokemon.RaidBoss;
import com.schlock.website.entities.apps.pokemon.RaidCounterInstance;
import com.schlock.website.entities.apps.pokemon.RaidCounterType;
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
    private RaidBoss currentRaidBoss;

    @Property
    private RaidCounterInstance currentCounterPokemon;

    @Property
    private Integer currentIndex;

    public List<RaidBoss> getLegendaryPokemon()
    {
        return counterService.getRaidBosses();
    }

    public List<RaidCounterInstance> getCounterPokemon()
    {
        return counterService.getCounterPokemon(currentRaidBoss, RaidCounterType.CUSTOM);
    }

    public String getColumnIndex()
    {
        return "column" + (currentIndex +1);
    }

    public String getTotalDamageMessage()
    {
        List<RaidCounterInstance> party = getCounterPokemon();

        Integer totalDamage = calculationService.getTotalDamageForParty(party);

        return messages.format("total-damage", totalDamage.toString());
    }

    public String getTotalTimeMessage()
    {
        List<RaidCounterInstance> party = getCounterPokemon();

        Integer totalTime = calculationService.getTotalTimeForParty(party);

        return messages.format("total-time", totalTime.toString());
    }
}
