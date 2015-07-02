package com.schlock.others;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class BingoRandomizing
{
    private static final String OUTPUT_LOCATION = "/jet/output/";
//    public static final String OUTPUT_LOCATION = "Hi Friends 2/Lesson 8/Bingo Order/";
    public static final String FILENAME = "bingo.order";

    public static final int ITERATIONS = 34;

//    public static final List<String> jobs = Arrays.asList("Artist",
//                                                            "Baker",
//                                                            "Bus Driver",
//                                                            "Cabin Attendant",
//                                                            "Comedian",
//                                                            "Cook",
//                                                            "Dentist",
//                                                            "Doctor",
//                                                            "Farmer",
//                                                            "Fire Fighter",
//                                                            "Florist",
//                                                            "Singer",
//                                                            "Soccer Player",
//                                                            "Teacher",
//                                                            "Vet",
//                                                            "Zoo Keeper");

    public static final List<String> jobs = Arrays.asList("apples", "bananas", "baseball", "basketball", "birds", "cats", "cherries", "dogs", "grapes", "ice cream", "juice", "kiwi fruits", "lemons", "melons", "milk", "oranges", "peaches", "pineapples", "rabbits", "soccer", "spiders", "strawberries", "swimming");

    private int limit;

    public BingoRandomizing()
    {
//        limit = jobs.size();
        limit = 16;
    }

    public void run()
    {
        File directory = new File(OUTPUT_LOCATION);
        directory.mkdir();

        for(int i = 0; i < ITERATIONS; i++)
        {
            try
            {
                createBingoOrder(directory, i);
            }
            catch(Exception e)
            {
                System.out.println("Exception thrown in iteration " + i);
                e.printStackTrace();
            }
            System.out.println("Finished iteration number: " + i);
        }
    }

    private void createBingoOrder(File directory, int iteration) throws Exception
    {
        File file = new File(directory, FILENAME + "." + (iteration +1) + ".txt");
        String content = createOrder();

        writeToFile(file, content);
    }

    private String createOrder()
    {
        List<String> order = new ArrayList<String>();

        while(order.size() < limit)
        {
            double random = Math.random();
            int selection = (int) (random * jobs.size());
            String job = jobs.get(selection);

            if (!order.contains(job))
            {
                order.add(job);
            }
        }
        return join(order);
    }

    private String join(List<String> strings)
    {
        String output = "";
        for(int i = 0; i < strings.size(); i++)
        {
            if (i % 4 == 0)
            {
                output += "\r\n";
            }

            String s = strings.get(i);
            if (output.length() != 0)
            {
                output = output + ", ";
            }
            output = output + s;
        }
        return output;
    }

    private void writeToFile(File file, String content) throws Exception
    {
        if(!file.exists())
        {
            file.createNewFile();
        }

        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter out = new BufferedWriter(fw);

        out.write(content);
        out.close();
    }

    public static void main(String args[])
    {
        new BingoRandomizing().run();
    }
}
