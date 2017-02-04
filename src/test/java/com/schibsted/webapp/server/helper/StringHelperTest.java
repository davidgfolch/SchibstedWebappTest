package com.schibsted.webapp.server.helper;

import org.junit.*;
import static org.junit.Assert.*;

public class StringHelperTest {

	@Test
	public void isNotEmpty() {
		Assert.assertTrue(StringHelper.isNotEmpty("aa"));
		Assert.assertTrue(!StringHelper.isNotEmpty(""));
		Assert.assertTrue(!StringHelper.isNotEmpty("  "));
		Assert.assertTrue(!StringHelper.isNotEmpty(null));
	}
}
