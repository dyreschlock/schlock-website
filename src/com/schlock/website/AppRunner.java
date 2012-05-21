package com.schlock.website;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class AppRunner
{
	public static void main(String[] args) throws Exception
	{
		System.setProperty("org.apache.tapestry.disable-caching", "false");
		System.setProperty("org.apache.tapestry.enable-reset-service", "true");

		Server server = new Server(8080);

		WebAppContext context = new WebAppContext();
		context.setContextPath("/");
		context.setWar("web");	

		server.setHandler(context);
		server.start();
		server.join();
	}
}