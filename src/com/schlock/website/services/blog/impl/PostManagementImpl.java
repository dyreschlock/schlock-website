package com.schlock.website.services.blog.impl;

import com.schlock.website.entities.blog.*;
import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.blog.PhotoFileFilter;
import com.schlock.website.services.blog.PostManagement;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.services.ApplicationStateManager;

import java.io.File;
import java.util.*;

public class PostManagementImpl implements PostManagement
{
    private final static String VALID_UUID_CHARACTERS = "abcdefghijklmnopqrstuvwxyz1234567890";
    private final static int PREVIEW_LENGTH = 900;

    private final static String BREAK = "<break>";

    private final static String ITALICS_OPEN = "<i>";
    private final static String ITALICS_CLOSE = "</i>";

    private final static String EM_OPEN = "<em>";
    private final static String EM_CLOSE = "</em>";

    private final static String BOLD_OPEN = "<b>";
    private final static String BOLD_CLOSE = "</b>";

    private final static String STRONG_OPEN = "<strong>";
    private final static String STRONG_CLOSE = "</strong>";

    private final static String UNDER_OPEN = "<b>";
    private final static String UNDER_CLOSE = "</b>";

    private final ApplicationStateManager asoManager;

    private final DeploymentContext deploymentContext;
    private final PostDAO postDAO;

    private Map<String, Date> cachedUpdateTime;
    private Set<String> cachedUuids;

