package com.schlock.website.services.apps.notfibbage.impl;

import com.schlock.website.entities.apps.notfibbage.NotFibbagePlayer;
import com.schlock.website.entities.apps.notfibbage.NotFibbageQuestion;
import com.schlock.website.services.apps.notfibbage.NotFibbageManagement;
import com.schlock.website.services.database.apps.notfibbage.NotFibbageQuestionDAO;
import org.apache.commons.lang.StringUtils;

import java.util.*;

public class NotFibbageManagementImpl implements NotFibbageManagement
{
    private final NotFibbageQuestionDAO questionDAO;

    private Map<String, NotFibbagePlayer> players;

    private boolean started = false;

    private int currentNumber = 0;
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


    public int getRoundNumber()
    {
        return currentNumber;
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

    private boolean isResponseCorrect(String response)
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

    public List<String> getQuestionResponses()
    {
        List<String> responses = new ArrayList<String>();

        for (NotFibbagePlayer player : players.values())
        {
            String response = player.getCurrentResponse();
            if (!containsString(responses, response))
            {
                responses.add(response);
            }
        }

        orderStringList(responses);
        return responses;
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

    public void tabulatePoints()
    {




    }

    public List<String> getPlayersInOrderByScore()
    {
        List<NotFibbagePlayer> playerList = new ArrayList<NotFibbagePlayer>();
        playerList.addAll(players.values());

        Collections.sort(playerList, new Comparator<NotFibbagePlayer>()
        {
            public int compare(NotFibbagePlayer p1, NotFibbagePlayer p2)
            {
                if (p1.getScore() > p2.getScore())
                {
                    return 1;
                }
                if (p1.getScore() < p2.getScore())
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
        this.started = false;
        this.currentNumber = 0;
        this.currentQuestion = null;
    }

    public void resetScore()
    {
        for (NotFibbagePlayer player : players.values())
        {
            player.reset();
        }
        this.started = false;
        this.currentNumber = 0;
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
