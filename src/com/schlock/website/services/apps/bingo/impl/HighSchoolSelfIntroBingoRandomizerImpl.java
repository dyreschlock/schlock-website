package com.schlock.website.services.apps.bingo.impl;

import com.schlock.website.entities.apps.bingo.BingoOption;
import com.schlock.website.services.apps.bingo.BingoRandomizer;
import com.schlock.website.services.database.apps.bingo.BingoOptionDAO;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HighSchoolSelfIntroBingoRandomizerImpl implements BingoRandomizer
{
    private static final String BINGO_SHEET = "high-school-self-intro";
    private static final String DEFAULT_COURSE = "2ABb";

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
        return createOrder(null);
    }

    public List<String> createOrder(String parameter)
    {
        String course = parameter;
        if (StringUtils.isEmpty(course))
        {
            course = DEFAULT_COURSE;
        }

        List<String> order = createListInHorizontalOrder(course);
        order = reorderListVertically(order);

        return order;
    }

    private List<String> createListInHorizontalOrder(String course)
    {
        List<String> order = new ArrayList<String>();

        for (final int CATEGORY : CATEGORIES)
        {
            List<BingoOption> options = bingoDAO.getBySheetAndCategory(BINGO_SHEET, course, CATEGORY);

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

    private List<String> reorderListVertically(List<String> horizontal)
    {
        List<String> vertical = new ArrayList<String>(horizontal);

        for (final int CATEGORY : CATEGORIES)
        {
            int c = CATEGORY -1;
            for(int i = 0; i < BINGO_SIZE; i++)
            {
                int h_index = c*5 + i;
                int v_index = c + i*5;

                String h_data = horizontal.get(h_index);
                vertical.set(v_index, h_data);
            }
        }
        return vertical;
    }
}
