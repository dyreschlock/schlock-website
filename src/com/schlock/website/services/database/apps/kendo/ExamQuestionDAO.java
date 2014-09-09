package com.schlock.website.services.database.apps.kendo;

import com.schlock.website.entities.apps.kendo.ExamQuestion;
import com.schlock.website.services.database.BaseDAO;

import java.util.List;

public interface ExamQuestionDAO extends BaseDAO<ExamQuestion>
{
    public List<ExamQuestion> getQuestions(int level, int number);
}