    public PostManagementImpl(ApplicationStateManager asoManager,
                                DeploymentContext deploymentContext,
                                PostDAO postDAO)
    {
        this.asoManager = asoManager;

        this.deploymentContext = deploymentContext;
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

    public Post createPost(String postTitle, String postContent)
    {
        if (StringUtils.isEmpty(postTitle))
        {
            return null;
        }

        Calendar cal = Calendar.getInstance();
        Date created = cal.getTime();

        return createPost(created, postTitle, postContent);
    }

    public Post createPost(Date created, String postTitle, String postContent)
    {
        String uuid = createUuid(postTitle);

        Post post = new Post(uuid);
        post.setCreated(created);

        post.setTitle(postTitle);
        post.setBodyText(postContent);

        postDAO.save(post);

        return post;
    }

    public Date getUpdatedTime(Page page)
    {
        if (cachedUpdateTime == null)
        {
            cachedUpdateTime = new HashMap<String, Date>();
        }

        Date updatedTime = cachedUpdateTime.get(page.getUuid());
        if (updatedTime == null)
        {
            if (page.isClub())
            {
                ClubPost recent = postDAO.getMostRecentClubPost(true);
                updatedTime = recent.getCreated();
            }
            if (page.isAlt())
            {
                LessonPost recent = postDAO.getMostRecentLessonPost(true);
                updatedTime = recent.getCreated();
            }
        }

        cachedUpdateTime.put(page.getUuid(), updatedTime);
        return updatedTime;
    }

    public void regenerateAllPostHTML()
    {
        for (AbstractPost post : postDAO.getAll())
        {
            setPostHTML(post);
        }
    }

    public void setPostHTML(AbstractPost post)
    {
        String text = post.getBodyText();
        String html = generatePostHTML(text);
        post.setBodyHTML(html);
        postDAO.save(post);
    }

    public String generatePostPreview(AbstractPost post)
    {
        String tempText = post.getBodyText();

        int brake = 0;
        if (StringUtils.containsIgnoreCase(tempText, BREAK))
        {
            brake = tempText.indexOf(BREAK);
        }
        else
        {
            brake = tempText.length();
            if (brake > PREVIEW_LENGTH)
            {
                brake = PREVIEW_LENGTH;
            }
        }
        tempText = tempText.substring(0, brake);


        int openTag = tempText.lastIndexOf("<");
        int closeTag = tempText.lastIndexOf(">");
        if (openTag != -1 && openTag > closeTag)
        {
            tempText = tempText.substring(0, openTag);
        }

        tempText = closeTags(tempText, BOLD_OPEN, BOLD_CLOSE);
        tempText = closeTags(tempText, STRONG_OPEN, STRONG_CLOSE);
        tempText = closeTags(tempText, UNDER_OPEN, UNDER_CLOSE);
        tempText = closeTags(tempText, ITALICS_OPEN, ITALICS_CLOSE);
        tempText = closeTags(tempText, EM_OPEN, EM_CLOSE);

        String html = generatePostHTML(tempText);
        return html;
    }

    private String closeTags(final String text, final String OPEN, final String CLOSE)
    {
        int oi = text.lastIndexOf(OPEN);
        int ci = text.lastIndexOf(CLOSE);
        if (oi != -1 && oi > ci)
        {
            return text + CLOSE;
        }
        return text;
    }

    private String generatePostHTML(String tempText)
    {
        if (StringUtils.isBlank(tempText))
        {
            return tempText;
        }

        String html = tempText;

        html = html.replaceAll(BREAK, "");
        html = changeLineBreaksToHtmlTags(html);
        html = changePostTitlesForCss(html);
        html = changeImagesAndLinksToLocal(html);

        return html;
    }

    private String changeLineBreaksToHtmlTags(String h)
    {
        String html = "";

        String[] paragraphs = h.split("\r\n\r\n");
        for (int i = 0; i < paragraphs.length; i++)
        {
            String[] paragraphs2 = paragraphs[i].split("\n\n");
            for (int j = 0; j < paragraphs2.length; j++)
            {
                String p = paragraphs2[j];

                html += "<p>" + p + "</p>";
            }
        }

        html = html.replaceAll("\r\n", "<br/>");
        html = html.replaceAll("\n", "<br/>");

        html = html.replaceAll("<p></p>", "");

        return html;
    }

    private String changePostTitlesForCss(String h)
    {
        String html = h;

        html = html.replaceAll("<strong>", "<b>");
        html = html.replaceAll("</strong>", "</b>");

        html = tagReplacement(html, "<p><b>", "</b>", "</p>", "<div class='title'>", "</div>");
        html = tagReplacement(html, "<p><br/><b>", "</b>", "</p>", "<div class='title'>", "</div>");

        html = tagReplacement(html, "<p><b>", "</b>", "<br/>", "<div class='title'>", "</div><p>");

        html = tagReplacement(html, "<p><span style=\"font-weight: bold\">", "</span>", "</p>", "<div class='title'>", "</div>");

        return html;
    }

    private String tagReplacement(final String h,
                                  final String FIND_START,
                                  final String FIND_END_P1,
                                  final String FIND_END_P2,
                                  final String REPLACE_START,
                                  final String REPLACE_END)
    {
        final String FIND_END = FIND_END_P1 + FIND_END_P2;

        String html = h;

        int start = html.indexOf(FIND_START);
        while (start != -1)
        {
            int end = html.substring(start).indexOf(FIND_END);
            if (end != -1)
            {
                end += start;

                int p_end = start + html.substring(start).indexOf(FIND_END_P2);
                if (p_end == (end + FIND_END_P1.length()))
                {
                    String newHtml = "";

                    newHtml += html.substring(0, start);
                    newHtml += REPLACE_START;
                    newHtml += html.substring(start + FIND_START.length(), end);
                    newHtml += REPLACE_END;
                    newHtml += html.substring(end + FIND_END.length());

                    html = newHtml;
                }
                else
                {
                    start++;
                }
            }
            else
            {
                start++;
            }

            int index = html.substring(start).indexOf(FIND_START);
            if (index != -1)
            {
                start += index;
            }
            else
            {
                start = index;
            }
        }
        return html;
    }

    private String changeImagesAndLinksToLocal(String h)
    {
        String html = h;

        html = html.replaceAll("href=\"http://theschlock.com/", "href=\"/");
        html = html.replaceAll("href=\"http://www.theschlock.com/", "href=\"/");

        html = html.replaceAll("src=\"http://theschlock.com/", "src=\"/");
        html = html.replaceAll("src=\"http://www.theschlock.com/", "src=\"/");

        return html;
    }

    public List<String> getGalleryImages(AbstractPost post)
    {
        String galleryName = post.getGalleryName();
        if (StringUtils.isBlank(galleryName))
        {
            return Collections.EMPTY_LIST;
        }

        String photoLocation = deploymentContext.photoLocation();
        File gallery = new File(photoLocation + galleryName);
        if (!gallery.exists())
        {
             return Collections.EMPTY_LIST;
        }

        File[] files = gallery.listFiles(new PhotoFileFilter());

        List<String> images = new ArrayList<String>();
        for (File file : files)
        {
            String path = file.getAbsolutePath();
            int i = path.indexOf(DeploymentContext.PHOTO_DIR);

            path = "/" + path.substring(i);
            images.add(path);
        }

        Collections.sort(images, new Comparator<String>()
        {
            public int compare(String s1, String s2)
            {
                return s1.compareToIgnoreCase(s2);
            }
        });

        return images;
    }


    public String getPostImage(AbstractPost post)
    {
        List<String> images = getGalleryImages(post);

        String header = post.getCoverImage();
        if (StringUtils.isNotBlank(header))
        {
            for (String image : images)
            {
                if (StringUtils.endsWithIgnoreCase(image, header))
                {
                    return image;
                }
            }
        }

        int index = images.size() / 4;
        index = images.size() - index;

        if (index < 0)
        {
            index = 0;
        }
        if (index >= images.size())
        {
            index = images.size() -1;
        }

        if (images.size() > 0)
        {
            return images.get(index);
        }
        return null;
    }

    public String getStylizedHTMLTitle(AbstractPost post)
    {
        String title = post.getTitle();

        String html = "";

        String[] parts = title.split("@");
        if (parts.length == 2)
        {
            String name = parts[0];
            String venue = parts[1];

            name = splitBy(name, ",", "<br />");

            String[] names = name.split(",");
            name = "";
            for (int i = 0; i < names.length; i++)
            {
                if (i != 0)
                {
                    name += "<br />";
                }
                name += names[i];
            }

            html = name + "<br />" + "@" + venue;
        }
        else
        {
            html = splitBy(title, " - ", "<br />");
        }
        return html;
    }

    private String splitBy(final String str, String split, String add)
    {
        String[] parts = str.split(split);

        String newStr = "";
        for (int i = 0; i < parts.length; i++)
        {
            if (i != 0)
            {
                newStr += add;
            }
            newStr += parts[i];
        }
        return newStr;
    }


    public List<Post> getTop3Posts(Category category)
    {
        final int LIMIT = 3;

        List<Post> posts = new ArrayList<Post>();

        boolean unpublished = asoManager.get(ViewState.class).isShowUnpublished();
        Long categoryId = category.getId();

        Post recentGallery = postDAO.getMostRecentPostWithGallery(unpublished, categoryId);
        if (recentGallery != null)
        {
            posts.add(recentGallery);
        }

        List<Post> pinned = postDAO.getMostRecentPinnedPosts(LIMIT, unpublished, categoryId);
        for (Post post : pinned)
        {
            if (!posts.contains(post) && posts.size() < LIMIT)
            {
                posts.add(post);
            }
        }

        if (posts.size() < LIMIT)
        {
            List<Post> recent = postDAO.getMostRecentPosts(LIMIT, unpublished, categoryId);
            for (Post post : recent)
            {
                if (!posts.contains(post) && posts.size() < LIMIT)
                {
                    posts.add(post);
                }
            }
        }

        return posts;
    }
}
