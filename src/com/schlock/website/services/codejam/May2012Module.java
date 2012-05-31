package com.schlock.website.services.codejam;

import com.schlock.website.codejam.may2012.services.DecisionManagement;
import com.schlock.website.codejam.may2012.services.impl.DecisionManagementImpl;
import org.apache.tapestry5.ioc.ServiceBinder;

public class May2012Module
{
    public static void bind(ServiceBinder binder)
    {
        binder.bind(DecisionManagement.class, DecisionManagementImpl.class);
    }
}
