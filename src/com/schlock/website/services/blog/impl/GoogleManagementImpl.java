package com.schlock.website.services.blog.impl;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.schlock.website.entities.blog.Image;
import com.schlock.website.entities.blog.ImageFolder;
import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.blog.GoogleManagement;
import com.schlock.website.services.database.blog.ImageFolderDAO;
import org.apache.commons.lang.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GoogleManagementImpl implements GoogleManagement
{
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    private static final String APPLICATION_NAME = "schlock-website"; // doesn't matter?
    private static final String TOKENS_DIRECTORY_PATH = "tokens"; // for caching? I dunno

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE_METADATA_READONLY);

    private final ImageFolderDAO folderDAO;
    private final DeploymentContext context;

    public GoogleManagementImpl(ImageFolderDAO folderDAO,
                                DeploymentContext context)
    {
        this.folderDAO = folderDAO;
        this.context = context;
    }


    /**
     * Creates an authorized Credential object.
     *
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException
    {
        InputStream in = new FileInputStream(context.googleCredentialsFilepath());
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                    .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                    .setAccessType("offline")
                    .build();

        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();

        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
        return credential;
    }

    public String getGoogleIdForImage(Image image)
    {
        List<String> path = new ArrayList<String>();
        path.add(image.getDirectory());
        if (StringUtils.isNotBlank(image.getGalleryName()))
        {
            path.add(image.getGalleryName());
        }
        path.add(image.getImageName());

        try
        {
            File photo = getFileFromPath(path);
            if (photo != null)
            {
                return photo.getId();
            }
            return null;
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public void test() throws Exception
    {
        File photo = getFileFromPath(Arrays.asList("photo", "ffxv", "FINAL_FANTASY_XV_20161230163328.jpg"));
        if(photo != null)
        {
            System.out.printf("%s (%s)\n", photo.getName(), photo.getId());
        }
        else
        {
            System.out.println("Cannot find photo: FINAL_FANTASY_XV_20161230163328.jpg");
        }
    }

    private void buildFolders()
    {

    }

    public File getFileFromPath(List<String> path) throws Exception
    {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive service =
                new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                    .setApplicationName(APPLICATION_NAME)
                    .build();


        // Print the names and IDs for up to 10 files.
//        FileList result = service.files().list()
//                                            .setPageSize(10)
//                                            .setFields("nextPageToken, files(id, name)")
//                                            .execute();

        //page is limited to 100 by default

        //query for folders = "mimeType='application/vnd.google-apps.folder'"

        ImageFolder rootFolder = folderDAO.getRoot();

        File photo = getFileFromPath(service, rootFolder, path);
        return photo;
    }

    private File getFileFromPath(Drive service, ImageFolder currentFolder, List<String> path) throws Exception
    {
        String folderId = currentFolder.getGoogleId();

        if (path.size() == 1)
        {
            //look for file
            String filename = path.get(0);
            return getFromGoogle(service, folderId, filename);
        }

        String subFolderName = path.get(0);
        path = path.subList(1, path.size());

        ImageFolder subFolder = folderDAO.getFolderByNameParentId(subFolderName, folderId);
        if (subFolder == null || StringUtils.isBlank(subFolder.getGoogleId()))
        {
            File folder = getFromGoogle(service, folderId, subFolderName);
            if (folder == null)
            {
                return null;
            }

            if (subFolder == null)
            {
                subFolder = new ImageFolder();
                subFolder.setFolderName(subFolderName);
                subFolder.setParent(currentFolder);
            }
            subFolder.setGoogleId(folder.getId());

            folderDAO.save(subFolder);
        }
        return getFileFromPath(service, subFolder, path);
    }

    private File getFromGoogle(Drive service, String folderId, String name) throws Exception
    {
        String queryText = String.format("parents = '%s'", folderId);

        FileList result = service.files().list()
                                            .setQ(queryText)
                                            .setFields("nextPageToken, files(id, name)")
                                            .execute();

        for(File file : result.getFiles())
        {
            if (StringUtils.equalsIgnoreCase(name, file.getName()))
            {
                return file;
            }
        }
        return null;
    }
}
