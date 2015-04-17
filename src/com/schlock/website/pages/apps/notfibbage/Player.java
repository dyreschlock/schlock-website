package com.schlock.website.pages.apps.notfibbage;

import com.schlock.website.services.apps.notfibbage.NotfibbageManagement;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.ioc.annotations.Inject;

public class Player
{

    @Inject
    private NotfibbageManagement management;


    @Persist
    private String playerName;



    Object onActivate()
    {
        if (this.playerName != null)
        {
            boolean registered = management.isRegisteredPlayer(this.playerName);
            if (!registered)
            {
                this.playerName = null;
            }
        }

        return true;
    }

    Object onActivate(String parameter)
    {
        boolean registered = management.isRegisteredPlayer(parameter);
        if (!registered)
        {
            return onActivate();
        }

        this.playerName = parameter;
        return true;
    }
}
