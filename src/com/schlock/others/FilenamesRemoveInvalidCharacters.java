package com.schlock.others;

import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.FilenameFilter;

public class FilenamesRemoveInvalidCharacters
{
    private static final String FOLDER_LOCATION = "/Users/JHendricks/Google Drive/Blog/www/photo/ffxv/";

    private static final String TM = "™";

    public void run()
    {
        File directory = new File(FOLDER_LOCATION);

        File[] files = directory.listFiles(new FilenameFilter()
        {
            @Override
            public boolean accept(File dir, String name)
            {
                return StringUtils.endsWith(name, ".png");
            }
        });

        for (File file : files)
        {
            String name = file.getName();

            name = name.replace(TM, "");
            name = name.replace(" ", "_");

            File newfile = new File(FOLDER_LOCATION + name);
            if (!newfile.exists())
            {
                file.renameTo(newfile);
            }
        }
    }

    public static void main(String[] args) { new FilenamesRemoveInvalidCharacters().run(); }
}
