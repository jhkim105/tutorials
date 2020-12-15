package com.example.demo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.codec.Utf8;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
@Slf4j
public class PasswordEncoderTest {

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Test
  void encode() {
    log.debug("{}", passwordEncoder.encode("111111"));
  }

  @Test
  void sha256() {
    String password = "111111";
    MessageDigestPasswordEncoder messageDigestPasswordEncoder = new MessageDigestPasswordEncoder("SHA-256");
    String encoded = messageDigestPasswordEncoder.encode(password);
    String encodedNs = sha256NotSalted(password);
    log.debug(encoded);
    log.debug(encodedNs);
  }


  private String sha256NotSalted(String plainText) {
    MessageDigest messageDigest = null;
    try {
      messageDigest = MessageDigest.getInstance("SHA-256");
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    byte[] digest = messageDigest.digest(Utf8.encode(plainText));
    return new String(Hex.encode(digest));
  }

}
