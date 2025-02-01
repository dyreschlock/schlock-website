package com.schlock.website.services.blog.impl;

import com.google.common.io.Files;
import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.Image;
import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.SiteGenerationCache;
import com.schlock.website.services.blog.ImageManagement;
import com.schlock.website.services.blog.PostManagement;
import com.schlock.website.services.database.blog.ImageDAO;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.commons.lang.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.List;
import java.util.*;

public class ImageManagementImpl implements ImageManagement
{

    private final DeploymentContext deploymentContext;
    private final SiteGenerationCache siteCache;

    private final PostManagement postManagement;

    private final PostDAO postDAO;
    private final ImageDAO imageDAO;

    public ImageManagementImpl(DeploymentContext deploymentContext,
                               SiteGenerationCache siteCache,
                               PostManagement postManagement,
                               PostDAO postDAO,
                               ImageDAO imageDAO)
    {
        this.deploymentContext = deploymentContext;
        this.siteCache = siteCache;

        this.postManagement = postManagement;

        this.postDAO = postDAO;
        this.imageDAO = imageDAO;
    }


    public List<Image> getGalleryImages(AbstractPost post)
    {
        String galleryName = post.getGalleryName();
        if (StringUtils.isBlank(galleryName))
        {
            return Collections.EMPTY_LIST;
        }

        List<Image> galleryImages = new ArrayList<Image>();

        Map<String, Image> images = generateImagesByGallery(galleryName);
        for(String filename : images.keySet())
        {
            Image image = images.get(filename);
            if (image.isThumbnail())
            {
                galleryImages.add(image);
            }
            else
            {
                //if doesn't have thumbnail -> yes
                int index = filename.lastIndexOf(".");

                String thumbnailName = filename.substring(0, index) + "_t" + filename.substring(index);
                if(!images.containsKey(thumbnailName))
                {
                    galleryImages.add(image);
                }
            }
        }

        Collections.sort(galleryImages, new Comparator<Image>()
        {
            public int compare(Image o1, Image o2)
            {
                return o1.getImageName().compareToIgnoreCase(o2.getImageName());
            }
        });

        return galleryImages;
    }

    private Integer getImageIndexInGallery(AbstractPost post, String imageLink)
    {
        if (post == null || !post.isHasGallery())
        {
            return null;
        }

        String[] linkParts = extractDirectoryGalleryFilenameFromLink(imageLink);
        if (linkParts == null)
        {
            return null;
        }

        String galleryName = linkParts[1];
        String imageName = linkParts[2];

        if (post.getGalleryName().equals(galleryName))
        {
            List<Image> images = getGalleryImages(post);
            for(Integer i = 0; i < images.size(); i++)
            {
                Image image = images.get(i);
                if (image.isSameImage(imageName))
                {
                    return i;
                }
            }
        }
        return null;
    }

    public void generateImageObjects()
    {
        Set<String> galleries = postDAO.getAllGalleryNames();
        for(String gallery : galleries)
        {
            generateImagesByGallery(gallery);
        }
    }

    private Map<String, Image> generateImagesByGallery(String galleryName)
    {
        String directory = DeploymentContext.PHOTO_DIR.substring(0, DeploymentContext.PHOTO_DIR.length() - 1);

        File gallery = new File(deploymentContext.photoLocation() + galleryName);
        if (gallery.exists())
        {
            Map<String, Image> cache = getImagesByGallery(galleryName);

            File[] dirImgs = gallery.listFiles(new FilenameFilter()
            {
                public boolean accept(File dir, String name)
                {
                    return StringUtils.endsWith(name, ".jpg") ||
                            StringUtils.endsWith(name, ".png") ||
                            StringUtils.endsWith(name, ".gif");
                }
            });

            List<File> directoryImages = Arrays.asList(dirImgs);
            Collections.sort(directoryImages, new Comparator<File>()
            {
                public int compare(File o1, File o2)
                {
                    return o1.getName().compareTo(o2.getName());
                }
            });

            for (File file : directoryImages)
            {
                String imageName = file.getName();
                Image image = cache.get(imageName);
                if (image == null)
                {
                    image = imageDAO.getByDirectoryGalleryName(directory, galleryName, imageName);
                    if (image == null)
                    {
                        String possibleParentName = imageName.replace("_t.", ".");
                        Image parent = cache.get(possibleParentName);

                        image = createImage(directory, galleryName, imageName, parent);
                    }

                    cache.put(image.getImageName(), image);
                    if (image.getParent() != null)
                    {
                        Image parent = image.getParent();
                        cache.put(parent.getImageName(), parent);
                    }
                }
            }
            return cache;
        }
        return Collections.EMPTY_MAP;
    }

    private Image createImage(String directory, String galleryName, String imageName)
    {
        return createImage(directory, galleryName, imageName, null);
    }

