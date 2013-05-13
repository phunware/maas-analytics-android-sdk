package com.phunware.core.internal;

import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.phunware.core.PwCoreSession;
import com.phunware.core.PwLog;

import android.util.Base64;

/**
 * Used to encrypt and decrypt data between the mobile client and Phunware's
 * infrastructure servers
 * 
 */
public class Cryptor {

	/**
	 * Decrypts data that was encrypted on Phunware's infrastructure servers
	 * 
	 * @param encryptedData A string of data encrypted by Phunware's infrastructure servers
	 * @param encryptionKey The key used to encrypt data. Can be obtained by {@link PwCoreSession#getEncryptionKey()}.
	 * @return The decrypted data or null if the data was unable to be decrypted
	 */
	public String decrypt(String encryptedData, String encryptionKey) {
		byte[] output = null;
		
		try {
			String key = encryptionKey;
			if (key == null) {
				return null;
			}
		
			byte[] k = key.getBytes("UTF-8");
			MessageDigest sha = MessageDigest.getInstance("SHA-256");
			k = sha.digest(k);
			SecretKeySpec skey = new SecretKeySpec(k, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
			
			byte[] iv = new byte[16];
			IvParameterSpec ivspec = new IvParameterSpec(iv);
			cipher.init(Cipher.DECRYPT_MODE, skey, ivspec);
			
			byte[] mByte = Base64.decode(encryptedData, Base64.DEFAULT);
			output = cipher.doFinal(mByte);
			return new String(output);
		
		} catch (Exception e) {
			PwLog.d("PHUNWARE_CORE",
					"Cryptor[decrypt]: exception " + e.toString());
		}
		return null;
	}

	/**
	 * Encrypts data to send to Phunware's infrastructure servers
	 * 
	 * @param input
	 *            A string of data to be encrypted
	 * @return The encrypted string or null if the data was unable to be
	 *         encrypted
	 */
	public String encrypt(String input, String encryptionKey) {
		byte[] encoded = null;
		String key = encryptionKey;
		
		if (key == null) {
			return null;
		}
		
		try {
			byte[] k = key.getBytes("UTF-8");
			MessageDigest sha = MessageDigest.getInstance("SHA-256");
			k = sha.digest(k);
		
			SecretKeySpec skey = new SecretKeySpec(k, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
			
			byte[] iv = new byte[16];
			IvParameterSpec ivspec = new IvParameterSpec(iv);
			cipher.init(Cipher.ENCRYPT_MODE, skey, ivspec);
			encoded = cipher.doFinal(input.getBytes("UTF-8"));
		
		} catch (Exception e) {
			PwLog.d("PHUNWARE_CORE",
					"Cryptor[encrypt]: exception " + e.toString());
			return null;
		}
		return Base64.encodeToString(encoded, Base64.DEFAULT);
	}
}
