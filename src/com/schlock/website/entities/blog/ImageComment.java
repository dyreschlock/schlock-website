package com.schlock.website.entities.blog;

import com.schlock.website.entities.Persisted;

public class ImageComment extends Persisted
{
    private String imageName;

    private String commentText;
    private String commentHTML;



    public String getImageName() { return imageName; }

    public void setImageName(String imageName) { this.imageName = imageName; }

    public String getCommentText() { return commentText; }

    public void setCommentText(String commentText) { this.commentText = commentText; }

    public String getCommentHTML() { return commentHTML; }

    public void setCommentHTML(String commentHTML) { this.commentHTML = commentHTML; }
}
