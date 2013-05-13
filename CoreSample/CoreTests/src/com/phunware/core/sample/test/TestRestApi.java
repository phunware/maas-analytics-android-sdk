package com.phunware.core.sample.test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import android.test.AndroidTestCase;

import com.phunware.core.exceptions.EncryptionException;
import com.phunware.core.internal.CoreFactory;
import com.phunware.core.internal.Cryptor;
import com.phunware.core.internal.ServerUtilities;

public class TestRestApi extends AndroidTestCase {

	@Override
	public void setUp() throws Exception {
		super.setUp();
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}

	// Test the server utilities class post method
	public void testPost() {

		// Test arguments
		try {
			ServerUtilities.post(null, null, false);
			assertTrue(false);
		} catch (EncryptionException e) {
			assertTrue(false);
		} catch (IOException e) {
			assertTrue(false);
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}

		// Test encryption
		Cryptor cryptor = mock(Cryptor.class);
		CoreFactory.getInstance().setCryptor(cryptor);
		try {
			ServerUtilities.post("http://phunware.com", "{menu_id:2,parent_id:4}", true);
		} catch (EncryptionException e) {
		} catch (IOException e) {
		}
		verify(cryptor, times(1)).encrypt(any(String.class), any(String.class));

	}

	// Test the server utilities class get method
	public void testGet() {

	}
}
