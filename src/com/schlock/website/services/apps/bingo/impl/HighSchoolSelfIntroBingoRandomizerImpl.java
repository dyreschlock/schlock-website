package com.schlock.website.services.apps.bingo.impl;

import com.schlock.website.entities.apps.bingo.BingoOption;
import com.schlock.website.services.apps.bingo.BingoRandomizer;
import com.schlock.website.services.database.apps.bingo.BingoOptionDAO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HighSchoolSelfIntroBingoRandomizerImpl implements BingoRandomizer
{
    private static final String BINGO_SHEET = "high-school-self-intro";

    private static final int CLUB_CATEGORY = 1;
    private static final int HOBBY_CATEGORY = 2;
    private static final int FOOD_CATEGORY = 3;
    private static final int TRAVEL_CATEGORY = 4;
    private static final int JOB_CATEGORY = 5;
    private static final List<Integer> CATEGORIES = Arrays.asList(CLUB_CATEGORY, HOBBY_CATEGORY, FOOD_CATEGORY, TRAVEL_CATEGORY, JOB_CATEGORY);

    private static final int FIVE_BY_FIVE = 5;

    public static final int BINGO_SIZE = FIVE_BY_FIVE;

    private final BingoOptionDAO bingoDAO;

    public HighSchoolSelfIntroBingoRandomizerImpl(BingoOptionDAO bingoDAO)
    {
        this.bingoDAO = bingoDAO;
    }


    public List<String> createOrder()
    {
        List<String> order = new ArrayList<String>();

        for (final int CATEGORY : CATEGORIES)
        {
            List<BingoOption> options = bingoDAO.getBySheetAndCategory(BINGO_SHEET, CATEGORY);

            List<String> orderInCategory = new ArrayList<String>();
            while (orderInCategory.size() < BINGO_SIZE)
            {
                double random = Math.random();
                int selection = (int) (random * options.size());

                BingoOption option = options.get(selection);
                String text = option.getEntry();

                if (!orderInCategory.contains(text))
                {
                    orderInCategory.add(text);
                }
            }

            order.addAll(orderInCategory);
        }
        return order;
    }
}
