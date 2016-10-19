package se.skaro.teslbot.web.api.key;

import java.security.Key;
import java.util.Base64;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import se.skaro.teslbot.config.ExternalConfigComponents;

@Component
public class AESkeyEncrypt {
	
	@Autowired
	private ExternalConfigComponents config;
	
	private final String ALGO = "AES";
	private byte[] keyValue;
	
	@PostConstruct
	public void setKeyValue(){
		keyValue = config.getKeyValue().getBytes();
	}

	public String encrypt(String Data) throws Exception {
		Key key = generateKey();
		Cipher c = Cipher.getInstance(ALGO);
		c.init(Cipher.ENCRYPT_MODE, key);
		byte[] encVal = c.doFinal(Data.getBytes());
		String encryptedValue = Base64.getUrlEncoder().withoutPadding().encodeToString(encVal);
		return encryptedValue;
	}

	public String decrypt(String encryptedData) throws Exception {
		Key key = generateKey();
		Cipher c = Cipher.getInstance(ALGO);
		c.init(Cipher.DECRYPT_MODE, key);
		byte[] decordedValue = Base64.getUrlDecoder().decode(encryptedData);
		byte[] decValue = c.doFinal(decordedValue);
		String decryptedValue = new String(decValue);
		return decryptedValue;
	}

	private Key generateKey() throws Exception {
		Key key = new SecretKeySpec(keyValue, ALGO);
		return key;
	}

}
