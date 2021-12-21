package jhkim105.tutorials.jmh;

import java.nio.charset.Charset;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;

public final class RsCrypto {

    private static final RsCrypto rsCrypto = new RsCrypto();
    private SecretKeySpec keySpec;
    private IvParameterSpec ivSpec;
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String KEY_STRING = "0123456789012345";
    private static final String IV_STRING = "0123456789012345";
    private static final String DEFAULT_CHAR_SET = "UTF-8";

    private RsCrypto() {
        generateKey();
    }

    public static RsCrypto getInstance() {
        return rsCrypto;
    }

    public String encrypt(String plainText) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, this.keySpec, this.ivSpec);
            byte[] plain = plainText.getBytes(DEFAULT_CHAR_SET);
            byte[] encrypt = cipher.doFinal(plain);
            return new String(Base64.encodeBase64(encrypt), DEFAULT_CHAR_SET);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String decrypt(String encryptText) {
        if (encryptText.length() <= 0)
            return "";
        try {
            if (StringUtils.contains(encryptText, " ")) {
                encryptText = StringUtils.replace(encryptText, " ", "+");
            }
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, this.keySpec, this.ivSpec);
            byte[] encrypt = Base64.decodeBase64(encryptText.getBytes(DEFAULT_CHAR_SET));
            byte[] plain = cipher.doFinal(encrypt);
            return new String(plain, DEFAULT_CHAR_SET);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void generateKey() {
        byte[] keyBytes = KEY_STRING.getBytes( Charset.forName(DEFAULT_CHAR_SET));
        byte[] ivBytes = IV_STRING.getBytes(Charset.forName(DEFAULT_CHAR_SET));
        byte[] key = new byte[16];
        byte[] iv = new byte[16];

        System.arraycopy(keyBytes, 0, key, 0, key.length);
        System.arraycopy(ivBytes, 0, iv, 0, iv.length);

        this.keySpec = new SecretKeySpec(key, 0, 16, "AES");
        this.ivSpec = new IvParameterSpec(iv);
    }

}
