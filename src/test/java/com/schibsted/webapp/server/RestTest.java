package com.schibsted.webapp.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.LinkedHashMap;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.schibsted.webapp.server.base.ServerBaseTest;
import com.schibsted.webapp.server.exception.DataException;
import com.schibsted.webapp.server.to.UsersTO;

public class RestTest extends ServerBaseTest {

	ObjectMapper mapper=new ObjectMapper();

	@Test
	public void get() throws IOException {
		String url="/rest/user/user1";
		assertEquals(HttpURLConnection.HTTP_OK,serverTestHelper.getResponseCode(url));
		String json=serverTestHelper.getResponseBody(url);
		assertNotNull(json);
//		ViewModel viewModel=mapper.readValue(json,ViewModel.class);
//		assertNotNull(viewModel);
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

	@Test
	public void deleteFail() throws IOException {
		String url="/rest/user/user";
		assertEquals(HttpURLConnection.HTTP_OK,serverTestHelper.getResponseCode(url,"DELETE"));
		String json=serverTestHelper.getResponseBody(url,null,"DELETE",true);
		assertNotNull(json);
		UsersTO usersTO= mapper.readValue(json, UsersTO.class);
		assertNotNull(usersTO.getException());
	}

	@Test
	public void deleteOK() throws IOException {
		//Note: user1 is used to other test operatons don't delete
		String url="/rest/user/user2";
		assertEquals(HttpURLConnection.HTTP_OK,serverTestHelper.getResponseCode(url,"DELETE"));
		url="/rest/user/user3";
		String json=serverTestHelper.getResponseBody(url,null,"DELETE",true);
		assertNotNull(json);
		UsersTO usersTO= mapper.readValue(json, UsersTO.class);
		assertNull(usersTO.getException());
	}

}
