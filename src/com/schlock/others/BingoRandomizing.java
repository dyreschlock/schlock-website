package com.schlock.others;

import com.schlock.website.services.apps.bingo.BingoRandomizer;
import com.schlock.website.services.apps.bingo.impl.FifthGradeVocabBingoRandomizerImpl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;


public class BingoRandomizing
{
    private static final String OUTPUT_LOCATION = "/jet/output/";
//    public static final String OUTPUT_LOCATION = "Hi Friends 2/Lesson 8/Bingo Order/";
    public static final String FILENAME = "bingo.order";

    public static final int ITERATIONS = 34;

    private BingoRandomizer service = new FifthGradeVocabBingoRandomizerImpl();

    public BingoRandomizing()
    {
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
        List<String> content = service.createOrder();

        String printableContent = join(content);
        writeToFile(file, printableContent);
    }

    private String join(List<String> strings)
    {
        String output = "";
        for(int i = 0; i < strings.size(); i++)
        {
            if (i % FifthGradeVocabBingoRandomizerImpl.BINGO_SIZE == 0)
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