    private Image createImage(String directory, String galleryName, String imageName, Image parentImage)
    {
        Image image = new Image();
        image.setDirectory(directory);
        image.setGalleryName(galleryName);
        image.setImageName(imageName);

        Image parent = parentImage;
        if (parent == null)
        {
            parent = getCreateParentIfExists(image);
        }
        image.setParent(parent);

        imageDAO.save(image);
        return image;
    }

    private Image getCreateParentIfExists(Image image)
    {
        String imageLink = image.getImageLink().substring(1);
        int index = imageLink.lastIndexOf(".");

        String parentLink = imageLink.substring(0, index - 2) + imageLink.substring(index);
        if (checkIfImageExists(parentLink))
        {
            String imageName = image.getImageName();
            index = imageName.lastIndexOf(".");

            String parentName = imageName.substring(0, index -2) + imageName.substring(index);
            String galleryName = image.getGalleryName();
            String directory = image.getDirectory();

            Image parent = imageDAO.getByDirectoryGalleryName(directory, galleryName, imageName);
            if (parent == null)
            {
                parent = new Image();
                parent.setDirectory(directory);
                parent.setGalleryName(galleryName);
                parent.setImageName(parentName);
            }
            return parent;
        }
        return null;
    }

    private Map<String, Image> getImagesByGallery(String galleryName)
    {
        List<Image> allImages = imageDAO.getByGallery(galleryName);

        Map<String, Image> cache = new HashMap<String, Image>();
        for(Image image : allImages)
        {
            cache.put(image.getImageName(), image);
        }
        return cache;
    }


    private static final String IMG_TAG = "<img ";
    private static final String SRC_PARAM = "src=\"";

    private static final String A_TAG = "<a ";
    private static final String HREF_PARAM = "href=\"";

    private static final String ALT_TEXT = "alt=\"\"";

    public String updateImagesInHTML(AbstractPost post, String h, boolean useGalleryLink)
    {
        String html = h;

        html = html.replaceAll(ALT_TEXT, "");
        html = updateImagesInHTML(IMG_TAG, SRC_PARAM, html, post, useGalleryLink);
        html = updateImagesInHTML(A_TAG, HREF_PARAM, html, post, useGalleryLink);

        return html;
    }

    private String updateImagesInHTML(final String TAG, final String PARAM, String h, AbstractPost post, boolean useGalleryLink)
    {
        final String QUOTE = "\"";

        StringBuilder finishHTML = new StringBuilder();
        String remainHTML = h;

        while(StringUtils.isNotBlank(remainHTML))
        {
            int index = remainHTML.indexOf(TAG);
            if (index == -1)
            {
                finishHTML.append(remainHTML);
                remainHTML = "";
            }
            else
            {
                index = remainHTML.indexOf(PARAM, index + TAG.length());

                finishHTML.append(remainHTML.substring(0, index));
                remainHTML = remainHTML.substring(index);

                index = PARAM.length();

                String linkReference = remainHTML.substring(index, remainHTML.indexOf(QUOTE, index));

                index = remainHTML.indexOf(linkReference) + linkReference.length();
                remainHTML = remainHTML.substring(index);

                String updatedLink;
                if (isImage(linkReference))
                {
                    updatedLink = updateImageLink(linkReference);

                    Integer imageIndex = getImageIndexInGallery(post, linkReference);
                    if (HREF_PARAM.equals(PARAM) && imageIndex != null && useGalleryLink)
                    {
                        // onclick="galleryClicked(1)"
                        String onClick = " onclick=\"galleryClicked(" + imageIndex + ")";
                        finishHTML.append(onClick);
                    }
                    else
                    {
                        finishHTML.append(PARAM + updatedLink);
                    }
                }
                else
                {
                    updatedLink = postManagement.updateLinkToModernReference(linkReference);
                    finishHTML.append(PARAM + updatedLink);
                }

                if (IMG_TAG.equals(TAG))
                {
                    String lazyLoad = "\" loading=\"lazy";
                    String altTag = "\" alt=\"" + updatedLink;

                    finishHTML.append(lazyLoad).append(altTag);
                }

                String titleTag = "\" title=\"" + updatedLink;
                finishHTML.append(titleTag);
            }
        }
        return finishHTML.toString();
    }

    private boolean isImage(String link)
    {
        return StringUtils.isNotBlank(link) &&
                    (link.endsWith("jpg") ||
                        link.endsWith("jpeg") ||
                        link.endsWith("png") ||
                        link.endsWith("gif")) ||
                        link.endsWith("svg");
    }

    private String updateImageLink(String link)
    {
        String[] linkParts = extractDirectoryGalleryFilenameFromLink(link);
        if (linkParts == null)
        {
            return link;
        }

        String directory = linkParts[0];
        String galleryName = linkParts[1];
        String imageName = linkParts[2];

        Image image = imageDAO.getByDirectoryGalleryName(directory, galleryName, imageName);
        if (image == null)
        {
            image = createImage(directory, galleryName, imageName);
        }
        return image.getImageLink();
    }

