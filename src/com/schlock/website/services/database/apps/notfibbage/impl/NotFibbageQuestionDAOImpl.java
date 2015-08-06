package com.schlock.website.services.database.apps.notfibbage.impl;

import com.schlock.website.entities.apps.notfibbage.NotFibbageQuestion;
import com.schlock.website.services.database.BaseDAOImpl;
import com.schlock.website.services.database.apps.notfibbage.NotFibbageQuestionDAO;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class NotFibbageQuestionDAOImpl extends BaseDAOImpl<NotFibbageQuestion> implements NotFibbageQuestionDAO
{
    public NotFibbageQuestionDAOImpl(Session session)
    {
        super(NotFibbageQuestion.class, session);
    }

    public List<NotFibbageQuestion> getByCategory(Long categoryId)
    {
        String text = "select q " +
                        " from NotFibbageQuestion q " +
                        " join q.category c " +
                        " where c.id = :categoryId " +
                        " order by q.order, q.id ";

        Query query = session.createQuery(text);
        query.setLong("categoryId", categoryId);

        return query.list();
    }
}

