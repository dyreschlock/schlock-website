package com.schlock.website.services.blog.impl;

import com.schlock.website.entities.blog.AbstractPost;
import com.schlock.website.entities.blog.Image;
import com.schlock.website.entities.blog.Post;
import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.blog.ImageManagement;
import com.schlock.website.services.database.blog.ImageDAO;
import com.schlock.website.services.database.blog.PostDAO;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.*;

public class ImageManagementImpl implements ImageManagement
{
    private final DeploymentContext deploymentContext;

    private final PostDAO postDAO;
    private final ImageDAO imageDAO;

    public ImageManagementImpl(DeploymentContext deploymentContext,
                               PostDAO postDAO,
                               ImageDAO imageDAO)
    {
        this.deploymentContext = deploymentContext;

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

    public void generateImages()
    {
        Set<String> galleries = postDAO.getAllGalleryNames();
        for(String gallery : galleries)
        {
            generateImagesByGallery(gallery);
        }

        List<Image> images = imageDAO.getAllWithoutGooleId();
        for(Image image : images)
        {
            generateGoogleId(image);
        }

        List<AbstractPost> posts = postDAO.getAll();
        for(AbstractPost post : posts)
        {

        }
        //TODO find images in post html
    }

    private Map<String, Image> generateImagesByGallery(String galleryName)
    {
        File gallery = new File(deploymentContext.photoLocation() + galleryName);
        if (gallery.exists())
        {
            Map<String, Image> cache = getImagesByGallery(galleryName);

            File[] nonThumbnails = gallery.listFiles(new FilenameFilter()
            {
                public boolean accept(File dir, String name)
                {
                    if (StringUtils.endsWith(name, "_t.jpg"))
                    {
                        return false;
                    }
                    if (StringUtils.endsWith(name, ".jpg") ||
                            StringUtils.endsWith(name, ".png") ||
                            StringUtils.endsWith(name, ".gif"))
                    {
                        return true;
                    }
                    return false;
                }
            });

            for (File file : nonThumbnails)
            {
                String imageName = file.getName();
                Image image = cache.get(imageName);
                if (image == null)
                {
                    image = createImage(file.getName(), galleryName);
                    imageDAO.save(image);

                    cache.put(image.getImageName(), image);
                }
                generateGoogleId(image);
            }

            File[] thumbnails = gallery.listFiles(new FilenameFilter()
            {
                public boolean accept(File dir, String name)
                {
                    return StringUtils.endsWith(name, "_t.jpg");
                }
            });

            for(File file : thumbnails)
            {
                String imageName = file.getName();
                Image image = cache.get(imageName);
                if (image == null)
                {
                    String parentName = imageName.replace("_t.jpg", ".jpg");
                    Image parent = cache.get(parentName);

                    image = createImage(file.getName(), galleryName, parent);
                    imageDAO.save(image);

                    cache.put(image.getImageName(), image);
                }
                generateGoogleId(image);
            }
            return cache;
        }
        return Collections.EMPTY_MAP;
    }

    private Image createImage(String imageName, String galleryName)
    {
        return createImage(imageName, galleryName, null);
    }

    private Image createImage(String imageName, String galleryName, Image parent)
    {
        String directory = DeploymentContext.PHOTO_DIR;
        directory = directory.substring(0, directory.length() - 1);

        return createImage(imageName, galleryName, directory, parent);
    }

    private Image createImage(String imageName, String galleryName, String directory, Image parent)
    {
        Image image = new Image();
        image.setDirectory(directory);
        image.setGalleryName(galleryName);
        image.setImageName(imageName);
        image.setParent(parent);

        generateGoogleId(image);

        return image;
    }



    private void generateGoogleId(Image image)
    {
        String googleId = image.getGoogleId();
        if (StringUtils.isBlank(googleId))
        {
            //TODO do google stuff

            image.setGoogleId(googleId);
            imageDAO.save(image);
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

}
