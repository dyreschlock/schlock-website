package com.schlock.website.components.apps.bingo;

import com.schlock.website.services.apps.bingo.BingoRandomizer;
import com.schlock.website.services.apps.bingo.impl.HighSchoolSelfIntroBingoRandomizerImpl;
import com.schlock.website.services.apps.bingo.impl.HighSchoolSelfIntroBingoService;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.ArrayList;
import java.util.List;

public class HighSchoolSelfIntroBingoSheet
{
    private static final int BINGO_SIZE = HighSchoolSelfIntroBingoRandomizerImpl.BINGO_SIZE;

    @Parameter(required = true)
    private String courseName;

    @Inject
    @HighSchoolSelfIntroBingoService
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
            itemsList = bingoService.createOrder(courseName);
        }
        return itemsList;
    }
}
