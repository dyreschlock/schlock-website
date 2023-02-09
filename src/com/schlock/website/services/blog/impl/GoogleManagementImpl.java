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
import com.schlock.website.services.database.blog.ImageDAO;
import com.schlock.website.services.database.blog.ImageFolderDAO;
import org.apache.commons.lang.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class GoogleManagementImpl implements GoogleManagement
{
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    private static final String APPLICATION_NAME = "schlock-website"; // doesn't matter?
    private static final String TOKENS_DIRECTORY_PATH = "tokens"; // for caching, probably

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE_METADATA_READONLY);

    private final ImageDAO imageDAO;
    private final ImageFolderDAO folderDAO;
    private final DeploymentContext context;

    public GoogleManagementImpl(ImageFolderDAO folderDAO,
                                ImageDAO imageDAO,
                                DeploymentContext context)
    {
        this.folderDAO = folderDAO;
        this.imageDAO = imageDAO;
        this.context = context;
    }

    /**
     * single queries are limited to 100 results by default
     *
     * Query Terms and Operators
     * https://developers.google.com/drive/api/guides/ref-search-terms
     *
     * query for folders = "mimeType='application/vnd.google-apps.folder'"
     *
     */

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

    private Drive cachedService;

    private Drive service() throws Exception
    {
        if (cachedService == null)
        {
            // Build a new authorized API client service.
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                                                                .setApplicationName(APPLICATION_NAME)
                                                                .build();

            cachedService = service;
        }
        return cachedService;
    }


//        File photo = getFileFromPath(Arrays.asList("photo", "ffxv", "FINAL_FANTASY_XV_20161230163328.jpg"));
//        if(photo != null)
//        {
//            System.out.printf("%s (%s)\n", photo.getName(), photo.getId());
//        }
//        else
//        {
//            System.out.println("Cannot find photo: FINAL_FANTASY_XV_20161230163328.jpg");
//        }

    public void buildFolders() throws Exception
    {
        List<Image> allImages = imageDAO.getAll();

        Map<String, List<String>> folderStructure = new HashMap<String, List<String>>();

        for(Image image : allImages)
        {
            String directory = image.getDirectory();
            String galleryName = image.getGalleryName();

            if (!folderStructure.containsKey(directory))
            {
                List<String> subFolders = new ArrayList<String>();
                if (StringUtils.isNotBlank(galleryName) && !subFolders.contains(galleryName))
                {
                    subFolders.add(galleryName);
                }

                folderStructure.put(directory, subFolders);
            }
            else if(StringUtils.isNotBlank(galleryName) && !folderStructure.get(directory).contains(galleryName))
            {
                folderStructure.get(directory).add(galleryName);
            }
        }

        ImageFolder root = folderDAO.getRoot();

        for(String dir : folderStructure.keySet())
        {
            ImageFolder mainFolder = folderDAO.getFolderByNameParentId(dir, root.getGoogleId());
            if (mainFolder == null)
            {
                mainFolder = new ImageFolder();
                mainFolder.setFolderName(dir);
                mainFolder.setParent(root);
            }

            if (StringUtils.isBlank(mainFolder.getGoogleId()))
            {
                File mainFolderGoogle = getFromGoogle(root.getGoogleId(), dir);
                mainFolder.setGoogleId(mainFolderGoogle.getId());

                folderDAO.save(mainFolder);
            }

            List<String> subFolders = folderStructure.get(dir);
            for(String subName : subFolders)
            {
                ImageFolder subFolder = folderDAO.getFolderByNameParentId(subName, mainFolder.getGoogleId());
                if (subFolder == null)
                {
                    subFolder = new ImageFolder();
                    subFolder.setFolderName(subName);
                    subFolder.setParent(mainFolder);
                }

                if (StringUtils.isBlank(subFolder.getGoogleId()))
                {
                    File subFolderGoogle = getFromGoogle(mainFolder.getGoogleId(), subName);
                    subFolder.setGoogleId(subFolderGoogle.getId());

                    folderDAO.save(subFolder);
                }
            }
        }
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
            ImageFolder rootFolder = folderDAO.getRoot();

            File photo = getFileFromPath(rootFolder, path);
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

    private File getFileFromPath(ImageFolder currentFolder, List<String> path) throws Exception
    {
        String folderId = currentFolder.getGoogleId();

        if (path.size() == 1)
        {
            //look for file
            String filename = path.get(0);
            return getFromGoogle(folderId, filename);
        }

        String subFolderName = path.get(0);
        path = path.subList(1, path.size());

        ImageFolder subFolder = folderDAO.getFolderByNameParentId(subFolderName, folderId);
        if (subFolder == null || StringUtils.isBlank(subFolder.getGoogleId()))
        {
            File folder = getFromGoogle(folderId, subFolderName);
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
        return getFileFromPath(subFolder, path);
    }

    private File getFromGoogle(String folderId, String name) throws Exception
    {
        String query1 = String.format("parents = '%s'", folderId); //from parent
        String query2 = String.format("name = '%s'", name);        //with name

        FileList result = service().files().list()
                                            .setQ(query1).setQ(query2)
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
