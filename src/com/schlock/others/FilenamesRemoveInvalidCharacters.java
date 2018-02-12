package com.schlock.others;

import com.schlock.website.services.blog.PhotoFileFilter;

import java.io.File;

public class FilenamesRemoveInvalidCharacters
{
    private static final String FOLDER_LOCATION = "/Users/JHendricks/Google Drive/Blog/www/photo/ffxv/";

    private static final String TM = "™";

    public void run()
    {
        File directory = new File(FOLDER_LOCATION);
        for (File file : directory.listFiles(new PhotoFileFilter()))
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
