package com.schlock.website.components.apps.bingo;

import com.schlock.website.services.apps.bingo.BingoRandomizer;
import com.schlock.website.services.apps.bingo.impl.FifthGradeVocabBingoRandomizerImpl;
import com.schlock.website.services.apps.bingo.impl.FifthGradeVocabBingoService;
import org.apache.tapestry5.annotations.Property;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class FifthGradeVocabBingoSheet
{
    private static final int BINGO_SIZE = FifthGradeVocabBingoRandomizerImpl.BINGO_SIZE;
    private static final String IMAGE_FOLDER = "/image/bingo/";

    @Inject
    @FifthGradeVocabBingoService
    private BingoRandomizer bingoService;


    private List<String> itemsList;

    @Property
    private List<String> row;

    @Property
    private String bingoColumn;


    public List<List<String>> getRows()
    {
        List<List<String>> rows = new ArrayList<List<String>>();

        for(int i = 0; i < BINGO_SIZE; i++)
        {
            int start = i * BINGO_SIZE;
            int end = start + BINGO_SIZE;

            List<String> row = getItemsList().subList(start, end);
            rows.add(row);
        }
        return rows;
    }


    private List<String> getItemsList()
    {
        if (itemsList == null)
        {
            itemsList = bingoService.createOrder();
        }
        return itemsList;
    }

    public String getImage()
    {
//        String location = IMAGE_FOLDER + "tiger.jpg";
        String location = IMAGE_FOLDER + bingoColumn + ".jpg";
        return location;
    }
}
