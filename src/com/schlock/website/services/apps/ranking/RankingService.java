package com.schlock.website.services.apps.ranking;

import java.util.List;

public interface RankingService
{
    public List<String> getCurrentPair();

    public void choose(String choice);

    public int getCurrentRound();

    public int getTotalRounds();

    public boolean isFinished();

    public List<String> getSortedList();
}
