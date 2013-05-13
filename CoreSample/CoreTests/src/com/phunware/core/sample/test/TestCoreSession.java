package com.phunware.core.sample.test;

import android.test.AndroidTestCase;

import com.phunware.core.PwCoreSession;

public class TestCoreSession extends AndroidTestCase {
	// TODO test public methods for CoreSessionImp and PwCoreSession
	private static final String ACCESS_KEY = "ACCESS_KEY";
	private static final String SIGNATURE_KEY = "SIGNATURE_KEY";
	private static final String ENCRYPTION_KEY = "ENCRYPTION_KEY";
	
	@Override
	protected void setUp() throws Exception {
		PwCoreSession.getInstance().registerKeys(getContext(), ACCESS_KEY, SIGNATURE_KEY, ENCRYPTION_KEY);
		super.setUp();
	}
	@Override
	protected void tearDown() throws Exception {
		PwCoreSession.killSession();
		super.tearDown();
	}

	public void testKeys() {
		assertEquals(ACCESS_KEY, PwCoreSession.getInstance().getAccessKey());
		assertEquals(SIGNATURE_KEY, PwCoreSession.getInstance().getSignatureKey());
		assertEquals(ENCRYPTION_KEY, PwCoreSession.getInstance().getEncryptionKey());
	}
}
