package com.phunware.core.internal;


public class CoreFactory {

	private static CoreFactory mInstance;
	private Cryptor mCryptor;

	CoreFactory() {

	}

	/**
	 * Get an instance of this {@link CoreFactory}.
	 * 
	 * @return A {@link CoreFactory} instance.
	 */
	public static CoreFactory getInstance() {
		if (mInstance == null) {
			mInstance = new CoreFactory();
		}
		return mInstance;
	}

	public static Cryptor createCryptor() {
		return mInstance.getCryptor();
	}

	public Cryptor getCryptor() {
		if (mCryptor != null) {
			return mCryptor;
		} else {
			return new Cryptor();
		}
	}

	public void setCryptor(Cryptor cryptor) {
		mCryptor = cryptor;
	}

}
