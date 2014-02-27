package com.schlock.website.services.blog.impl;

import com.schlock.website.entities.blog.Post;
import com.schlock.website.entities.blog.TextPost;
import com.schlock.website.services.blog.PostManagement;
import com.schlock.website.services.database.blog.PostDAO;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.TimeZone;

public class PostManagementImpl implements PostManagement
{
    private final static String VALID_UUID_CHARACTERS = "abcdefghijklmnopqrstuvwxyz1234567890";

    private final PostDAO postDAO;

    private Set<String> cachedUuids;

    public PostManagementImpl(PostDAO postDAO)
    {
        this.postDAO = postDAO;
    }


    private Set<String> getCachedUuids()
    {
        if(cachedUuids == null)
        {
            cachedUuids = postDAO.getAllUuids();
        }
        return cachedUuids;
    }

    private String createUuid(String title)
    {
        String baseUuid = "";

        for (char c : title.trim().toLowerCase().toCharArray())
        {
            if (VALID_UUID_CHARACTERS.indexOf(c) != -1)
            {
                baseUuid += c;
            }
            if ((c == ' ' || c == '-') &&
                    baseUuid.lastIndexOf('-') != baseUuid.length()-1)
            {
                baseUuid += '-';
            }
        }
        if (baseUuid.lastIndexOf('-') == baseUuid.length() - 1)
        {
            baseUuid = baseUuid.substring(0, baseUuid.length() - 1);
        }

        String uuid = baseUuid;

        int increment = 1;
        while (getCachedUuids().contains(uuid))
        {
            increment++;

            uuid = baseUuid + "-" + increment;
        }

        cachedUuids.add(uuid);
        return uuid;
    }

    public Post createTextPost(String postTitle, String postContent)
    {
        Calendar cal = Calendar.getInstance();
        Date created = cal.getTime();

        cal.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date createdGMT = cal.getTime();

        return createTextPost(created, createdGMT, postTitle, postContent);
    }

    public Post createTextPost(Date created, Date createdGMT, String postTitle, String postContent)
    {
        String uuid = createUuid(postTitle);

        TextPost post = new TextPost(uuid);
        post.setCreated(created);
        post.setCreatedGMT(createdGMT);

        post.setTitle(postTitle);
        post.setBodyText(postContent);

        postDAO.save(post);

        return post;
    }
}
