package com.schlock.website.pages;

import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.util.TextStreamResponse;

public class Manifest
{
    @Inject
    private Messages messages;

    Object onActivate() {

        String title = messages.get("website-title");
        String url = messages.get("website-url");

        String description = messages.get("website-about");
        description = description.replace("<b>", "");
        description = description.replace("</b>", "");

        JSONObject json = new JSONObject(
                "name", title,
                "short_name", title,
                "description", description,
                "start_url", url,
                "lang", "en-US",
                "dir", "ltr",
                "theme_color", "#2c2c2c",
                "background_color", "#fafafa",
                "display", "minimal-ui"
        );

        json.put("icons", getIcons());

        return new TextStreamResponse("application/json", json.toCompactString());
    }

    private JSONArray getIcons()
    {
        JSONObject icon192 = getIcon("192");
        JSONObject icon512 = getIcon("512");

        return new JSONArray(icon192, icon512);
    }

    private JSONObject getIcon(String size)
    {
        return new JSONObject(
            "src", "/icons/fav-" + size + ".png",
                "type", "image/png",
                "sizes", size + "x" + size
        );
    }
}
