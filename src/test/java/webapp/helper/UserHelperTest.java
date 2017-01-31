package webapp.helper;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.schibsted.webapp.persistence.InMemory;
import com.schibsted.webapp.server.helper.UserHelper;
import com.schibsted.webapp.server.model.Role;
import com.schibsted.webapp.server.model.User;

public class UserHelperTest {

	private final List<User> users=InMemory.getUsers();
	private final String testUserName="user1";
	private final String testUserPwd="user1";
	
	private final String[] roles= new String[]{"ADMIN","PAGE_1"};

	@Test
	public void checkCreadentials() {
		assertNotNull(UserHelper.checkCreadentials(InMemory.getUsers(), testUserName, testUserPwd));
	}

	@Test
	public void createRoles() {
		assertTrue(
				UserHelper.createRoles(roles) //
				.stream() //
				.map(Role::getRole)
				.filter(a->roles[0].equals(a) || roles[1].equals(a))
				.toArray()
				.length==roles.length);
	}

	@Test
	public void getUser() {
		assertNotNull(UserHelper.getUser(users, testUserName));
	}

}
