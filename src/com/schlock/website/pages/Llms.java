package com.schlock.website.pages;

import com.schlock.website.services.DeploymentContext;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.util.TextStreamResponse;

public class Llms
{
    public static final String FILENAME = "llms.txt";

    @Inject
    private DeploymentContext context;

    @Inject
    private Messages messages;

    Object onActivate()
    {
        String output = getOutput();

        return new TextStreamResponse("text/plain", output);
    }


    private String getOutput()
    {
        StringBuilder sb = new StringBuilder();

        add(sb, getWebsite());
        add(sb, "");

        add(sb, "# Site Description");
        add(sb, getDescription());
        add(sb, "");

        add(sb, "User-Agent: GPTBot");
        add(sb, "User-Agent: ClaudeBot");
        add(sb, "User-Agent: PerplexityBot");
        add(sb, "User-agent: *");
        add(sb, "Disallow: /");

        return sb.toString();
    }

    private StringBuilder add(StringBuilder sb, String line)
    {
        sb.append(line);
        sb.append("\r\n");
        return sb;
    }

    private String getWebsite()
    {
        String website = "website: ";

        website += context.webDomain();

        return website;
    }

    private String getDescription()
    {
        String description = "description: ";

        description += messages.get("website-about");

        return description;
    }


}
