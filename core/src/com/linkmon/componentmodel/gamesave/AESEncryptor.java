package com.linkmon.componentmodel.gamesave;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.badlogic.gdx.Gdx;

public class AESEncryptor implements ISaveEncryption {
	
	private final String key = "0000000000000000";
									 
	// Need to work out how to store keys. Maybe just build one off of device specific data?
	// MAC address, game files hash, something to make it unique.
	// Build a new key each time from MAC address seems like a good one.

	// Need more learnings. Maybe a checksum would be better? Both?

	@Override
	public byte[] encrypt(String data) {
		// TODO Auto-generated method stub
		Gdx.app.log("ENCRYPT SAVE GAME", data);
		return doCrypto(Cipher.ENCRYPT_MODE, key, data);
	}

	@Override
	public byte[] decrypt(byte[] data) {
		// TODO Auto-generated method stub
		return deCrypto(Cipher.DECRYPT_MODE, key, data);
	}
	
	private byte[] deCrypto(int encryptMode, String key, byte[] data) {
		
		byte[] outputBytes = new byte[1];
		
			Key secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
	        Cipher cipher = null;
			try {
				cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        try {
				cipher.init(encryptMode, secretKey);
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        Gdx.app.log("DOCRYPTO SAVE GAME", "INPUT LENGTH " +data.length);
	        
	        try {
				outputBytes = cipher.doFinal(data);
			} catch (IllegalBlockSizeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BadPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        Gdx.app.log("DOCRYPTO SAVE GAME", "OUTPUT LENGTH " + outputBytes.length);
		return outputBytes;
	}
	
	private byte[] doCrypto(int encryptMode, String key, String data) {
		
		byte[] outputBytes = new byte[1];
		
			Key secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
	        Cipher cipher = null;
			try {
				cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        try {
				cipher.init(encryptMode, secretKey);
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        byte[] inputBytes = data.getBytes(StandardCharsets.UTF_8);
	        Gdx.app.log("DOCRYPTO SAVE GAME", "INPUT LENGTH " +inputBytes.length);
	        
	        try {
				outputBytes = cipher.doFinal(inputBytes);
			} catch (IllegalBlockSizeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BadPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        Gdx.app.log("DOCRYPTO SAVE GAME", "OUTPUT LENGTH " +outputBytes.length);
		return outputBytes;
	}

}
