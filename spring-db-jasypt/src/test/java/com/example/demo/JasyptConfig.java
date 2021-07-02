package com.example.demo;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.hibernate5.encryptor.HibernatePBEStringEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JasyptConfig {


  @Bean
  public PooledPBEStringEncryptor stringEncryptor() {
    PooledPBEStringEncryptor stringEncryptor = new PooledPBEStringEncryptor();
    stringEncryptor.setAlgorithm("PBEWithMD5AndTripleDES");
    stringEncryptor.setPassword(("secret#01"));
    stringEncryptor.setPoolSize(4);
    return stringEncryptor;
  }

  @Bean
  public HibernatePBEStringEncryptor hibernateStringEncryptor(PooledPBEStringEncryptor stringEncryptor) {
    HibernatePBEStringEncryptor hibernateStringEncryptor = new HibernatePBEStringEncryptor();
    hibernateStringEncryptor.setRegisteredName("hibernateStringEncryptor");
    hibernateStringEncryptor.setEncryptor(stringEncryptor);
    return hibernateStringEncryptor;
  }

}
