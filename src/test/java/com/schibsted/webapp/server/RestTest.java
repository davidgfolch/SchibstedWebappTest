package com.schibsted.webapp.server;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItem;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.schibsted.webapp.persistence.InMemory;
import com.schibsted.webapp.server.base.ServerBaseTest;
import com.schibsted.webapp.server.model.User;
import com.schibsted.webapp.server.model.ViewModel;
import com.schibsted.webapp.server.to.UsersTO;

public class RestTest extends ServerBaseTest {

	ObjectMapper mapper=new ObjectMapper();

	@Test
	public void userGet() throws IOException {
		String url="/rest/user/user1";
		assertEquals(HttpURLConnection.HTTP_OK,serverTestHelper.getResponseCode(url));
		String json=serverTestHelper.getResponseBody(url);
		assertNotNull(json);
		ViewModel viewModel=mapper.readValue(json,ViewModel.class);
		assertNotNull(viewModel);

		
		//List<User> users = mapper.readValue(json, new TypeReference<List<User>>() {});
		UsersTO users = mapper.readValue(json, UsersTO.class);
		assertNotNull(users);
		assertNotNull(users.getUsers());
//		assertThat(users.getUsers(), hasItem(hasProperty("name", is("user1"))));
		assertEquals("user1",users.getUsers().get(0).getName());
		
//		List<LinkedHashMap<String, Object>> users = (List<LinkedHashMap<String,Object>>) viewModel.get("users");
//		assertEquals("user1",users.get(0).get("name"));
//		assertNotNull(users);
	}

}
