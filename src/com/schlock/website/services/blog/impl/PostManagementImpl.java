package com.schlock.website.services.blog.impl;

import com.schlock.website.entities.blog.*;
import com.schlock.website.entities.old.SiteVersion;
import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.blog.ImageManagement;
import com.schlock.website.services.blog.KeywordManagement;
import com.schlock.website.services.blog.PostManagement;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.services.ApplicationStateManager;

import java.util.*;

public class PostManagementImpl implements PostManagement
{
    private static final List<String> IGNORE_CATEGORY_UUIDS_WHEN_FINDING_RELATED_POSTS = Arrays.asList("reviews");

    private final static String VALID_UUID_CHARACTERS = "abcdefghijklmnopqrstuvwxyz1234567890";
    private final static int PREVIEW_LENGTH = 900;
    private final static int POST_DESCRIPTION_LENGTH = 160;


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

    private final DeploymentContext context;

    private final KeywordManagement keywordManagement;
    private final ImageManagement imageManagement;
    private final PostDAO postDAO;

    private Map<String, Date> cachedUpdateTime;
    private Set<String> cachedUuids;

    public PostManagementImpl(ApplicationStateManager asoManager,
                                DeploymentContext context,
                                KeywordManagement keywordManagement,
                                ImageManagement imageManagement,
                                PostDAO postDAO)
    {
        this.asoManager = asoManager;

        this.context = context;

        this.keywordManagement = keywordManagement;
        this.imageManagement = imageManagement;
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
            if (page.isCourseRelated())
            {
                LessonPost recent = postDAO.getMostRecentLessonPost(true);
                updatedTime = recent.getCreated();
            }
            if (page.isProjects())
            {
                AbstractPost recent = postDAO.getMostRecentProject(true);
                updatedTime = recent.getCreated();
            }
        }

        cachedUpdateTime.put(page.getUuid(), updatedTime);

