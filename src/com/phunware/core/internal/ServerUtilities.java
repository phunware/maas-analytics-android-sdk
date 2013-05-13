package com.phunware.core.internal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.text.SimpleDateFormat;
import java.util.Formatter;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

import com.phunware.core.PwCoreSession;
import com.phunware.core.PwLog;
import com.phunware.core.exceptions.DecryptionException;
import com.phunware.core.exceptions.EncryptionException;

/**
 * Helper class used to communicate with the server.
 */
public class ServerUtilities {

	private static final String TAG = "ServerUtilities";
	private static final String HASH_ALGORITHM = "HmacSHA256";

	/**
	 * Method name for HTTP call type Get
	 */
	public static final String HTTP_TYPE_GET = "GET";
	/**
	 * Method name for HTTP call type Post
	 */
	public static final String HTTP_TYPE_POST = "POST";
	/**
	 * Method name for HTTP call type Put
	 */
	public static final String HTTP_TYPE_PUT = "PUT";
	/**
	 * Method name for HTTP call type Delete
	 */
	public static final String HTTP_TYPE_DELETE = "DELETE";

	/**
	 * Issue a POST request to the server.
	 * 
	 * @param endpoint
	 *            Host url
	 * @param body
	 *            as a JSON string.
	 * @param encryptData
	 *            <code>true</code> to encrypt the data being sent,
	 *            <code>false</code> to not worry about it. Only encrypt the
	 *            calls that need to be encrypted. Everything can be encrypted
	 *            however there is more overhead when encrypting everything.
	 * @throws IOException
	 * @throws Exception
	 */
	public static void post(String endpoint, String body, boolean encryptData)
			throws EncryptionException, IOException {
		Log.d(TAG, "test::testtesta");

		if (endpoint == null || body == null) {
			throw new IllegalArgumentException("invalid argument(s)");
		}

		// Setup the URL
		URL url;

		try {
			url = new URL(endpoint);
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException("invalid url: " + endpoint);
		}

		byte[] bytes = body.getBytes();
		HttpURLConnection conn = null;

		try {
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setFixedLengthStreamingMode(bytes.length);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			PwLog.d(TAG, "test::1");

			// Handle encryption
			if (encryptData) {
				PwLog.d(TAG, "test::2");
				Cryptor cryptor = CoreFactory.createCryptor();
				String encryptedBody = cryptor
						.encrypt(body, getEncryptionKey());
				if (encryptedBody == null) {
					throw new EncryptionException("Failed to encrypt body.");
				} else {
					body = encryptedBody;
					conn.setRequestProperty("Content-Type",
							"application/json-encrypted");
				}
			}
			PwLog.d(TAG, "test::3");
			// Add security
			String authHeader = getAuthenticationHeader(HTTP_TYPE_GET,body);
			conn.setRequestProperty("X-API-Auth", authHeader);
			PwLog.d(TAG, "test::4");

			// post the request
			OutputStream out = conn.getOutputStream();
			out.write(bytes);
			out.close();
			PwLog.d(TAG, "test::5");

			// handle the response
			int status = conn.getResponseCode();
			PwLog.d(TAG, "test::Posting Status Response is " + status);

			if (status != 200 && status != 204) {
				throw new IOException("Post failed with error code " + status);
			}

		} finally {
			PwLog.d(TAG, "test::6");
			if (conn != null) {
				conn.disconnect();
			}
		}
		PwLog.d(TAG, "test::7");
	}

	/**
	 * Issue a GET request to the server and get the response
	 * 
	 * @param endpoint
	 *            Host url
	 * @param body
	 *            as a JSON string.
	 * @return GET response
	 * @throws IOException
	 * @throws DecryptionException
	 */
	public static String get(String endpoint, String body) throws IOException,
			DecryptionException {

		if (endpoint == null) {
			throw new IllegalArgumentException(
					"Endpoint cannot be null in GET Request");
		}

		String result = "";
		HttpClient httpclient = new DefaultHttpClient();
		String authHeader = getAuthenticationHeader(HTTP_TYPE_POST,body);
		HttpGet httpGet = new HttpGet(endpoint);
		httpGet.setHeader("X-API-Auth", authHeader);
		httpGet.setHeader("Content-Type", "application/json");

		HttpResponse response = httpclient.execute(httpGet);
		int resStatus = response.getStatusLine().getStatusCode();

		if (resStatus != 200 && resStatus != 204) {
			throw new IOException("Get failed with error code " + resStatus);
		}

		HttpEntity entity = response.getEntity();
		InputStream is = entity.getContent();

		// convert stream to string
		BufferedReader reader = new BufferedReader(new InputStreamReader(is,
				"iso-8859-1"), 8);

		StringBuilder sb = new StringBuilder();
		String line = null;

		while ((line = reader.readLine()) != null) {
			sb.append(line + "\n");
		}

		is.close();
		result = sb.toString();

		// Handle decrypt data
		String contentType = entity.getContentType().getName();
		if (contentType.equals("application/json-encrypted")) {
			String decryptedString = new Cryptor().decrypt(result,
					getEncryptionKey());
			if (decryptedString == null) {
				throw new DecryptionException("Failed to decrypt response.");
			} else {
				result = decryptedString;
			}
		}

		return result;
	}

