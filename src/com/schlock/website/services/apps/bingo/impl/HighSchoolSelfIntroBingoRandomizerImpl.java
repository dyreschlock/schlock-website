package com.schlock.website.services.apps.bingo.impl;

import com.schlock.website.services.apps.bingo.BingoRandomizer;

import java.util.ArrayList;
import java.util.List;

public class HighSchoolSelfIntroBingoRandomizerImpl implements BingoRandomizer
{
    private static final int FIVE_BY_FIVE = 5;

    public static final int BINGO_SIZE = FIVE_BY_FIVE;

    public List<String> createOrder()
    {
        List<String> empty = new ArrayList<String>();
        for(int i = 0; i < 25; i++)
        {
            empty.add("Not Empty");
        }
        return empty;
    }
}
