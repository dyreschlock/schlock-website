package com.schlock.website.services.apps.notfibbage.impl;

import com.schlock.website.entities.apps.notfibbage.NotFibbagePlayer;
import com.schlock.website.entities.apps.notfibbage.NotFibbageQuestion;
import com.schlock.website.services.apps.notfibbage.NotFibbageManagement;
import com.schlock.website.services.database.apps.notfibbage.NotFibbageQuestionDAO;
import org.apache.commons.lang.StringUtils;

import java.util.*;

public class NotFibbageManagementImpl implements NotFibbageManagement
{
    private static final long QUESTION_CATEGORY = 1;

    private static final int FULL_POINTS_DEFAULT = 10;


    private final NotFibbageQuestionDAO questionDAO;

    private Map<String, NotFibbagePlayer> players;

    private Long currentQuestion;


    public NotFibbageManagementImpl(NotFibbageQuestionDAO questionDAO)
    {
        this.questionDAO = questionDAO;

        resetGame();
    }


    public void registerPlayer(String playerName)
    {
        NotFibbagePlayer player = players.get(playerName);
        if (player == null)
        {
            player = new NotFibbagePlayer(playerName);
            players.put(playerName, player);
        }
    }

    public boolean isRegisteredPlayer(String playerName)
    {
        NotFibbagePlayer player = players.get(playerName);
        return player != null;
    }

    public List<String> getRegisteredPlayers()
    {
        List<String> playerList = new ArrayList<String>();
        playerList.addAll(players.keySet());

        return playerList;
    }

    public boolean hasNextQuestion(int roundNumber)
    {
        List<NotFibbageQuestion> questions = questionDAO.getByCategory(QUESTION_CATEGORY);
        return roundNumber <= questions.size();
    }

    public boolean setNewQuestion(int roundNumber)
    {
        List<NotFibbageQuestion> questions = questionDAO.getByCategory(QUESTION_CATEGORY);

        if (roundNumber <= questions.size())
        {
            int index = roundNumber -1;
            NotFibbageQuestion question = questions.get(index);
            this.currentQuestion = question.getId();

            return true;
        }
        return false;
    }

    private NotFibbageQuestion getCurrentQuestion()
    {
        if (currentQuestion != null)
        {
            return questionDAO.getById(currentQuestion);
        }
        return null;
    }

    public String getQuestionText()
    {
        NotFibbageQuestion question = getCurrentQuestion();
        if (question != null)
        {
            return question.getText();
        }
        return "";
    }

    public boolean registerResponse(String playerName, String response)
    {
        if (isResponseCorrect(response))
        {
            return false;
        }

        NotFibbagePlayer player = players.get(playerName);
        if (player != null)
        {
            player.setCurrentResponse(response);
        }
        return true;
    }

    public boolean isResponseCorrect(String response)
    {
        NotFibbageQuestion question = getCurrentQuestion();
        return StringUtils.equalsIgnoreCase(question.getAnswer(), response);
    }

    public List<String> getPlayersResponded()
    {
        List<String> playersResponded = new ArrayList<String>();

        for (String playerName : players.keySet())
        {
            NotFibbagePlayer player = players.get(playerName);
            if (StringUtils.isNotBlank(player.getCurrentResponse()))
            {
                playersResponded.add(playerName);
            }
        }

        orderStringList(playersResponded);
        return playersResponded;
    }

    public boolean hasPlayerResponded(String playerName)
    {
        NotFibbagePlayer player = players.get(playerName);
        return player != null && StringUtils.isNotBlank(player.getCurrentResponse());
    }

    public List<String> getQuestionResponses(String currentPlayer)
    {
        NotFibbagePlayer current = players.get(currentPlayer);
        String currentPlayerResponse = current.getCurrentResponse();

        if (StringUtils.isBlank(currentPlayerResponse))
        {
            return getQuestionResponses();
        }

        List<String> responses = new ArrayList<String>();
        for (String response : getQuestionResponses())
        {
            if (!StringUtils.equalsIgnoreCase(currentPlayerResponse, response))
            {
                responses.add(response);
            }
        }
        return responses;
    }

    public List<String> getQuestionResponses()
    {
        List<String> responses = new ArrayList<String>();
        for (NotFibbagePlayer player : players.values())
        {
            String response = player.getCurrentResponse();
            if (StringUtils.isNotBlank(response) &&
                    !containsString(responses, response))
            {
                responses.add(response);
            }
        }


        String correct = getCurrentQuestion().getAnswer();
        responses.add(correct);


        int max = getMaximumNumbersOfResponses();

        List<String> fakeAnswers = new ArrayList<String>();
        fakeAnswers.addAll(getCurrentQuestion().getFakeAnswers());

        for (int i = 0; i < fakeAnswers.size() && responses.size() < max; i++)
        {
            String fake = fakeAnswers.get(i);
            if (!containsString(responses, fake))
            {
                responses.add(fake);
            }
        }

        orderStringList(responses);
        return responses;
    }

    private int getMaximumNumbersOfResponses()
    {
        int count = getRegisteredPlayers().size();
        count++;

        while (count % 3 != 0)
        {
            count++;
        }
        return count;
    }

    public boolean registerAnswer(String playerName, String answer)
    {
        NotFibbagePlayer player = players.get(playerName);
        if (player != null)
        {
            player.setCurrentAnswer(answer);
        }
        return true;
    }