	public static String get(String url) throws IOException {

		if (url == null) {
			return null;
		}

		String result = "";
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		HttpResponse response = httpclient.execute(httpGet);

		int resStatus = response.getStatusLine().getStatusCode();

		if (resStatus != 200 && resStatus != 204) {
			throw new IOException("Get failed with error code " + resStatus);
		}

		HttpEntity entity = response.getEntity();
		InputStream is = entity.getContent();

		// convert stream to string
		BufferedReader reader = new BufferedReader(new InputStreamReader(is,
				"iso-8859-1"), 8);

		StringBuilder sb = new StringBuilder();
		String line = null;

		while ((line = reader.readLine()) != null) {
			sb.append(line + "\n");
		}

		is.close();
		result = sb.toString();

		PwLog.d("PHUNWARE_CORE", "HTTP Get url: " + url);
		PwLog.d("PHUNWARE_CORE", "HTTPGet Response string: " + result);

		return result;
	}

	/**
	 * Build an authentication header string
	 * 
	 * @param httpMethod uppercase HTTP request method (e.g. GET, POST)
	 * @param body
	 * @return
	 */
	private static String getAuthenticationHeader(String httpMethod, String body) {
		String authHeader = "";
		String time = getTimeStamp();
		String signature;

		try {
			signature = getDigitalSignature(httpMethod, time, body);
			authHeader = getAccessKey() + ":" + time + ":" + signature;

		} catch (SignatureException e) {
			e.printStackTrace();
		}
		return authHeader;
	}

	/**
	 * Get the digital signature to be a part of the authentication header.
	 * 
	 * @param httpMethod uppercase HTTP request method (e.g. GET, POST)
	 * @param timeStamp
	 *            use {@link ServerUtilities#getTimeStamp()}
	 * @param requestBody
	 * @return digital signature
	 * @throws SignatureException
	 */
	public static String getDigitalSignature(String httpMethod, String timeStamp,
			String requestBody) throws SignatureException {

		String signatureString = getSignatureString(httpMethod, timeStamp, requestBody);

		byte[] signatureBytes = getSignatureKey() != null ? getSignatureKey()
				.getBytes() : null;

		try {
			Key sk = new SecretKeySpec(signatureBytes, HASH_ALGORITHM);
			Mac mac = Mac.getInstance(sk.getAlgorithm());
			mac.init(sk);
			final byte[] hmac = mac.doFinal(signatureString.getBytes());
			return toHexString(hmac);

		} catch (NoSuchAlgorithmException e1) {
			// throw an exception or pick a different encryption method
			throw new SignatureException(
					"error building signature, no such algorithm in device "
							+ HASH_ALGORITHM);

		} catch (InvalidKeyException e) {
			throw new SignatureException(
					"error building signature, invalid key " + HASH_ALGORITHM);
		}
	}

	/**
	 * Get the current time formatted as yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
	 * 
	 * @return
	 */
	private static String getTimeStamp() {
		long now = System.currentTimeMillis();
		TimeZone utc = TimeZone.getTimeZone("UTC");
		GregorianCalendar cal = new GregorianCalendar(utc);
		cal.setTimeInMillis(now);

		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
		formatter.setTimeZone(utc);
		return formatter.format(cal.getTime());
	}

	/**
	 * Get the access key from the session instance
	 * 
	 * @return access key
	 */
	private static String getAccessKey() {
		String key = PwCoreSession.getInstance().getAccessKey();
		return key == null ? "" : key;
	}

	/**
	 * Get the signature key from the session instance
	 * 
	 * @return signature key
	 */
	private static String getSignatureKey() {
		String key = PwCoreSession.getInstance().getSignatureKey();
		return key == null ? "" : key;
	}

	/**
	 * Get the encryption key from the session instance
	 * 
	 * @return encryption key
	 */
	private static String getEncryptionKey() {
		String key = PwCoreSession.getInstance().getEncryptionKey();
		return key == null ? "" : key;
	}

	/**
	 * Get a signature hash string
	 * 
	 * @param httpMethod uppercase HTTP request method (e.g. GET, POST)
	 * @param timeStamp
	 *            use {@link ServerUtilities#getTimeStamp()}
	 * @param jsonParamString JSON-encoded parameter string
	 * @return a hashed signature string
	 */
	private static String getSignatureString(String httpMethod, String timeStamp,
			String jsonParamString) {
		return httpMethod + "&" + getAccessKey() + "&" + timeStamp + "&" + jsonParamString;
	}

	/**
	 * Convert a byte array to a hex string.
	 * 
	 * @param bytes
	 *            array to convert to a string
	 * @return a hex string
	 */
	private static String toHexString(byte[] bytes) {
		StringBuilder sb = new StringBuilder(bytes.length * 2);

		Formatter formatter = new Formatter(sb);
		for (byte b : bytes) {
			formatter.format("%02x", b);
		}

		String returnMe = sb.toString();
		formatter.close();

		return returnMe;
	}
}
