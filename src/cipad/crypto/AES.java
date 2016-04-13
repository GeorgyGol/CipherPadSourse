package cipad.crypto;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import cipad.Cipherpad;

public class AES {
	//public AES(String strKey, )
		final static public String cstrAES="AES"; // 128
		final static public String cstrDES="DES"; // 56
		final static public String cstrDESede="DESede"; // 168
		final static public String cstrBlowfish="Blowfish"; // 64
		
	public enum constCipher{

		AES(cstrAES, 128),
		DES(cstrDES, 56),
		DESede(cstrDESede, 168),
		Blowfish(cstrBlowfish, 64); 
		
		private int lType;
		private int lKeySize;
		
		private constCipher(String strMeth, int lSize){
			switch(strMeth){
				//case cstrAES: lType=1; lSize=128;
				//	break;
				case cstrDES: lType=2; lKeySize=lSize;
					break;
				case cstrDESede: lType=3; lKeySize=lSize;
					break;
				case cstrBlowfish: lType=4; lKeySize=lSize;
					break;
				default: lType=1; lKeySize=128;
			}
		}
		
		public String getMethodString()
		{ 
			switch(lType){
				case 2: return cstrDES;
				case 3:  return cstrDESede;
				case 4: return cstrBlowfish;
				default: return cstrAES;
			}
			 
		}
		public int getKeySize(){ return lKeySize; }
	}
	
	private constCipher ccType;
	private byte[] bSeedKey;
	
	
	public AES(String strKey, constCipher aes) {
		bSeedKey = strKey.getBytes();
		ccType=aes;
	}

	private byte[] getRawKey(byte[] password) throws NoSuchAlgorithmException {
        KeyGenerator kgen = KeyGenerator.getInstance(ccType.getMethodString());
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        sr.setSeed(password);
        kgen.init(ccType.getKeySize(), sr); 
        SecretKey skey = kgen.generateKey();
        return skey.getEncoded();
    }
	
	
	
	public String encrypt(String value) throws AesException {
		try {
            byte[] rawKey = getRawKey(bSeedKey);
            return toHex(encrypt(rawKey, value.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            throw new AesException("AES encrypt error", e);
        } catch (NoSuchPaddingException e) {
            throw new AesException("AES encrypt error", e);
        } catch (InvalidKeyException e) {
            throw new AesException("AES encrypt error", e);
        } catch (IllegalBlockSizeException e) {
            throw new AesException("AES encrypt error", e);
        } catch (BadPaddingException e) {
            throw new AesException("AES encrypt error", e);
        }
	}

	private byte[] encrypt(byte[] raw, byte[] clear)
            throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, ccType.getMethodString());
        Cipher cipher = Cipher.getInstance(ccType.getMethodString());
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        Cipherpad.PrintDebug(cipher.getAlgorithm());
        return cipher.doFinal(clear);
    }
	
	public String decrypt(String encrypted) throws AesException {
		byte[] rawKey = new byte[0];
        try {
            rawKey = getRawKey(bSeedKey);
            byte[] enc = toByte(encrypted);
            byte[] result = new byte[0];
            result = decrypt(rawKey, enc);
            return new String(result);
        } catch (NoSuchAlgorithmException e) {
            throw new AesException("AES decrypt error", e);
        } catch (NoSuchPaddingException e) {
            throw new AesException("AES decrypt error", e);
        } catch (InvalidKeyException e) {
            throw new AesException("AES decrypt error", e);
        } catch (IllegalBlockSizeException e) {
            throw new AesException("AES decrypt error", e);
        } catch (BadPaddingException e) {
            throw new AesException("AES decrypt error", e);
        }
	}
	
	
	private byte[] decrypt(byte[] raw, byte[] encrypted)
            throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        //SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        //Cipher cipher = Cipher.getInstance("AES");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, ccType.getMethodString());
        Cipher cipher = Cipher.getInstance(ccType.getMethodString());
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] decrypted = cipher.doFinal(encrypted);
        return decrypted;
    }
	
	 public static String toHex(byte [] buffer) {
	        return DatatypeConverter.printBase64Binary(buffer);
	    }

	    public static byte[] toByte(String hex) {
	        return DatatypeConverter.parseBase64Binary(hex);
	    }
}
