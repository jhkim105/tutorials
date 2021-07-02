package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.pbe.PBEStringCleanablePasswordEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;

/**
 * 평문 복호화시 예외 발생하지 않도록
 * 기존 시스템 데이터 변경없이, 암호화를 추가하는 경우에 필요하다.
 */
@Slf4j
public class CustomPBStringEncryptor implements PBEStringCleanablePasswordEncryptor {

  private final PooledPBEStringEncryptor pooledPBEStringEncryptor;

  public CustomPBStringEncryptor(PooledPBEStringEncryptor pooledPBEStringEncryptor) {
    this.pooledPBEStringEncryptor = pooledPBEStringEncryptor;
  }


  @Override
  public String encrypt(String message) {
    return pooledPBEStringEncryptor.encrypt(message);
  }

  @Override
  public String decrypt(String encryptedMessage) {
    String ret;
    try {
      ret = pooledPBEStringEncryptor.decrypt(encryptedMessage);
    } catch(Exception ex) {
      //ignored
      log.debug("{}", ex.toString());
      ret = encryptedMessage;
    }
    return ret;
  }

  @Override
  public void setPasswordCharArray(char[] password) {
    pooledPBEStringEncryptor.setPasswordCharArray(password);
  }

  @Override
  public void setPassword(String password) {
    pooledPBEStringEncryptor.setPassword(password);
  }
}
