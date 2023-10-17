package com.schlock.website.services.blog.impl;

import com.schlock.website.entities.Icon;
import com.schlock.website.services.DeploymentContext;
import com.schlock.website.services.blog.IconManagement;
import org.apache.commons.io.FileUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.tapestry5.services.Context;

import java.io.File;
import java.io.IOException;

public class IconManagementImpl implements IconManagement
{
    private DeploymentContext deploymentContext;
    private Context context;

    public IconManagementImpl(DeploymentContext deploymentContext,
                              Context context)
    {
        this.deploymentContext = deploymentContext;
        this.context = context;
    }

    public String createBase64ImgLink(Icon icon)
    {
        String base64 = convertIconToBase64(icon);

        return "data:image/png;base64," + base64;
    }

    public String convertIconToBase64(Icon icon)
    {
        String path = icon.getIconPath();
        File resource = context.getRealFile(path);
        if (resource.exists())
        {
            try
            {
                byte[] body = FileUtils.readFileToByteArray(resource);
                String base64 = Base64.encodeBase64String(body);

                return base64;
            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public String getIconLink(Icon icon)
    {
        String link = deploymentContext.webDomain() + icon.getIconPath();
        return link;
    }
}