        return null;
//        return updatedTime;
    }

    public void regenerateAllPostHTML()
    {
        for(AbstractPost post : postDAO.getAll())
        {
            String text = post.getBodyText();
            String html = generatePostHTML(post, text, null, false);
            post.setBodyHTML(html);

            String keyString = post.getKeywordString();
            List<Keyword> keywords = keywordManagement.generateKeywords(keyString);
            post.setKeywords(keywords);

            postDAO.save(post);
        }
    }

    public void regeneratePostNumbers()
    {
        List<Post> posts = postDAO.getAllVisibleByDate();

        int count = 1;
        for(Post post : posts)
        {
            post.setNumber(count);
            postDAO.save(post);

            count++;
        }
    }

    public String generateCommentHTML(String comment)
    {
        String output = comment.trim();

        output = replace(output,"\"", "\\\"");

        return output;
    }

    private String replace(String fullString, String toReplace, String replacement)
    {
        String newString = "";
        String remainingString = fullString;

        int index = remainingString.indexOf(toReplace);
        while(index > 0)
        {
            newString += remainingString.substring(0, index) + replacement;
            remainingString = remainingString.substring(index + toReplace.length());

            index = remainingString.indexOf(toReplace);
        }

        newString += remainingString;

        return newString;
    }

    public String generatePostDescription(AbstractPost post)
    {
        String description = generatePostPreviewContent(post, POST_DESCRIPTION_LENGTH);
        if (description != null)
        {
            description = description.replaceAll("\r\n\r\n", " ");
            description = description.replaceAll("\r\n", " ");
            description = description.replaceAll("\n\n", " ");
            description = description.replaceAll("\n", " ");
        }
        return description;
    }

    public String generatePostPreview(AbstractPost post)
    {
        String tempText = generatePostPreviewContent(post, PREVIEW_LENGTH);

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

    private String generatePostPreviewContent(AbstractPost post, final int MAX_LENGTH)
    {
        String description = post.getBodyText();
        if (StringUtils.isNotBlank(post.getBlurb()))
        {
            description = post.getBlurb();
        }
        if (StringUtils.contains(description, BREAK))
        {
            int breek = description.indexOf(BREAK);
            description = description.substring(0, breek);
        }
        if (description != null && description.length() > MAX_LENGTH)
        {
            description = description.substring(0, MAX_LENGTH);

            int lastSpace = description.lastIndexOf(" ");
            description = description.substring(0, lastSpace);
            description += "...";
        }
        return description;
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

    public String generatePostHTML(AbstractPost post)
    {
        return generatePostHTML(post, null);
    }

    public String generateRssHTML(AbstractPost post)
    {
        String text = post.getBodyText();
        String html = generatePostHTML(post, text, null, true);
        return html;
    }

    public String generatePostHTML(AbstractPost post, SiteVersion version)
    {
        String text = post.getBodyText();
        String html = generatePostHTML(post, text, version, false);
        return html;
    }

    public String generatePostHTML(String htmlContents)
    {
        return generatePostHTML(null, htmlContents, null, false);
    }

    private String generatePostHTML(AbstractPost post, String tempText, SiteVersion oldVersion, boolean rssFeed)
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
        if(rssFeed)
        {
            html = changeLinksToAbsolute(html);
        }

        html = removeBreaksFromBetweenHtmlCode(html);

        html = wrapJapaneseTextInTags(html);

        boolean useGalleryLink = !rssFeed && oldVersion == null;
        html = imageManagement.updateImagesInHTML(post, html, useGalleryLink);

        if (oldVersion != null)
        {
            html = changeLinksToOldVersion(html, oldVersion);
        }

        return html;
    }

    private String changeLineBreaksToHtmlTags(String h)
    {
        StringBuilder sb = new StringBuilder();

        String[] paragraphs = h.split("\r\n\r\n");
        for (int i = 0; i < paragraphs.length; i++)
        {
            String[] paragraphs2 = paragraphs[i].split("\n\n");
            for (int j = 0; j < paragraphs2.length; j++)
            {
                String p = paragraphs2[j];

                String html;
                if (StringUtils.containsIgnoreCase(p, "<img src"))
                {
                    html = "<p class='img'>" + p + "</p>";
                }
                else if(StringUtils.containsIgnoreCase(p, "<table"))
                {
                    html = p;
                }
                else
                {
                    html = "<p>" + p + "</p>";
                }
                sb.append(html);
            }
        }

        String html = sb.toString();

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

        html = tagReplacement(html, "<p><b>", "</b>", "</p>", "<div class='title'><b>", "</b></div>");
        html = tagReplacement(html, "<p><br/><b>", "</b>", "</p>", "<div class='title'><b>", "</b></div>");

        html = tagReplacement(html, "<p><b>", "</b>", "<br/>", "<div class='title'><b>", "</b></div><p>");

        html = tagReplacement(html, "<p><span style=\"font-weight: bold\">", "</span>", "</p>", "<div class='title'><b>", "</b></div>");

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
                    StringBuilder newHtml = new StringBuilder();

                    newHtml.append(html.substring(0, start));
                    newHtml.append(REPLACE_START);
                    newHtml.append(html.substring(start + FIND_START.length(), end));
                    newHtml.append(REPLACE_END);
                    newHtml.append(html.substring(end + FIND_END.length()));

                    html = newHtml.toString();
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

    private String changeLinksToAbsolute(String h)
    {
        String domain = context.webDomain();

        String html = h.replaceAll("href=\"/", "href=\"" + domain);
        return html;
    }

    private String changeLinksToOldVersion(String h, SiteVersion oldVersion)
    {
        final String HREF_CODE = "href=\"/"; // href="/
        final String QUOTE = "\"";

        StringBuilder finishedHTML = new StringBuilder();
        String remainingHTML = h;

        while(StringUtils.isNotBlank(remainingHTML))
        {
            int index = remainingHTML.indexOf(HREF_CODE);
            if (index == -1)
            {
                finishedHTML.append(remainingHTML);
                remainingHTML = "";
            }
            else
            {
                index += HREF_CODE.length();

                finishedHTML.append(remainingHTML.substring(0, index));
                remainingHTML = remainingHTML.substring(index);

                index = remainingHTML.indexOf(QUOTE);

                String link = remainingHTML.substring(0, index);
                if (!link.contains("/"))
                {
                    link = oldVersion.linkPath(link);
                }

                finishedHTML.append(link);
                remainingHTML = remainingHTML.substring(index);
            }
        }

        return finishedHTML.toString();
    }

    private String removeBreaksFromBetweenHtmlCode(String h)
    {
        String html = h;

//        html = html.replaceAll("<tr><br/><td>", "<tr><td>");
//        html = html.replaceAll("</td><br/><td>", "</td><td>");
//        html = html.replaceAll("</td><br/></tr>", "</td></tr>");

        html = html.replaceAll("</tr><br/></table>", "</tr></table>");
        html = html.replaceAll("<table><br/><tr>", "<table><tr>");
        html = html.replaceAll("</tr><br/><tr>", "</tr><tr>");

        html = StringUtils.replaceEach(html, new String[]{"</div><br/><div"}, new String[]{"</div><div"});

        return html;
    }

    public String wrapJapaneseTextInTags(String h)
    {
        String html = h;

        int i = 0;
        while (i < html.length())
        {
            char c = html.charAt(i);
            if (isJapaneseCharacter(c))
            {
                int start = i;

                int end = i +1;
                while (end < html.length() &&
                        isJapaneseCharacter(html.charAt(end)))
                {
                    end++;
                }

                String jtext = html.substring(start, end);
                jtext = "<span class='ja'>" + jtext + "</span>";

                html = html.substring(0, start) + jtext + html.substring(end);

                i = start + jtext.length();
            }

            i++;
        }

        return html;
    }

    private boolean isJapaneseCharacter(char c)
    {
        int FULL_WIDTH_LEFT_PAREN = 65288;
        int FULL_WIDTH_RIGHT_PAREN = 65289;

        Character.UnicodeBlock b = Character.UnicodeBlock.of(c);

        return Character.UnicodeBlock.HIRAGANA.equals(b) ||
                Character.UnicodeBlock.KATAKANA.equals(b) ||
                Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS.equals(b) ||
                Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION.equals(b) ||
                Character.UnicodeBlock.CJK_COMPATIBILITY.equals(b) ||
                Character.UnicodeBlock.CJK_COMPATIBILITY_FORMS.equals(b) ||
                Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS.equals(b) ||
                ((int) c) == FULL_WIDTH_LEFT_PAREN ||
                ((int) c) == FULL_WIDTH_RIGHT_PAREN;
    }


    public String updateLinkToModernReference(String originalLink)
    {
        if (StringUtils.isBlank(originalLink) ||
                isNotLocalLinkReference(originalLink))
        {
            return originalLink;
        }

        final List<String> WHITE_LIST = Arrays.asList(
                "/misc/games2.html",
                "/pages/games/lst/games2.html"
        );
        if (WHITE_LIST.contains(originalLink))
        {
            return originalLink;
        }

        String possibleUUID = originalLink.substring(1);
        List<AbstractPost> posts = postDAO.getAllByUuid(possibleUUID);
        for(AbstractPost post : posts)
        {
            if (!post.isCoursePage())
            {
                return originalLink;
            }
        }

        if (originalLink.endsWith("events.html"))
        {
            return "/club";
        }

        final String PHOTO_LINK = "/photo/";
        if (originalLink.startsWith(PHOTO_LINK))
        {
            String galleryName = originalLink.substring(PHOTO_LINK.length());
            galleryName = galleryName.split("/")[0];

            AbstractPost post = postDAO.getByGalleryName(galleryName);
            if(post != null)
            {
                return "/" + post.getUuid();
            }
        }

        final String WORDPRESS_PREFIX = "/blog/?p=";
        final String WORDPRESS_PREFIX_TYPO = "/blog?p=";
        if (originalLink.startsWith(WORDPRESS_PREFIX) || originalLink.startsWith(WORDPRESS_PREFIX_TYPO))
        {
            int index = originalLink.indexOf("p=");
            String wpid = originalLink.substring(index + 2);
            AbstractPost post = postDAO.getByWpid(wpid);
            if (post != null)
            {
                return "/" + post.getUuid();
            }
        }

        final String CLUB_LINK = "/club/";
        if (originalLink.startsWith(CLUB_LINK))
        {
            String galleryName = originalLink.substring(CLUB_LINK.length());
            galleryName = galleryName.split("/")[0];

            AbstractPost post = postDAO.getByGalleryName(galleryName);
            if (post != null)
            {
                return "/" + post.getUuid();
            }
        }

        System.out.println("[Non-Image] " + originalLink);
        return originalLink;
    }

    private boolean isNotLocalLinkReference(String link)
    {
        boolean notLocal = link.startsWith("http") && !link.contains("theschlock.com");
        return notLocal;
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


    public List<Post> getTopPosts(Integer count, Long categoryId, Set<Long> excludeIds)
    {
        return getTopPosts(count, null, null, categoryId, excludeIds);
    }

    public List<Post> getTopPosts(Integer count, Integer year, Integer month, Set<Long> excludeIds)
    {
        return getTopPosts(count, year, month, null, excludeIds);
    }

    /*
        - most recent with gallery
        - most recent pinned
        - most recent
     */
    public List<Post> getTopPosts(final Integer LIMIT, Integer year, Integer month, Long categoryId, final Set<Long> EXCLUDE)
    {
        List<Post> posts = new ArrayList<>();

        boolean unpublished = asoManager.get(ViewState.class).isShowUnpublished();
        int count = LIMIT;

        Set<Long> excludeIds = new HashSet<>();
        excludeIds.addAll(EXCLUDE);


        //ONE most recent normal post with gallery
        List<Post> results = postDAO.getMostRecentPostsWithGallery(1, unpublished, year, month, categoryId, excludeIds);
        for (Post post : results)
        {
            posts.add(post);

            excludeIds.add(post.getId());

            count--;
        }

        if (count == 0)
        {
            return posts;
        }

        //all most recent promoted posts
        results = postDAO.getMostRecentPinnedPosts(count, unpublished, year, month, categoryId, excludeIds);
        for (Post post : results)
        {
            posts.add(post);

            excludeIds.add(post.getId());

            count--;
        }

        if (count == 0)
        {
            return posts;
        }

        //remaining most recent posts with gallery
        results = postDAO.getMostRecentPostsWithGallery(count, unpublished, year, month, categoryId, excludeIds);
        for (Post post : results)
        {
            posts.add(post);

            excludeIds.add(post.getId());

            count--;
        }

        if (count == 0)
        {
            return posts;
        }

        //all other remaining posts without galleries
        results = postDAO.getMostRecentPosts(count, unpublished, year, month, categoryId, excludeIds);
        for (Post post : results)
        {
            posts.add(post);

            excludeIds.add(post.getId());

            count--;
        }

        return posts;
    }


    public List<AbstractPost> getNextPosts(AbstractPost post)
    {
        boolean unpublished = asoManager.get(ViewState.class).isShowUnpublished();
        int count = PostDAO.MIN_RECENT;

        List<AbstractPost> posts = postDAO.getNextPosts(count, post, null, false, unpublished, null, null, null);
        return posts;
    }

    public List<AbstractPost> getPreviousPosts(AbstractPost post)
    {
        boolean unpublished = asoManager.get(ViewState.class).isShowUnpublished();
        int count = PostDAO.MIN_RECENT;

        List<AbstractPost> posts = postDAO.getPreviousPosts(count, post, null, false, unpublished, null, null, null);
        return posts;
    }

    public List<AbstractPost> getNextRelatedPosts(AbstractPost post)
    {
        return getRelatedPosts(true, post);
    }

    public List<AbstractPost> getPreviousRelatedPosts(AbstractPost post)
    {
        return getRelatedPosts(false, post);
    }


    private List<AbstractPost> getRelatedPosts(boolean next, AbstractPost post)
    {
        List<AbstractPost> posts = new ArrayList<AbstractPost>();

        boolean unpublished = asoManager.get(ViewState.class).isShowUnpublished();

        Set<Long> excludeIds = new HashSet<Long>();
        if (next)
        {
            for (AbstractPost p : getNextPosts(post))
            {
                excludeIds.add(p.getId());
            }
        }
        else
        {
            for (AbstractPost p : getPreviousPosts(post))
            {
                excludeIds.add(p.getId());
            }
        }

        List<SearchCriteria> criteria = createSearchCriteria(post);
        for (SearchCriteria c : criteria)
        {
            int count = PostDAO.TOP_RECENT - posts.size();
            if (count == 0)
            {
                return posts;
            }

            Class clazz = c.clazz;
            Long keyId = c.keywordId;
            Long catId = c.categoryId;

            boolean pinned = c.pinned;

            List<AbstractPost> ps;
            if (next)
            {
                ps = postDAO.getNextPosts(count, post, clazz, pinned, unpublished, catId, keyId, excludeIds);
            }
            else
            {
                ps = postDAO.getPreviousPosts(count, post, clazz, pinned, unpublished, catId, keyId, excludeIds);
            }

            for (AbstractPost p : ps)
            {
                posts.add(p);
                excludeIds.add(p.getId());
            }
        }
        return posts;
    }

    public AbstractPost getMostRecentPost()
    {
        return postDAO.getMostRecentPost(false, null);
    }

    public AbstractPost getFirstAvailablePost()
    {
        return postDAO.getFirstAvailablePost(false);
    }

    /*

     pinned, no class, keyword, no category

     class, keyword, category
     class, keyword, no category

     no class, keyword, category
     no class, keyword, no category
     no class, no keyword, category
     no class, no keyword, no category

     */

    private List<SearchCriteria> createSearchCriteria(AbstractPost post)
    {
        Class clazz = null;
        if (post.isClubPost() || post.isLessonPost())
        {
            clazz = post.getClass();
        }

        List<Keyword> keywords = post.getKeywords();

        List<PostCategory> categories = post.getAllPostCategories();
        Collections.reverse(categories);



        List<SearchCriteria> classCriteria = new ArrayList<SearchCriteria>();
        List<SearchCriteria> criteria = new ArrayList<SearchCriteria>();

        for (Keyword keyword : keywords)
        {
            classCriteria.add(new SearchCriteria(null, keyword.getId(), null, true));
        }

        for(PostCategory category : categories)
        {
            if (!IGNORE_CATEGORY_UUIDS_WHEN_FINDING_RELATED_POSTS.contains(category.getUuid()))
            {
                for (Keyword keyword : keywords)
                {
                    if (clazz != null)
                    {
                        classCriteria.add(new SearchCriteria(clazz, keyword.getId(), category.getId()));
                    }
                    criteria.add(new SearchCriteria(null, keyword.getId(), category.getId()));
                }
            }
        }

        for (Keyword keyword : keywords)
        {
            if (clazz != null)
            {
                classCriteria.add(new SearchCriteria(clazz, keyword.getId(), null));
            }
            criteria.add(new SearchCriteria(null, keyword.getId(), null));
        }

        for (PostCategory category : categories)
        {
            criteria.add(new SearchCriteria(null, null, category.getId()));
        }

        criteria.add(new SearchCriteria(null, null, null));

        classCriteria.addAll(criteria);
        return classCriteria;
    }

    private class SearchCriteria
    {
        public Class clazz;
        public Long keywordId;
        public Long categoryId;

        public boolean pinned;

        public SearchCriteria(Class clazz, Long keywordId, Long categoryId)
        {
            this.clazz = clazz;
            this.keywordId = keywordId;
            this.categoryId = categoryId;
            this.pinned = false;
        }

        public SearchCriteria(Class clazz, Long keywordId, Long categoryId, boolean pinned)
        {
            this.clazz = clazz;
            this.keywordId = keywordId;
            this.categoryId = categoryId;
            this.pinned = pinned;
        }
    }
}
