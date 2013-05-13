package com.phunware.core.sample.test;

import java.security.SignatureException;

import android.test.AndroidTestCase;
import android.util.Log;

import com.phunware.core.PwCoreSession;
import com.phunware.core.internal.Cryptor;
import com.phunware.core.internal.ServerUtilities;

public class TestCryptor extends AndroidTestCase {
	
	private static final String TAG = "TestCryptor";
	@Override
	public void setUp() throws Exception {
		PwCoreSession.killSession();
		PwCoreSession.getInstance().registerKeys(getContext(), ACCESS_KEY, SIGNATURE_KEY, encryptionKey);
		super.setUp();
	}

	@Override
	public void tearDown() throws Exception {
		PwCoreSession.killSession();
		super.tearDown();
	}

	private String input = "{menu_id:2, parent_id:4}";
	private String expectedEncryptedResult = "qeKpnjs1JKnkqCt6gmd/LghO0OknhFsyZQO9qAsbHiI=\n";
	private String encryptionKey = "zxcvbnmasdfghjklqwertyuiop123456";
	
	public void testEncryption() {
		// Pass Case
		String encryptedData = new Cryptor().encrypt(input, encryptionKey);
		assertNotNull(encryptedData);
		assertEquals(expectedEncryptedResult, encryptedData);
		
		// Fail Case
		encryptedData = new Cryptor().encrypt(null, "Wrong_key");
		assertNull(encryptedData);
		
		encryptedData = new Cryptor().encrypt(null, null);
		assertNull(encryptedData);
		
		encryptedData = new Cryptor().encrypt("", null);
		assertNull(encryptedData);
	}

	public void testDecryption() {
		// Pass Case
		String decryptedData = new Cryptor().decrypt(expectedEncryptedResult, encryptionKey);
		//make sure the decryption worked in general
		assertNotNull(decryptedData);
		
		//make sure the decryption method gave us the initial input
		assertEquals(input, decryptedData);
		
		// Fail Case
		decryptedData = new Cryptor().decrypt(null, encryptionKey);
		assertNull(decryptedData);
		
		decryptedData = new Cryptor().decrypt(expectedEncryptedResult, null);
		assertNull(decryptedData);
		
		decryptedData = new Cryptor().decrypt(null, null);
		assertNull(decryptedData);
		
		decryptedData = new Cryptor().decrypt("qwkrhfs8uaodskgl", encryptionKey);
		assertNotSame(decryptedData, input);
	}
	
	
	/*
	private static final String ACCESS_KEY = "zxcvbnmasdfghjklqwertyuiop123456";
	private static final String SIGNATURE_KEY = "signaturehere";
	private static final String REQUEST_BODY = "{ \"name\":\"Robert\", \"somenumber\":52}";
	private static final long TIMESTAMP = 1367592012377L;
	private static final String EXPECTED_VALUE = "2e8693430f8777b0966f5ae56e42d0b46e16cff4e046818005ad515f34235494";
	private static final String HTTP_TYPE = "POST";
	*/
	
	private static final String ACCESS_KEY = "gWczdpdx86Duhu65";
	private static final String SIGNATURE_KEY = "2W2tFrvPZC5wQB8q";
	private static final String REQUEST_BODY = "{\"menu_id\":\"2\",\"parent_id\":\"3\"}";
	private static final long TIMESTAMP = 1367003068L;
	private static final String EXPECTED_VALUE = "c22b765adde053eff732e27627b731d8c1ef3268c1deb28cbe009ecb75f23505";
	private static final String HTTP_TYPE = "POST";
	
	public void testGenerateDigitalSignature()
	{
		
		Log.v(TAG, "HTTP Type: "+ HTTP_TYPE);
		Log.v(TAG, "Access Key: "+ACCESS_KEY);
		Log.v(TAG, "Timestamp: "+TIMESTAMP);
		Log.v(TAG, "Request Body: "+REQUEST_BODY);
		Log.v(TAG, "Signature Key: "+SIGNATURE_KEY);
		Log.v(TAG, "-------------------------------");
		Log.v(TAG, "Expected Value: " + EXPECTED_VALUE);
		//Pass
		String enc = null;
		try {
			enc = ServerUtilities.getDigitalSignature(HTTP_TYPE,String.valueOf(TIMESTAMP), REQUEST_BODY);
		} catch (SignatureException e) {
			assertTrue(false);
		}
		assertNotNull(enc);
		assertEquals(EXPECTED_VALUE, enc);
		
		//Fail
		enc = null;
		try {
			enc = ServerUtilities.getDigitalSignature(HTTP_TYPE,null, REQUEST_BODY);
		} catch (SignatureException e) {
			assertTrue(false);
		}
		assertNotSame(EXPECTED_VALUE, enc);
		
		enc = null;
		try {
			enc = ServerUtilities.getDigitalSignature(HTTP_TYPE,String.valueOf(TIMESTAMP), null);
		} catch (SignatureException e) {
			assertTrue(false);
		}
		assertNotSame(EXPECTED_VALUE, enc);
		
		enc = null;
		try {
			enc = ServerUtilities.getDigitalSignature(HTTP_TYPE,null, null);
		} catch (SignatureException e) {
			assertTrue(false);
		}
		assertNotSame(EXPECTED_VALUE, enc);
		
		enc = null;
		try {
			enc = ServerUtilities.getDigitalSignature(null,null, REQUEST_BODY);
		} catch (SignatureException e) {
			assertTrue(false);
		}
		assertNotSame(EXPECTED_VALUE, enc);
		
		enc = null;
		try {
			enc = ServerUtilities.getDigitalSignature(null,String.valueOf(TIMESTAMP), null);
		} catch (SignatureException e) {
			assertTrue(false);
		}
		assertNotSame(EXPECTED_VALUE, enc);
		
		enc = null;
		try {
			enc = ServerUtilities.getDigitalSignature(null,null, null);
		} catch (SignatureException e) {
			assertTrue(false);
		}
		assertNotSame(EXPECTED_VALUE, enc);
	}
}