package com.schlock.website.services.database.apps.notfibbage.impl;

import com.schlock.website.entities.apps.notfibbage.NotFibbageQuestion;
import com.schlock.website.services.database.BaseDAOImpl;
import com.schlock.website.services.database.apps.notfibbage.NotFibbageQuestionDAO;
import org.hibernate.Session;

public class NotFibbageQuestionDAOImpl extends BaseDAOImpl<NotFibbageQuestion> implements NotFibbageQuestionDAO
{
    public NotFibbageQuestionDAOImpl(Session session)
    {
        super(NotFibbageQuestion.class, session);
    }
}

