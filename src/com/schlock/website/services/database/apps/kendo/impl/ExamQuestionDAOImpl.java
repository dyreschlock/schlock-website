package com.schlock.website.services.database.apps.kendo.impl;

import com.schlock.website.entities.apps.kendo.ExamCategory;
import com.schlock.website.entities.apps.kendo.ExamQuestion;
import com.schlock.website.services.database.BaseDAOImpl;
import com.schlock.website.services.database.apps.kendo.ExamQuestionDAO;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class ExamQuestionDAOImpl extends BaseDAOImpl<ExamQuestion> implements ExamQuestionDAO
{
    public ExamQuestionDAOImpl(Session session)
    {
        super(ExamQuestion.class, session);
    }


    public List<ExamQuestion> getQuestions(int level, int count)
    {
        List<ExamQuestion> questions = new ArrayList<ExamQuestion>();

        questions.add(getMainQuestion());
        questions.addAll(getRandomQuestions(level, count - 1));

        return questions;
    }

    public ExamQuestion getMainQuestion()
    {
        ExamCategory cat = ExamCategory.MEANING;
        int number = 1;

        String text = "from ExamQuestion q " +
                        " where q.category = :cat " +
                        " and q.number = :number ";

        Query query = session.createQuery(text);
        query.setParameter("cat", cat);
        query.setInteger("number", number);

        return (ExamQuestion) singleResult(query);
    }

    private List<ExamQuestion> getRandomQuestions(int level, int count)
    {
        String text = "from ExamQuestion q " +
                        " where q.level <= :level ";

        Query query = session.createQuery(text);
        query.setInteger("level", level);

        List<ExamQuestion> questions = query.list();

        List<ExamQuestion> result = new ArrayList<ExamQuestion>();
        while (result.size() < count)
        {
            int index = (int) (Math.random() * questions.size());

            ExamQuestion q = questions.get(index);
            if (!result.contains(q) && index != 0)
            {
                result.add(q);
            }
        }
        return result;
    }
}
