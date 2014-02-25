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

    private Category parent;
    
    private boolean top;
    private int order;
}
