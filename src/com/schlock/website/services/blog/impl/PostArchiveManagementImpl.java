package com.schlock.website.services.blog.impl;

import com.schlock.website.entities.blog.ClubPost;
import com.schlock.website.entities.blog.Post;
import com.schlock.website.entities.blog.ViewState;
import com.schlock.website.services.SiteGenerationCache;
import com.schlock.website.services.blog.PostArchiveManagement;
import com.schlock.website.services.blog.PostManagement;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.services.ApplicationStateManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class PostArchiveManagementImpl implements PostArchiveManagement
{
    private static final String ITERATION_DELIM = "-";

    private static final Integer SECTION_POST_LIMIT = 20;

    private final ApplicationStateManager asoManager;
    private final Messages messages;

    private final PostManagement postManagement;
    private final SiteGenerationCache siteCache;

    private final PostDAO postDAO;

    public PostArchiveManagementImpl(ApplicationStateManager asoManager,
                                     Messages messages,
                                     PostManagement postManagement,
                                     SiteGenerationCache siteCache,
                                     PostDAO postDAO)
    {

        this.asoManager = asoManager;
        this.messages = messages;

        this.postManagement = postManagement;
        this.siteCache = siteCache;
        this.postDAO = postDAO;
    }

    public List<String> getYearlyMonthlyIterations(Integer year, Integer month)
    {
        List<String> results = siteCache.getCachedStringList(SiteGenerationCache.YEARLY_MONTHLY_ITERATIONS, year, month);
        if(results != null)
        {
            return results;
        }

        ViewState viewState = asoManager.get(ViewState.class);
        boolean unpublished = viewState.isShowUnpublished();

        results = new ArrayList<String>();

        if (year != null && month != null)
        {
            results.add(createIteration(year, month));
        }
        else if (year != null)
        {
            List<Integer> months = postDAO.getMonths(year, unpublished);
            for (Integer m : months)
            {
                results.add(createIteration(year, m));
            }
        }
        else
        {
            List<Integer> years = postDAO.getAllYears(unpublished);
            for(Integer y : years)
            {
                results.add(createIteration(y, null));
            }
        }

        siteCache.addToStringListCache(results, SiteGenerationCache.YEARLY_MONTHLY_ITERATIONS, year, month);

        return results;
    }

    public List<String> getYearlyMonthlyIterations(Long categoryId)
    {
        List<String> results = siteCache.getCachedStringList(SiteGenerationCache.YEARLY_MONTHLY_ITERATIONS, categoryId);
        if (results != null)
        {
            return results;
        }

        boolean unpublished = asoManager.get(ViewState.class).isShowUnpublished();

        results = new ArrayList<>();

        List<List<Integer>> dates = postDAO.getYearsMonthsByCategory(unpublished, categoryId);
        for (List<Integer> date : dates)
        {
            Integer year = date.get(0);
            Integer month = date.get(1);

            results.add(createIteration(year, month));
        }

        siteCache.addToStringListCache(results, SiteGenerationCache.YEARLY_MONTHLY_ITERATIONS, categoryId);

        return results;
    }

    public List<String> getYearlyMonthlyIterations(String keywordName)
    {
        List<String> results = siteCache.getCachedStringList(SiteGenerationCache.YEARLY_MONTHLY_ITERATIONS, keywordName);
        if (results != null)
        {
            return results;
        }

        boolean unpublished = asoManager.get(ViewState.class).isShowUnpublished();

        results = new ArrayList<>();

        List<List<Integer>> dates = postDAO.getYearsMonthsByKeyword(unpublished, keywordName);
        for(List<Integer> date : dates)
        {
            Integer year = date.get(0);
            Integer month = date.get(1);

            results.add(createIteration(year, month));
        }

        siteCache.addToStringListCache(results, SiteGenerationCache.YEARLY_MONTHLY_ITERATIONS, keywordName);

        return results;
    }

    public String getNextYearlyMontlyIteration(String iteration, Long categoryId)
    {
        List<String> iterations = getYearlyMonthlyIterations(categoryId);

        int index = iterations.indexOf(iteration) -1;
        if (index < 0)
        {
            return null;
        }
        return iterations.get(index);
    }

    public String getPreviousYearlyMonthlyInteration(String iteration, Long categoryId)
    {
        List<String> iterations = getYearlyMonthlyIterations(categoryId);

        int index = iterations.indexOf(iteration) +1;
        if (index == 0 || index == iterations.size())
        {
            return null;
        }
        return iterations.get(index);
    }



    public List<Post> getPosts(String iteration)
    {
        return getPosts(iteration, null, null);
    }

    public List<Post> getPosts(String iteration, Long categoryId)
    {
        return getPosts(iteration, categoryId, null);
    }

    public List<Post> getPosts(String iteration, String keywordName)
    {
        return getPosts(iteration, null, keywordName);
    }

    private List<Post> getPosts(String iteration, Long categoryId, String keywordName)
    {
        List<Post> results = siteCache.getCachedPosts(SiteGenerationCache.ARCHIVED_POSTS, iteration, categoryId, keywordName);
        if (results != null)
        {
            return results;
        }

        ViewState viewState = asoManager.get(ViewState.class);
        boolean unpublished = viewState.isShowUnpublished();

        Integer year = parseYear(iteration);
        Integer month = parseMonth(iteration);

        results = postDAO.getMostRecentPosts(null, unpublished, year, month, categoryId, keywordName);

        siteCache.addToPostCache(results, SiteGenerationCache.ARCHIVED_POSTS, iteration, categoryId, keywordName);

        return results;
    }

    public List<Post> getPreviewPosts(String iteration, Set<Long> excludeIds)
    {
        return getPreviewPosts(iteration, null, null, excludeIds);
    }

    public List<Post> getPreviewPosts(String iteration, Long categoryId, Set<Long> excludeIds)
    {
        return getPreviewPosts(iteration, categoryId, null, excludeIds);
    }

    public List<Post> getPreviewPosts(String iteration, String keywordName, Set<Long> excludeIds)
    {
        return getPreviewPosts(iteration, null, keywordName, excludeIds);
    }

    private List<Post> getPreviewPosts(String iteration, Long categoryId, String keywordName, Set<Long> excludeIds)
    {
        int count = getPosts(iteration, categoryId, keywordName).size();
        int LIMIT = (int) Math.floor(((double) count ) / ((double) 7));
        if (LIMIT < 1)
        {
            LIMIT = 1;
        }

        Integer year = parseYear(iteration);
        Integer month = parseMonth(iteration);

        List<Post> posts = postManagement.getTopPosts(LIMIT, year, month, categoryId, keywordName, excludeIds);
        return posts;
    }


    public List<Post> getPagedPosts(Integer postCount, Integer pageNumber)
    {
        List<Post> results = siteCache.getCachedPosts(SiteGenerationCache.PAGED_CACHED, postCount, pageNumber);
        if (results == null)
        {
            ViewState viewState = asoManager.get(ViewState.class);
            boolean unpublished = viewState.isShowUnpublished();

            results = postDAO.getMostRecentPosts(null, unpublished, null, null, Collections.EMPTY_SET);
            results = pagedPosts(results, postCount, pageNumber);

            siteCache.addToPostCache(results, SiteGenerationCache.PAGED_CACHED, postCount, pageNumber);
        }
        return results;
    }

    public List<Post> getPagedPosts(Integer postCount, Integer pageNumber, String iteration)
    {
        List<Post> results = siteCache.getCachedPosts(SiteGenerationCache.PAGED_CACHED, postCount, pageNumber, iteration);
        if (results == null)
        {
            ViewState viewState = asoManager.get(ViewState.class);
            boolean unpublished = viewState.isShowUnpublished();

            Integer year = parseYear(iteration);
            Integer month = parseMonth(iteration);

            Long categoryId = null;

            results = postDAO.getMostRecentPosts(null, unpublished, year, month, categoryId);
            results = pagedPosts(results, postCount, pageNumber);

            siteCache.addToPostCache(results, SiteGenerationCache.PAGED_CACHED, postCount, pageNumber, iteration);
        }
        return results;
    }

    public List<Post> getPagedPosts(Integer postCount, Integer pageNumber, Set<Long> categoryIds)
    {
        List<Post> results = siteCache.getCachedPosts(SiteGenerationCache.PAGED_CACHED, postCount, pageNumber, categoryIds);
        if (results == null)
        {
            ViewState viewState = asoManager.get(ViewState.class);
            boolean unpublished = viewState.isShowUnpublished();

            results = postDAO.getMostRecentPosts(null, unpublished, null, null, categoryIds);
            results = pagedPosts(results, postCount, pageNumber);

            siteCache.addToPostCache(results, SiteGenerationCache.PAGED_CACHED, postCount, pageNumber, categoryIds);
        }
        return results;
    }

    public List<Post> getPagedClubPosts(Integer postCount, Integer pageNumber)
    {
        List<Post> results = siteCache.getCachedPosts(SiteGenerationCache.PAGED_CACHED, postCount, pageNumber, "club");
        if (results == null)
        {
            List<ClubPost> posts = postDAO.getAllClubPosts(true);

            results = new ArrayList<>();
            results.addAll(posts);
            results = pagedPosts(results, postCount, pageNumber);

            siteCache.addToPostCache(results, SiteGenerationCache.PAGED_CACHED, postCount, pageNumber, "club");
        }
        return results;
    }

    private List<Post> pagedPosts(List<Post> results, Integer postCount, Integer pageNumber)
    {
        if (postCount == null)
        {
            return results;
        }

        if (pageNumber < 1)
        {
            return Collections.EMPTY_LIST;
        }

        int start = (pageNumber - 1) * postCount;
        int end = start + postCount;

        if (end > results.size())
        {
            end = results.size();
        }

        if (end < start)
        {
            return Collections.EMPTY_LIST;
        }

        return results.subList(start, end);
    }





    private String createIteration(Integer year, Integer month)
    {
        if (year != null)
        {
            String iteration = Integer.toString(year);
            if (month != null)
            {
                iteration += ITERATION_DELIM + month;
            }
            return iteration;
        }
        return null;
    }

    public String getParentIteration(Integer year, Integer month)
    {
        if (month != null)
        {
            return createIteration(year, null);
        }
        return createIteration(null, null);
    }

    public String getIterationUrlChain(String iterationCode)
    {
        Integer year = parseYear(iterationCode);
        Integer month = parseMonth(iterationCode);

        String urlChain = "/archive";
        if (year != null)
        {
            urlChain += "/" + year.toString();
            if (month != null)
            {
                urlChain += "/" + month.toString();
            }
        }
        return urlChain;
    }

    public String getIterationTitle(String iteration)
    {
        Integer year = parseYear(iteration);
        Integer month = parseMonth(iteration);

        return getIterationTitle(year, month);
    }

    public String getIterationTitle(Integer year, Integer month)
    {
        String title = "";
        if (year != null)
        {
            if (month != null)
            {
                String m = messages.get(Integer.toString(month));
                title += m + " ";
            }
            title += year;
        }
        return title;
    }

    public Integer parseYear(String iteration)
    {
        String[] parts = StringUtils.split(iteration, ITERATION_DELIM);
        if (parts != null && parts.length > 0)
        {
            String year = parts[0];
            return Integer.parseInt(year);
        }
        return null;
    }

    public Integer parseMonth(String iteration)
    {
        String[] parts = StringUtils.split(iteration, ITERATION_DELIM);
        if (parts != null && parts.length > 1)
        {
            String month = parts[1];
            return Integer.parseInt(month);
        }
        return null;
    }

    public boolean isIteration(String iteration)
    {
        try
        {
            String[] parts = StringUtils.split(iteration, ITERATION_DELIM);

            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);

            return year > 2000 && month <= 12 && month > 0;
        }
        catch(Exception e)
        {
        }
        return false;
    }
}
