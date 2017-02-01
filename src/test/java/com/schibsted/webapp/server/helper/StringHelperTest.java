package com.schibsted.webapp.server.helper;

import org.junit.Assert;
import org.junit.Test;

public class StringHelperTest {

	@Test
	public void isNotEmpty() {
		Assert.assertTrue(StringHelper.isNotEmpty("aa"));
		Assert.assertTrue(!StringHelper.isNotEmpty(""));
		Assert.assertTrue(!StringHelper.isNotEmpty("  "));
		Assert.assertTrue(!StringHelper.isNotEmpty(null));
	}
}
