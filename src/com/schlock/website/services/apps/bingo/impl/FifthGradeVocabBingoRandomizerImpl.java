package com.schlock.website.services.apps.bingo.impl;

import com.schlock.website.services.apps.bingo.BingoRandomizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FifthGradeVocabBingoRandomizerImpl implements BingoRandomizer
{
    public static final int FOUR_BY_FOUR = 4;
    public static final int FIVE_BY_FIVE = 5;

    public static final int BINGO_SIZE = FIVE_BY_FIVE;

    private static final List<String> jobs = Arrays.asList("Artist",
            "Baker",
            "Bus Driver",
            "Cabin Attendant",
            "Comedian",
            "Cook",
            "Dentist",
            "Doctor",
            "Farmer",
            "Fire Fighter",
            "Florist",
            "Singer",
            "Soccer Player",
            "Teacher",
            "Vet",
            "Zoo Keeper");


    private static final List<String> lesson_4 = Arrays.asList("apples", "bananas", "baseball", "basketball", "birds", "cats", "cherries", "dogs", "grapes", "ice cream", "juice", "kiwi.fruits", "lemons", "melons", "milk", "oranges", "peaches", "pineapples", "rabbits", "soccer", "spiders", "strawberries", "swimming");

    private static final List<String> vegetables = Arrays.asList("carrot", "corn", "eggplant", "green.pepper", "onion", "potato", "pumpkin", "tomato");

    private static final List<String> fruits = Arrays.asList("apple", "banana", "cherry", "grapes", "lemon", "melon", "orange", "peach", "pear", "pineapple", "strawberry", "watermelon");

    private static final List<String> animals = Arrays.asList("dog", "cat", "chicken", "fish", "giraffe", "goat", "hamster", "tiger", "spider", "horse", "cow");

    private static final List<String> sports = Arrays.asList("baseball", "basketball", "golf", "football", "soccer", "volleyball", "hockey", "skiing", "swimming", "table.tennis");


    private List<String> items = new ArrayList<String>();

    private int limit;


    public FifthGradeVocabBingoRandomizerImpl()
    {
        limit = BINGO_SIZE * BINGO_SIZE;

        items.clear();
        items.addAll(vegetables);
        items.addAll(fruits);
        items.addAll(animals);
        items.addAll(sports);

        System.out.println("Number of items: " + items.size());
    }

    public List<String> createOrder()
    {
        List<String> order = new ArrayList<String>();

        while(order.size() < limit)
        {
            double random = Math.random();
            int selection = (int) (random * items.size());
            String job = items.get(selection);

            if (!order.contains(job))
            {
                order.add(job);
            }
        }
        return order;
    }
}
