package com.schlock.website.services.database.apps.bingo;

import com.schlock.website.entities.apps.bingo.BingoOption;
import com.schlock.website.services.database.BaseDAO;

import java.util.List;

public interface BingoOptionDAO extends BaseDAO<BingoOption>
{
    public List<BingoOption> getBySheetAndCategory(String sheet, int category);

    public List<BingoOption> getAllBySheet(String sheet);
}
