package com.schlock.website.components.apps.pokemon.raid;

import com.schlock.website.entities.apps.pokemon.PokemonCategory;
import com.schlock.website.services.database.apps.pokemon.PokemonCategoryDAO;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

public class RaidBossFilters
{
    @Inject
    private PokemonCategoryDAO categoryDAO;

    @Inject
    private Messages messages;

    @Property
    private PokemonCategory currentCategory;



    public List<PokemonCategory> getCategories()
    {
        return categoryDAO.getAll();
    }

    public String getCurrentCategoryText()
    {
        return messages.get(currentCategory.getNameId());
    }
}
