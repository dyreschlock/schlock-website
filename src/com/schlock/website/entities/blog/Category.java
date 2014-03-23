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
public class Category extends Persisted
{
    public static final String EVENT = "events";
    public static final String FESTIVAL = "festivals";

    private String name;
    private int ordering;

    private Category parent;


    public boolean isTopCategory()
    {
        return parent == null;
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

    public Category getParent()
    {
        return parent;
    }

    public void setParent(Category parent)
    {
        this.parent = parent;
    }
}
