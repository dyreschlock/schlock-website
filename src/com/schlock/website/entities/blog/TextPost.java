package com.schlock.website.entities.blog;

public class TextPost extends Post
{
    private String bodyText;
    private String bodyHTML;

    public TextPost(String uuid)
    {
        super(uuid);
    }

    public String getBodyText()
    {
        return bodyText;
    }

    public void setBodyText(String bodyText)
    {
        this.bodyText = bodyText;
    }

    public String getBodyHTML()
    {
        return bodyHTML;
    }

    public void setBodyHTML(String bodyHTML)
    {
        this.bodyHTML = bodyHTML;
    }
}
