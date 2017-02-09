package com.schibsted.webapp.server.helper;

import org.junit.Assert;
import org.junit.Test;

import com.schibsted.webapp.server.base.BaseTest;

public class StringHelperTest extends BaseTest {

	@Test
	public void isNotEmpty() {
		Assert.assertTrue(StringHelper.isNotEmpty("aa"));
		Assert.assertTrue(!StringHelper.isNotEmpty(""));
		Assert.assertTrue(!StringHelper.isNotEmpty("  "));
		Assert.assertTrue(!StringHelper.isNotEmpty(null));
	}
}
