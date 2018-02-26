package com.schlock.website.services.apps.bingo.impl;

import com.schlock.website.entities.apps.bingo.BingoOption;
import com.schlock.website.services.apps.bingo.BingoRandomizer;
import com.schlock.website.services.database.apps.bingo.BingoOptionDAO;

import java.util.ArrayList;
import java.util.List;

public class HighSchoolSelfIntroBingoRandomizerImpl implements BingoRandomizer
{
    private static final String BINGO_SHEET = "high-school-self-intro";

    private static final int FIVE_BY_FIVE = 5;

    public static final int BINGO_SIZE = FIVE_BY_FIVE;

    private final BingoOptionDAO bingoDAO;

    private int limit;

    public HighSchoolSelfIntroBingoRandomizerImpl(BingoOptionDAO bingoDAO)
    {
        this.bingoDAO = bingoDAO;

        limit = BINGO_SIZE * BINGO_SIZE;
    }


    public List<String> createOrder()
    {
        List<BingoOption> options = bingoDAO.getAllBySheet(BINGO_SHEET);

        List<String> order = new ArrayList<String>();
        while (order.size() < limit)
        {
            double random = Math.random();
            int selection = (int) (random * options.size());

            BingoOption option = options.get(selection);
            String text = option.getEntry();

            if (!order.contains(text))
            {
                order.add(text);
            }
        }
        return order;
    }
}
