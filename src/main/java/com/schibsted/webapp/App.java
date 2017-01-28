package com.schibsted.webapp;

import com.schibsted.webapp.server.Server;

public class App {
	
	private App() { //
	}
	
	public static void main(String[] args) {
		//Tell java logger to use log4j logger
		System.setProperty("java.util.logging.manager","org.apache.logging.log4j.jul.LogManager");
//		java.util.logging.Logger.getLogger("com.sun.net.httpserver").setLevel(Level.FINER);
		new Server().startServer();
	}

}
