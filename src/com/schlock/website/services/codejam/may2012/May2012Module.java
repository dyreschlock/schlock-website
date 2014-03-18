package com.schlock.website.services.codejam.may2012;

import com.schlock.website.services.codejam.may2012.impl.DecisionManagementImpl;
import org.apache.tapestry5.ioc.ServiceBinder;

public class May2012Module
{
    public static void bind(ServiceBinder binder)
    {
        binder.bind(DecisionManagement.class, DecisionManagementImpl.class);
    }
}
