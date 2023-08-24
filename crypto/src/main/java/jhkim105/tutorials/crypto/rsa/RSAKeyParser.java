package jhkim105.tutorials.crypto.rsa;

import java.io.FileReader;
import java.io.IOException;
import java.security.Key;
import java.security.Security;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;


public class RSAKeyParser {

  public static Key loadPrivateKey(String filePath) {
    Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

    try (PEMParser pemParser = new PEMParser(new FileReader(filePath))) {
      Object object = pemParser.readObject();
      if (object instanceof PEMKeyPair) { // PKCS#1
        return new JcaPEMKeyConverter().getKeyPair((PEMKeyPair) object).getPrivate();
      } else if (object instanceof PrivateKeyInfo) { // PKCS#8
        PrivateKeyInfo privateKeyInfo = (PrivateKeyInfo) object;
        return new JcaPEMKeyConverter().getPrivateKey(privateKeyInfo);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    throw new IllegalArgumentException("Invalid private key file format");
  }

  public static Key loadPublicKey(String filePath) {
    Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

    try (PEMParser pemParser = new PEMParser(new FileReader(filePath))) {
      Object object = pemParser.readObject();
      if (object instanceof SubjectPublicKeyInfo) {
        SubjectPublicKeyInfo subjectPublicKeyInfo = (SubjectPublicKeyInfo) object;
        return new JcaPEMKeyConverter().getPublicKey(subjectPublicKeyInfo);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    throw new IllegalArgumentException("Invalid public key file format");
  }

}