package com.schlock.website.services.apps.ranking.impl;

import com.schlock.website.services.apps.ranking.RankingService;

import java.util.*;

public class RankingServiceImpl implements RankingService
{
    private List<String> games = Arrays.asList("Snatcher",
                                                "Dance Dance Revolution Series",
                                                "Tetris",
                                                "Legend of Zelda - A Link to the Past",
                                                "Legend of Zelda - Ocarina of Time",
                                                "Legend of Zelda - Twilight Princess",
                                                "Legend of Zelda - Breath of the Wild",
                                                "Pokemon Series",
                                                "Wipeout XL",
                                                "Chrono Cross",
                                                "Final Fantasy VII",
                                                "Final Fantasy XV",
                                                "Final Fantasy VIII",
                                                "Return to Castle Wolfenstein",
                                                "Chrono Trigger",
                                                "Wipeout 3",
                                                "Toe Jam & Earl",
                                                "Sonic the Hedgehog 2",
                                                "Golden Eye 64",
                                                "Perfect Dark",
                                                "Megaman 3",
                                                "Megaman 2",
                                                "Rez",
                                                "Ikaruga",
                                                "Fzero GX",
                                                "Ultima Online - The Second Age",
                                                "Hitman (2016)",
                                                "Horizon Zero Dawn",
                                                "Firewatch",
                                                "The Witness",
                                                "Metal Gear Solid 5",
                                                "Metal Gear Solid 3",
                                                "Super Mario Maker",
                                                "Xenoblade",
                                                "Actraiser",
                                                "Dance Dance Revolution Series",
                                                "Mass Effect Series");

    private Map<String, Integer> values = new HashMap<String, Integer>();

    private int current_gap = 1;
    private int current_position = 0;

    public RankingServiceImpl()
    {
    }

    public List<String> getCurrentPair()
    {
        if (isFinished())
        {
            return null;
        }

        List<String> pairs = new ArrayList<String>();

        int i = current_position;
        int j = current_position + current_gap;

        pairs.add(games.get(i));
        pairs.add(games.get(j));

        return pairs;
    }

    public int getCurrentRound()
    {
        if (isFinished())
        {
            return 0;
        }

        return 0;

//        int round = current_position +1;
//
//
//        return round;
    }

    public int getTotalRounds()
    {
        int size = games.size();

        int rounds = factorial(size - 1);
        return rounds;
    }

    private int factorial(int number)
    {
        if (number > 0)
        {
            return number + factorial(number - 1);
        }
        return number;
    }

    public boolean isFinished()
    {
        return current_gap >= games.size();
    }

    public void choose(String choice)
    {
        List<String> pair = getCurrentPair();

        String option1 = pair.get(0);
        String option2 = pair.get(1);

        if (values.get(option1) == null)
        {
            values.put(option1, 0);
        }
        if (values.get(option2) == null)
        {
            values.put(option2, 0);
        }

        int value = values.get(choice);
        value++;

        values.put(choice, value);

        advance();
    }

    private void advance()
    {
        if (!isFinished())
        {
            current_position++;

            if (current_position + current_gap >= games.size())
            {
                current_position = 0;
                current_gap++;
            }
        }
    }


    public List<String> getSortedList()
    {
        if (!isFinished())
        {
            return null;
        }

        List<String> sorted = new ArrayList<String>();
        sorted.addAll(games);

        Collections.sort(sorted, new Comparator<String>()
        {
            public int compare(String o1, String o2)
            {
                int v1 = values.get(o1);
                int v2 = values.get(o2);

                return v2 - v1;
            }
        });

        return sorted;
    }



    public void argorithm(List list)
    {
        int size = list.size();
        int gap = 1;

        while (gap < size)
        {
            for(int i = 0; i < size-gap; i++)
            {
                //compare
            }
            gap++;
        }
    }
}
