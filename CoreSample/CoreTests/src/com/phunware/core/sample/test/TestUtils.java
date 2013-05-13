package com.phunware.core.sample.test;

import android.test.AndroidTestCase;
import android.util.Log;

import com.phunware.core.internal.Utils;

public class TestUtils extends AndroidTestCase {
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testSecurityHash() {
		String hash = Utils.sha256Encrypt("test");
		Log.v("TEST", hash);
		assertNotNull(hash);
	}
}
