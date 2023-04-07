package com.schlock.website.services.blog.impl;

import com.google.common.io.Files;
import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.Image;
import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.blog.GoogleManagement;
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
import java.util.*;
import java.util.List;

public class ImageManagementImpl implements ImageManagement
{

    private final DeploymentContext deploymentContext;

    private final GoogleManagement googleManagement;
    private final PostManagement postManagement;

    private final PostDAO postDAO;
    private final ImageDAO imageDAO;

    public ImageManagementImpl(DeploymentContext deploymentContext,
                               GoogleManagement googleManagement,
                               PostManagement postManagement,
                               PostDAO postDAO,
                               ImageDAO imageDAO)
    {
        this.deploymentContext = deploymentContext;

        this.googleManagement = googleManagement;
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


    public String updateImagesInHTML(String h)
    {
        String html = h;

        final String IMG_TAG = "<img ";
        final String SRC_PARAM = "src=\"";

        html = updateImagesInHTML(IMG_TAG, SRC_PARAM, html);

        final String A_TAG = "<a ";
        final String HREF_PARAM = "href=\"";

        html = updateImagesInHTML(A_TAG, HREF_PARAM, html);

        return html;
    }

    private String updateImagesInHTML(final String TAG, final String PARAM, String h)
    {
        final String QUOTE = "\"";

        String finishHTML = "";
        String remainHTML = h;

        while(StringUtils.isNotBlank(remainHTML))
        {
            int index = remainHTML.indexOf(TAG);
            if (index == -1)
            {
                finishHTML += remainHTML;
                remainHTML = "";
            }
            else
            {
                finishHTML += remainHTML.substring(0, index);
                remainHTML = remainHTML.substring(index);

                index = remainHTML.indexOf(PARAM) + PARAM.length();

                finishHTML += remainHTML.substring(0, index);
                remainHTML = remainHTML.substring(index);

                index = remainHTML.indexOf(QUOTE);

                String imageLink = remainHTML.substring(0, index);
                if (isImage(imageLink))
                {
                    finishHTML += updateImageLink(imageLink);
                }
                else
                {
                    finishHTML += postManagement.updateLinkToModernReference(imageLink);
                }
                remainHTML = remainHTML.substring(index);
            }
        }
        return finishHTML;
    }

    private boolean isImage(String link)
    {
        return StringUtils.isNotBlank(link) &&
                    (link.endsWith("jpg") ||
                        link.endsWith("jpeg") ||
                        link.endsWith("png") ||
                        link.endsWith("gif"));
    }

    private String updateImageLink(String link)
    {
        String originalLink = link;

        final String HTTP = "http";
        final String SLASH = "/";

        if (StringUtils.startsWith(originalLink, HTTP))
        {
            return originalLink;
        }

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

        Image image = imageDAO.getByDirectoryGalleryName(directory, galleryName, imageName);
        if (image == null)
        {
            image = createImage(directory, galleryName, imageName);
        }
        generateGoogleId(image);

        return image.getImageLink();
    }

    private boolean checkIfImageExists(String link)
    {
        String filepath = deploymentContext.webDirectory() + link;

        return new File(filepath).exists();
    }

    private void generateGoogleId(Image image)
    {
        String googleId = image.getGoogleId();
        if (StringUtils.isBlank(googleId))
        {
            googleId = googleManagement.getGoogleIdForImage(image);

            image.setGoogleId(googleId);
            imageDAO.save(image);
        }

        if (image.getParent() != null)
        {
            generateGoogleId(image.getParent());
        }
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

    public Image getPostImage(AbstractPost post)
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

    public String getPostPreviewMetadataLink(AbstractPost post)
    {
        String link = "";
        if (post != null)
        {
            String uuid = post.getUuid();
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
            Image coverImage = getPostImage(post);
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

    private static void convertAndCopyImage(File originalLocation, File outputLocation) throws Exception
    {
        BufferedImage originalImage = ImageIO.read(originalLocation);

        int newWidth = PREVIEW_WIDTH;
        int newHeight = getScaledHeight(originalImage);

        java.awt.Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, java.awt.Image.SCALE_SMOOTH);

        // The new Image must not contain an Alpha channel.
        BufferedImage convertedJPG = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);

        Graphics2D gr = convertedJPG.createGraphics();
        gr.drawImage(scaledImage, 0, 0, newWidth, newHeight, Color.WHITE, null);
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

    private static int getScaledHeight(BufferedImage image)
    {
        double oldWidth = image.getWidth();
        double oldHeight = image.getHeight();

        double ratioWidth = PREVIEW_WIDTH / oldWidth;

        Double newHeight = ratioWidth * oldHeight;

        return newHeight.intValue();
    }




    private static void createThumbnailsForDirectory(String location) throws Exception
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

    public static void main(String[] args) throws Exception
    {
        String LOCATION = "";

//        createThumbnailsForDirectory(LOCATION);
    }
}
