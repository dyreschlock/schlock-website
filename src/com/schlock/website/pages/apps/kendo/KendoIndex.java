package com.schlock.website.pages.apps.kendo;

import com.schlock.website.entities.apps.kendo.ExamQuestion;
import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.services.blog.PostManagement;
import com.schlock.website.services.database.apps.kendo.ExamQuestionDAO;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

public class KendoIndex
{
    @Inject
    private PostDAO postDAO;

    @Inject
    private ExamQuestionDAO questionDAO;

    @Inject
    private PostManagement postManagement;

    @Inject
    private Messages messages;


    @Property
    private ExamQuestion currentQuestion;


    public String getQuestionHtml()
    {
        String question = currentQuestion.getJapaneseQuestion();
        String html = postManagement.wrapJapaneseTextInTags(question);

        String category = messages.get(currentQuestion.getCategory().name());
        String prefix = messages.get("question");

        String text = category + String.format(prefix, currentQuestion.getNumber()) + " " + html;
        return text;
    }

    public String getQuestionResponse()
    {
        return "";
    }

    public void setQuestionResponse(String response)
    {
    }



    private List<ExamQuestion> cachedQuestions;

    public List<ExamQuestion> getQuestions()
    {
        if (cachedQuestions == null || cachedQuestions.isEmpty())
        {
            cachedQuestions = questionDAO.getQuestions(ExamQuestion.LEVEL_SHODAN, 3);
        }
        return cachedQuestions;
    }

    private AbstractPost cachedPage;

    public AbstractPost getPage()
    {
        if (cachedPage == null)
        {
            cachedPage = (AbstractPost) postDAO.getByUuid(AbstractPost.KENDO_UUID);
        }
        return cachedPage;
    }
}
