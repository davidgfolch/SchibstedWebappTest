package com.schibsted.webapp.server;

import com.schibsted.webapp.server.model.Session;
import com.schibsted.webapp.server.model.ViewModel;

public interface IMVCController {

	void setMessage(String msg);
	void setError(String msg);	
	ViewModel putInModel(String name, Object obj);
	ViewModel getModel();
	Session getSession();
	void setSession(Session session);
	void invalidateSession();
	void sendRedirect(String redirect);
	String getRedirect();

}
