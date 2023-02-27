package com.schlock.website.services.apps.subtitles.impl;

import com.schlock.website.services.apps.subtitles.SubtitleFixerService;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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
        while(sec < 0)
        {
            sec += 60;
            min -= 1;
        }
        while(min >= 60)
        {
            min -= 60;
            hr += 1;
        }
        while(min < 0)
        {
            min += 60;
            hr -= 1;
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

    private static final String OLD_SUBTITLES_FILEPATH = "old_subtitles.srt";
    private static final String NEW_SUBTITLES_FILEPATH = "new_subtitles.srt";

    private static double INITIAL_OFFSET = -1;        // old_time - new_time (earliest instance)
    private static double RATIO_OFFSET = 1;  // old_time / (new_time - INITIAL_OFFSET) (latest instance)

    public void process() throws Exception
    {
        List<String> originalSubtitles = readOriginalSubtitles();

        List<String> alteredSubtitles = offsetSubtitles(originalSubtitles);

        writeLinesToFiles(alteredSubtitles);
    }

    private List<String> offsetSubtitles(List<String> baseSubtitles)
    {
        List<String> newSubtitles = new ArrayList<String>();

        for(String line : baseSubtitles)
        {
            if (isTimeLine(line))
            {
                String alteredLine = alterTimeLine(line);

                newSubtitles.add(alteredLine);
            }
            else
            {
                newSubtitles.add(line);
            }
        }
        return newSubtitles;
    }

    private String alterTimeLine(String line)
    {
        String[] times = line.split(" ");

        if (times.length != 3)
        {
            throw new RuntimeException("Time code is malformed. [" + line + "]");
        }

        try
        {
            String time1 = alterTime(times[0]);
            String time2 = alterTime(times[2]);

            return time1 + " " + times[1] + " " + time2;
        }
        catch(Exception e)
        {
            throw new RuntimeException("Time code is malformed on. [" + line + "]");
        }
    }

    private String alterTime(String timeCode)
    {
        Double totalSeconds = convertTimeCodeToSeconds(timeCode);

        Double alteredSeconds = (totalSeconds - INITIAL_OFFSET) / RATIO_OFFSET;

        int offset = (int) Math.round(alteredSeconds - totalSeconds);

        return offsetTime(timeCode, offset);
    }

    private double convertTimeCodeToSeconds(String timeCode)
    {
        String[] time = timeCode.split(":");

        Integer hr = Integer.parseInt(time[0]);
        Integer min = Integer.parseInt(time[1]);

        String[] seconds = time[2].split(",");

        Integer sec = Integer.parseInt(seconds[0]);

        double totalSeconds = (hr.doubleValue() * 60.0 * 60.0) + (min.doubleValue() * 60.0) + sec.doubleValue();
        return totalSeconds;
    }

    private List<String> readOriginalSubtitles() throws Exception
    {
        List<String> lines = new ArrayList<String>();

        BufferedReader reader = new BufferedReader(new FileReader(OLD_SUBTITLES_FILEPATH));

        String line = reader.readLine();
        while(line != null)
        {
            lines.add(line);

            line = reader.readLine();
        }
        reader.close();

        return lines;
    }

    private void writeLinesToFiles(List<String> lines) throws Exception
    {
        File file = new File(NEW_SUBTITLES_FILEPATH);
        if (!file.exists())
        {
            file.createNewFile();
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        for(String line : lines)
        {
            writer.write(line);
            writer.newLine();
        }
        writer.close();
    }

    public static void main(String[] args) throws Exception
    {
        new SubtitleFixerServiceImpl().process();
    }
}
