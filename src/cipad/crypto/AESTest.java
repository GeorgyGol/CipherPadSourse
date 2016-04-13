package cipad.crypto;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class AESTest {

	AES a;
	String b;
	String strTest="asdfasdfasd asdfasdfasd hdfgjhdfgh warwerwe cvzvds";
	@Before
	public void setUp() throws Exception {
		a=new AES("Testing key", AES.constCipher.AES);
		strTest="hello world";
	}

	@Test
	public void testEncrypt() {
		try {
			b=a.encrypt(strTest);
		} catch (AesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(b);
	}

	@Test
	public void testDecrypt() {
		try {
			
			assertEquals(strTest, a.decrypt(b));
		} catch (AesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
