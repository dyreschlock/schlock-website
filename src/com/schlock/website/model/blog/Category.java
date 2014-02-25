package com.schlock.website.model.blog;

import com.schlock.website.model.Persisted;

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
    private String name;
    private int order;

    private Category parent;



    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getOrder()
    {
        return order;
    }

    public void setOrder(int order)
    {
        this.order = order;
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