    private String[] extractDirectoryGalleryFilenameFromLink(String link)
    {
        final String HTTP = "http";
        if (StringUtils.startsWith(link, HTTP))
        {
            return null;
        }

        String originalLink = link;

        final String SLASH = "/";
        if (StringUtils.startsWith(originalLink, SLASH))
        {
            originalLink = originalLink.substring(1);
        }

        boolean exists = checkIfImageExists(originalLink);
        if (!exists)
        {
            throw new RuntimeException("image does not exist: " + link);
        }

        String[] parts = originalLink.split(SLASH);

        String directory = parts[0];
        String galleryName = null;
        String imageName = "";

        if (parts.length == 2)
        {
            imageName = parts[1];
        }
        else if (parts.length == 3)
        {
            galleryName = parts[1];
            imageName = parts[2];
        }
        else
        {
            throw new RuntimeException("image tag is weird with " + link);
        }

        String[] linkParts = new String[3];
        linkParts[0] = directory;
        linkParts[1] = galleryName;
        linkParts[2] = imageName;

        return linkParts;
    }

    private boolean checkIfImageExists(String link)
    {
        String filepath = deploymentContext.webDirectory() + link;

        return new File(filepath).exists();
    }

    public Image getPostPreviewImage(AbstractPost post)
    {
        List<Image> images = getGalleryImages(post);

        String header = post.getCoverImage();
        if (StringUtils.isNotBlank(header))
        {
            for (Image image : images)
            {
                if (StringUtils.endsWithIgnoreCase(image.getImageName(), header))
                {
                    return image;
                }
            }
        }

        int index = images.size() / 4;
        index = images.size() - index;

        if (index < 0)
        {
            index = 0;
        }
        if (index >= images.size())
        {
            index = images.size() -1;
        }

        if (images.size() > 0)
        {
            return images.get(index);
        }
        return null;
    }

    public String getPostPreviewImageLink(AbstractPost post)
    {
        String link = siteCache.getCachedString(SiteGenerationCache.POST_IMAGE_DIRECT_LINK, post.getUuid());
        if (link == null)
        {
            Image image = getPostPreviewImage(post);
            if (image != null)
            {
                link = image.getImageLink();

                siteCache.addToStringCache(link, SiteGenerationCache.POST_IMAGE_DIRECT_LINK, post.getUuid());
            }
        }
        return link;
    }

    public String getPostPreviewMetadataLink(String uuid)
    {
        String link = "";
        if (StringUtils.isNotBlank(uuid))
        {
            String coverUrlBase = deploymentContext.coverImageLocationInternet();
            link = coverUrlBase + uuid + ".jpg";
        }
        return link;
    }