    public List<String> getPlayersAnswered()
    {
        List<String> playersAnswered = new ArrayList<String>();

        for (String playerName : players.keySet())
        {
            NotFibbagePlayer player = players.get(playerName);
            if (StringUtils.isNotBlank(player.getCurrentAnswer()))
            {
                playersAnswered.add(playerName);
            }
        }

        orderStringList(playersAnswered);
        return playersAnswered;
    }

    public boolean hasPlayerAnswered(String playerName)
    {
        NotFibbagePlayer player = players.get(playerName);
        return player != null && StringUtils.isNotBlank(player.getCurrentAnswer());
    }

    public List<String> getAnsweredResponses()
    {
        String correctAnswer = getCurrentQuestion().getAnswer();

        List<String> answers = new ArrayList<String>();

        for (NotFibbagePlayer player : players.values())
        {
            String answer = player.getCurrentAnswer();
            if (!containsString(answers, answer) &&
                    !StringUtils.equalsIgnoreCase(correctAnswer, answer))
            {
                answers.add(answer);
            }
        }

        orderStringList(answers);
        answers.add(correctAnswer);

        return answers;
    }

    public List<String> getPlayersByAnswer(String response)
    {
        List<String> playerNames = new ArrayList<String>();
        for (NotFibbagePlayer player : players.values())
        {
            if (StringUtils.equalsIgnoreCase(response, player.getCurrentAnswer()))
            {
                playerNames.add(player.getName());
            }
        }

        orderStringList(playerNames);
        return playerNames;
    }

    public List<String> getPlayersByResponse(String response)
    {
        List<String> playerNames = new ArrayList<String>();
        for (NotFibbagePlayer player : players.values())
        {
            if (StringUtils.equalsIgnoreCase(response, player.getCurrentResponse()))
            {
                playerNames.add(player.getName());
            }
        }

        orderStringList(playerNames);
        return playerNames;
    }

    public void tabulatePointsAndClearResponses()
    {
        tabulatePoints();
        clearResponses();
    }

    private void tabulatePoints()
    {
        // correct answers - full points
        // player answer - owner get full points for each player answered

        // fake answer - minus half points
        // own answer - minus half points

        final int FULL_POINTS = getCurrentPointValue();
        final int HALF_POINTS = FULL_POINTS / 2;

        for (String answer : getAnsweredResponses())
        {
            if (isResponseCorrect(answer))
            {
                for (String playerName : getPlayersByAnswer(answer))
                {
                    NotFibbagePlayer player = players.get(playerName);
                    player.incrementScore(FULL_POINTS);
                }
            }
            else
            {
                List<String> owners = getPlayersByResponse(answer);
                if (owners.size() == 0)
                {
                    for (String playerName : getPlayersByAnswer(answer))
                    {
                        NotFibbagePlayer player = players.get(playerName);
                        player.incrementScore(0 - HALF_POINTS);
                    }
                }
                else
                {
                    int countAnswered = getPlayersByAnswer(answer).size();
                    int points = FULL_POINTS * countAnswered;

                    for (String playerName : owners)
                    {
                        NotFibbagePlayer owner = players.get(playerName);
                        String ownerAnswer = owner.getCurrentAnswer();

                        if (!StringUtils.equalsIgnoreCase(answer, ownerAnswer))
                        {
                            owner.incrementScore(points);
                        }
                        else
                        {
                            owner.incrementScore(0 - HALF_POINTS);
                        }
                    }
                }
            }
        }
    }

    private int getCurrentPointValue()
    {
        return FULL_POINTS_DEFAULT;
    }

    private void clearResponses()
    {
        for (NotFibbagePlayer player : players.values())
        {
            player.setCurrentResponse(null);
            player.setCurrentAnswer(null);
        }
    }

    public List<String> getPlayersInOrderByScore()
    {
        List<NotFibbagePlayer> playerList = new ArrayList<NotFibbagePlayer>();
        playerList.addAll(players.values());

        Collections.sort(playerList, new Comparator<NotFibbagePlayer>()
        {
            public int compare(NotFibbagePlayer p1, NotFibbagePlayer p2)
            {
                if (p1.getScore() < p2.getScore())
                {
                    return 1;
                }
                if (p1.getScore() > p2.getScore())
                {
                    return -1;
                }
                return p1.getName().compareToIgnoreCase(p2.getName());
            }
        });

        List<String> names = new ArrayList<String>();
        for (NotFibbagePlayer player : playerList)
        {
            names.add(player.getName());
        }
        return names;
    }

    public Integer getScore(String playerName)
    {
        NotFibbagePlayer player = players.get(playerName);
        return player.getScore();
    }


    public void resetGame()
    {
        this.players = new HashMap<String, NotFibbagePlayer>();
        this.currentQuestion = null;
    }

    public void resetScore()
    {
        for (NotFibbagePlayer player : players.values())
        {
            player.reset();
        }
        this.currentQuestion = null;
    }



    private boolean containsString(List<String> list, String string)
    {
        for (String listString : list)
        {
            if (StringUtils.equalsIgnoreCase(listString, string))
            {
                return true;
            }
        }
        return false;
    }

    private void orderStringList(List<String> list)
    {
        Collections.sort(list, new Comparator<String>()
        {
            public int compare(String s, String s2)
            {
                return s.compareToIgnoreCase(s2);
            }
        });
    }
}
