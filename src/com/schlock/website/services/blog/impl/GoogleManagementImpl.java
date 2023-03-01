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


    public void generateIdsForFoldersImages() throws Exception
    {
        buildFolders();
        updateImages();
    }


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
            ImageFolder mainFolder = getCreateFolder(dir, root);

            List<String> subFolders = folderStructure.get(dir);
            for(String subName : subFolders)
            {
                getCreateFolder(subName, mainFolder);
            }
        }
    }

    private ImageFolder getCreateFolder(String name, ImageFolder parent) throws Exception
    {
        ImageFolder folder = folderDAO.getFolderByNameParentGoogleId(name, parent.getGoogleId());
        if (folder == null)
        {
            folder = new ImageFolder();
            folder.setFolderName(name);
            folder.setParent(parent);
        }

        if (StringUtils.isBlank(folder.getGoogleId()))
        {
            File subFolderGoogle = getFromGoogle(parent.getGoogleId(), name);
            folder.setGoogleId(subFolderGoogle.getId());

            folderDAO.save(folder);
        }
        return folder;
    }




    private void updateImages() throws Exception
    {
        List<Image> allImages = imageDAO.getAllWithoutGooleId();

        Map<String, Map<String, Map<String, Image>>> fileStructure = new HashMap<String, Map<String, Map<String, Image>>>();
        for(Image image : allImages)
        {
            String directory = image.getDirectory();
            String galleryName = image.getGalleryName();

            if (StringUtils.isNotBlank(galleryName))
            {
                Map<String, Map<String, Image>> subFolderStructure = fileStructure.get(directory);
                if (subFolderStructure == null)
                {
                    subFolderStructure = new HashMap<String, Map<String, Image>>();

                    fileStructure.put(directory, subFolderStructure);
                }

                Map<String, Image> images = subFolderStructure.get(galleryName);
                if (images == null)
                {
                    images = new HashMap<String, Image>();

                    subFolderStructure.put(galleryName, images);
                }
                images.put(image.getImageName(), image);
            }
        }

        for(Map<String, Map<String, Image>> gallery : fileStructure.values())
        {
            for(String galleryName : gallery.keySet())
            {
                Map<String, Image> imageSet = gallery.get(galleryName);

                ImageFolder folder = folderDAO.getByName(galleryName);

                String pageToken = null;
                do
                {
                    String query1 = String.format("parents = '%s'", folder.getGoogleId()); //from parent

                    FileList result = service().files().list()
                                                        .setQ(query1)
                                                        .setFields("nextPageToken, files(id, name)")
                                                        .setPageToken(pageToken)
                                                        .execute();

                    for (File file : result.getFiles())
                    {
                        String fileName = file.getName();
                        Image image = imageSet.get(fileName);
                        if (image != null)
                        {
                            image.setGoogleId(file.getId());
                            imageDAO.save(image);

                            String message = String.format("Updated Google Id on Image: %s (%s)", file.getName(), file.getId());
                            System.out.println(message);
                        }
                    }

                    pageToken = result.getNextPageToken();
                }
                while (pageToken != null);

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
        }
        catch (Exception e)
        {
            e.printStackTrace();
//            throw new RuntimeException(e);
        }
        return null;
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

        ImageFolder subFolder = folderDAO.getFolderByNameParentGoogleId(subFolderName, folderId);
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
