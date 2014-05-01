package com.schlock.website.services.applications.subtitles.impl;

import com.schlock.website.services.applications.subtitles.SubtitleFixerService;
import org.apache.commons.lang.StringUtils;

public class SubtitleFixerServiceImpl implements SubtitleFixerService
{
    //time lines in the form of xx:xx:xx,xxx --> xx:xx:xx,xxx
    private static final String TIME_REGEX = "[0-9][0-9]:[0-9][0-9]:[0-9][0-9],[0-9][0-9][0-9]";
    private static final String TIME_LINE_REGEX = TIME_REGEX + " --> " + TIME_REGEX;

    private static final String LINE_BREAK = "\n";

    public String offsetSubtitles(final String input, int offsetInSeconds)
    {
        StringBuilder output = new StringBuilder();

        int lineNumber = 0;

        String[] lines = StringUtils.splitPreserveAllTokens(input, LINE_BREAK);
        for (String line : lines)
        {
            lineNumber++;
            if (isTimeLine(line))
            {
                String newLine = offsetLine(line, offsetInSeconds, lineNumber);
                output.append(newLine);
            }
            else
            {
                output.append(line);
            }
            output.append(LINE_BREAK);
        }
        return output.toString();
    }


    private boolean isTimeLine(String line)
    {
        return line.matches(TIME_LINE_REGEX);
    }

    private String offsetLine(String line, int offset, int lineNumber)
    {
        String[] times = line.split(" ");

        if(times.length != 3)
        {
            throw new RuntimeException("Time code is malformed on " + lineNumber + ". [" + line + "]");
        }

        try
        {
            String time1 = offsetTime(times[0], offset);
            String time2 = offsetTime(times[2], offset);

            return time1 + " " + times[1] + " " + time2;
        }
        catch(Exception e)
        {
            throw new RuntimeException("Time code is malformed on " + lineNumber + ". [" + line + "]");
        }
    }

    private String offsetTime(String timeCode, int offset) throws NumberFormatException
    {
        String[] time = timeCode.split(":");

        int hr = Integer.parseInt(time[0]);
        int min = Integer.parseInt(time[1]);

        String[] seconds = time[2].split(",");

        int sec = Integer.parseInt(seconds[0]);
        String millis = seconds[1];

        sec += offset;
        while(sec >= 60)
        {
            sec -= 60;
            min += 1;
        }
        while(min >= 60)
        {
            min -= 60;
            hr += 1;
        }

        String hour = Integer.toString(hr);
        if(hour.length() == 1)
        {
            hour = "0" + hour;
        }
        String minute = Integer.toString(min);
        if(minute.length() == 1)
        {
            minute = "0" + minute;
        }
        String second = Integer.toString(sec);
        if(second.length() == 1)
        {
            second = "0" + second;
        }

        return hour + ":" + minute + ":" + second + "," + millis;
    }
}
