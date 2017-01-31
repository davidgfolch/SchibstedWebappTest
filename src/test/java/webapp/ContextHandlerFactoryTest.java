package webapp;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.schibsted.webapp.persistence.InMemory;
import com.schibsted.webapp.server.contextHandler.ContextHandlerFactory;
import com.schibsted.webapp.server.contextHandler.ContextHandlerFactory.CONTEXT_HANDLER;
import com.schibsted.webapp.server.contextHandler.WebContextHandler;
import com.schibsted.webapp.server.helper.UserHelper;
import com.schibsted.webapp.server.model.Role;
import com.schibsted.webapp.server.model.User;

public class ContextHandlerFactoryTest {

	@Test
	public void getByEnumType() {
		assertTrue(ContextHandlerFactory.get(ContextHandlerFactory.CONTEXT_HANDLER.WEB_CONTEXT_HANDLER) instanceof WebContextHandler);
	}

	@Test
	public void getByString() {
		assertTrue(ContextHandlerFactory.get("WebContextHandler") instanceof WebContextHandler);
	}

}
