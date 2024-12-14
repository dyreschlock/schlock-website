package com.schlock.website.entities.blog;

import com.schlock.website.entities.Persisted;

/**
 *
 * Japan
 * - Japanese
 * - Travel
 * - JET / ALT
 * - Study Abroad
 *
 * Music
 * - DJ Mixes
 * - Events
 * - Festivals
 *
 * Games
 * - PAX
 *
 * Technology
 * - Programming
 * - Math
 *
 * Media
 * - TV
 * - Film
 * - Books
 * - Anime
 *
 * Miscellanous
 * - Running
 * - Toys
 * - Recipes
 * - Ramen
 *
 *
 */
public abstract class AbstractCategory extends Persisted
{
    private String uuid;

    private String name;
    private int ordering;

    private AbstractCategory parent;

    private String description;

    public boolean isTopCategory()
    {
        return parent == null;
    }

    public boolean isPost()
    {
        return this.getClass().isAssignableFrom(PostCategory.class);
    }

    public boolean isProject()
    {
        return this.getClass().isAssignableFrom(ProjectCategory.class);
    }


    public String getUuid()
    {
        return uuid;
    }

    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getOrdering()
    {
        return ordering;
    }

    public void setOrdering(int ordering)
    {
        this.ordering = ordering;
    }

    public AbstractCategory getParent()
    {
        return parent;
    }

    public void setParent(AbstractCategory parent)
    {
        this.parent = parent;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
}
