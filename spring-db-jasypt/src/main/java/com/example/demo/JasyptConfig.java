package com.example.demo;

import org.jasypt.encryption.pbe.PBEStringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.hibernate5.encryptor.HibernatePBEStringEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JasyptConfig {


  @Bean
  public PBEStringEncryptor stringEncryptor() {
    PooledPBEStringEncryptor pooledPBEStringEncryptor = new PooledPBEStringEncryptor();
    pooledPBEStringEncryptor.setAlgorithm("PBEWithMD5AndTripleDES");
    pooledPBEStringEncryptor.setPassword(("secret#01"));
    pooledPBEStringEncryptor.setPoolSize(4);
    CustomPBStringEncryptor customPBStringEncryptor = new CustomPBStringEncryptor(pooledPBEStringEncryptor);
    return customPBStringEncryptor;
  }

  @Bean
  public HibernatePBEStringEncryptor hibernateStringEncryptor(PBEStringEncryptor stringEncryptor) {
    HibernatePBEStringEncryptor hibernateStringEncryptor = new HibernatePBEStringEncryptor();
    hibernateStringEncryptor.setRegisteredName("hibernateStringEncryptor");
    hibernateStringEncryptor.setEncryptor(stringEncryptor);
    return hibernateStringEncryptor;
  }

}
