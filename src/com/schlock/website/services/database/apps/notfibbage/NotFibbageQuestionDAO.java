package com.schlock.website.services.database.apps.notfibbage;

import com.schlock.website.entities.apps.notfibbage.NotFibbageQuestion;
import com.schlock.website.services.database.BaseDAO;

import java.util.List;

public interface NotFibbageQuestionDAO extends BaseDAO<NotFibbageQuestion>
{
    public List<NotFibbageQuestion> getByCategory(Long categoryId);
}