    public void createPostPreviewImages()
    {
        final String COVER_DIRECTORY = deploymentContext.coverImageLocationLocal();
        final String PHOTO_DIRECTORY = deploymentContext.photoLocation();

        createDirectories(COVER_DIRECTORY);

        List<AbstractPost> posts = postDAO.getAllWithGallery();
        for(AbstractPost post : posts)
        {
            Image coverImage = getPostPreviewImage(post);
            if (coverImage != null)
            {
                String coverOutputLocation = COVER_DIRECTORY + post.getUuid() + ".jpg";

                File coverOutput = new File(coverOutputLocation);
                if (!coverOutput.exists())
                {
                    String coverInputLocation = PHOTO_DIRECTORY + coverImage.getGalleryName() + "/" + coverImage.getImageName();
                    File coverInput = new File(coverInputLocation);

                    try
                    {
                        convertAndCopyImage(coverInput, coverOutput);
                    }
                    catch (Exception e)
                    {
                        System.out.println(String.format("Failure converting image: %s", coverInputLocation));
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void createDirectories(String location)
    {
        File folder = new File(location);
        if (!folder.exists())
        {
            folder.mkdirs();
        }
    }

    private static final int PREVIEW_WIDTH = 1024;

    private static void convertAndCopyImage(File originalLocation, File outputLocation) throws IOException
    {
        convertCopyImage(originalLocation, outputLocation, PREVIEW_WIDTH);
    }

    public void convertAndCopyImage(File originalLocation, File outputLocation, int newImageWidth) throws IOException
    {
        convertCopyImage(originalLocation, outputLocation, newImageWidth);
    }

    private static void convertCopyImage(File originalLocation, File outputLocation, int newImageWidth) throws IOException
    {
        BufferedImage originalImage = ImageIO.read(originalLocation);

        int newWidth = newImageWidth;
        int newHeight = getScaledHeight(newWidth, originalImage);

        java.awt.Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, java.awt.Image.SCALE_SMOOTH);

        // The new Image must not contain an Alpha channel.
        BufferedImage convertedJPG = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
//        BufferedImage convertedJPG = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB); // to copy a PNG

        Graphics2D gr = convertedJPG.createGraphics();
        gr.drawImage(scaledImage, 0, 0, newWidth, newHeight, Color.WHITE, null);
//        gr.drawImage(scaledImage, 0, 0, newWidth, newHeight, null); // to preserve the alpha channel
        gr.dispose();

        String extension = Files.getFileExtension(originalLocation.getName());

        boolean success = ImageIO.write(convertedJPG, extension, outputLocation);
        if(success)
        {
            System.out.println("Converted file: " + originalLocation.getName());
        }
        else
        {
            System.out.println("Write failed for: " + originalLocation.getName());
        }
    }

    private static int getScaledHeight(int width, BufferedImage image)
    {
        double oldWidth = image.getWidth();
        double oldHeight = image.getHeight();

        double ratioWidth = width / oldWidth;

        Double newHeight = ratioWidth * oldHeight;

        return newHeight.intValue();
    }


    public void removeInvalidCharactersFromImageFilenames(String webDirPath) throws IOException
    {
        final String O = "Ō";
        final String TM = "™";

        File directory = new File(webDirPath);

        File[] files = directory.listFiles(new FilenameFilter()
        {
            @Override
            public boolean accept(File dir, String name)
            {
                return isImage(name);
            }
        });

        for (File file : files)
        {
            String name = file.getName();

            name = name.replace(O, "O");
            name = name.replace(TM, "");
            name = name.replace(" ", "_");

            File newfile = new File(webDirPath + name);
            if (!newfile.exists())
            {
                file.renameTo(newfile);
            }
        }
    }

    public void createThumbnailsForDirectory(String location) throws IOException
    {
        FilenameFilter filter = new FilenameFilter()
        {
            public boolean accept(File dir, String name)
            {
                boolean image =  StringUtils.endsWith(name, ".jpg") || StringUtils.endsWith(name, ".png");
                boolean thumbnail = StringUtils.endsWith(name, "_t.jpg") || StringUtils.endsWith(name, "_t.png");

                return image && !thumbnail;
            }
        };

        File folder = new File(location);
        for(File image : folder.listFiles(filter))
        {
            String imageName = image.getAbsolutePath();
            String thumbnailName = imageName.substring(0, imageName.length() - 4);

            if (StringUtils.endsWith(imageName, ".jpg"))
            {
                thumbnailName += "_t.jpg";
            }
            if (StringUtils.endsWith(imageName, ".png"))
            {
                thumbnailName += "_t.png";
            }

            File thumbnail = new File(thumbnailName);
            if (!thumbnail.exists())
            {
                convertAndCopyImage(image, thumbnail);
            }
        }
    }

    public void generateWebpFilesFromImages()
    {
        List<Image> allImages = imageDAO.getAll();
        for(Image image: allImages)
        {
            String webpPath = deploymentContext.imageOutputDirectory() + "/" + image.getWebpFilepath();
            File webpFile = new File(webpPath);

            if (!webpFile.exists())
            {
                File folder = webpFile.getParentFile();
                if (!folder.exists())
                {
                    folder.mkdirs();
                }

                String imagePath = deploymentContext.webDirectory() + image.getImagePath();
                try
                {
                    BufferedImage original = ImageIO.read(new File(imagePath));

                    boolean success = ImageIO.write(original, "webp", new File(webpPath));
                    if(success)
                    {
                        System.out.println("Successfully Converted file: " + imagePath);
                    }
                    else
                    {
                        System.out.println("Something is wrong: " + imagePath);
                    }
                }
                catch (Exception e)
                {
                    System.out.println("Problem with this file: " + imagePath);
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws Exception
    {
        final int IMAGE_WIDTH = 128;

        final String INPUT_DIR =  "";
        final String OUTPUT_DIR = "";

//        List<String> names = Arrays.asList("901b", "1017c", "1017h", "1017w");

        if (StringUtils.isNotBlank(INPUT_DIR) && StringUtils.isNotBlank(OUTPUT_DIR))
        {
//            for(String name : names)
//            {
//                File oldImage = new File(INPUT_DIR + name + ".png");
//                File newImage = new File(OUTPUT_DIR + name + ".png");
//
//                convertAndCopyImage(oldImage, newImage, IMAGE_WIDTH);
//            }

//            for(int i = 1011; i <= 1017; i++)
//            {
//                File oldImage = new File(INPUT_DIR + i + ".png");
//                File newImage = new File(OUTPUT_DIR + i + ".png");
//
//                convertAndCopyImage(oldImage, newImage, IMAGE_WIDTH);
//            }
        }
    }
}
