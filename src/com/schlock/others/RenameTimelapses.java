package com.schlock.others;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.FilenameFilter;

public class RenameTimelapses
{
    private final String LOCATION = "/Users/JHendricks/Camera/_timelapse/_timelapse10/";
    private final String OUTPUT = LOCATION + "_output/";

    /*


    ffmpeg -r 24 -i frame%04d.jpg -b:v 15000k -s hd1080 anoutput.mp4
     */

    public void run() throws Exception
    {
        File dir = new File(LOCATION);
        File[] images = dir.listFiles(new FilenameFilter()
        {
            @Override
            public boolean accept(File file, String s)
            {
                return StringUtils.startsWith(s, "IMG");
            }
        });

        int i = 0;
        for (File file : images)
        {
            String increment = String.format("%04d", i);

            File newFile = new File(OUTPUT + "frame" + increment + ".jpg");
            FileUtils.copyFile(file, newFile);

            i++;
        }
    }


    public static void main(String[] args) throws Exception
    {
        new RenameTimelapses().run();
    }
}
