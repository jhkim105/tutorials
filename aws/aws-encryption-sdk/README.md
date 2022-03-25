AWS Encryption SDK Examples
============================


## Dependencies
```xml
<dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>software.amazon.awssdk</groupId>
      <artifactId>bom</artifactId>
      <version>2.17.153</version>
      <type>pom</type>
      <scope>import</scope>
    </dependency>
  </dependencies>
</dependencyManagement>
```
```xml
    <!-- https://mvnrepository.com/artifact/com.amazonaws/aws-encryption-sdk-java -->
    <dependency>
      <groupId>com.amazonaws</groupId>
      <artifactId>aws-encryption-sdk-java</artifactId>
      <version>2.4.0</version>
    </dependency>
    <!-- https://mvnrepository.com/artifact/software.amazon.awssdk/sdk-core -->
    <dependency>
      <groupId>software.amazon.awssdk</groupId>
      <artifactId>sdk-core</artifactId>
    </dependency>
    <!-- https://mvnrepository.com/artifact/software.amazon.awssdk/regions -->
    <dependency>
      <groupId>software.amazon.awssdk</groupId>
      <artifactId>regions</artifactId>
    </dependency>
    <!-- https://mvnrepository.com/artifact/software.amazon.awssdk/kms -->
    <dependency>
      <groupId>software.amazon.awssdk</groupId>
      <artifactId>kms</artifactId>
    </dependency>
```

## KmsMasterKey
```java
@Configuration
public class CryptoConfig {
  
  @Bean
  public KmsMasterKeyProvider masterKeyProvider(@Lazy KmsProperties kmsProperties) {
    return KmsMasterKeyProvider.builder()
        .buildStrict(kmsProperties.getKeyArn());
  }

  @Bean
  public AwsCrypto awsCrypto() {
    return AwsCrypto.builder()
        .withCommitmentPolicy(CommitmentPolicy.RequireEncryptRequireDecrypt)
        .build();
  }

}
```

```java
  public byte[] encryptBytes(byte[] bytesToDecrypt) {
    final CryptoResult<byte[], KmsMasterKey> encryptResult = awsCrypto.encryptData(masterKeyProvider,
        bytesToDecrypt);
    return encryptResult.getResult();
  }

  public byte[] decryptBytes(byte[] bytesToEncrypt) {
    CryptoResult<byte[], KmsMasterKey> decryptResult = awsCrypto.decryptData(masterKeyProvider, bytesToEncrypt);
    return decryptResult.getResult();
  }
```

01011112222 문자열 암호화 결과:
```text
AgV4zh+J9lJO/ZxTUO7vJsTID255IlMIq9C8GdlBIOEZZ/EAXwABABVhd3MtY3J5cHRvLXB1YmxpYy1rZXkAREFpS1N1VFVIcWFJYmgwRjgxcTQ4WkgwM0txZURDdlQyU0MzekNHdjZVZTJ3YVlYTW93VHpOa0ZkdlJoeFdGS1dHUT09AAEAB2F3cy1rbXMAUGFybjphd3M6a21zOmFwLW5vcnRoZWFzdC0xOjkxOTc2NzIyOTIwMDprZXkvMDhlOTdlNGMtMzA3Yi00N2Q2LWJhNjQtMTgyNDBkMzc5MTE0ALgBAgEAeMFuTmHyY5i42WyecbPw1x4nfmYomNRZ6Ts8wbb2dchBAb5zJLOVGRKTQJTzaKuruXUAAAB+MHwGCSqGSIb3DQEHBqBvMG0CAQAwaAYJKoZIhvcNAQcBMB4GCWCGSAFlAwQBLjARBAx/Y6+iAFqA0z16DNwCARCAO9NLMqruR6mZZbcaObnoBlfditj7RIAiTndtnhEhLY/x+TYpCOcq7eKNh7BdwQss9Gygn8SqG49x5x6fAgAAEAANQ4qsLn3rzmCEHwQt+QvF/0UyA7bz0FzqW/750RloboTeQR7d7v0Cjj9KQdL31Gv/////AAAAAQAAAAAAAAAAAAAAAQAAAAuCTAufwf3YTESx3i0jjYfrm85SFR93U0A7pAoAZzBlAjBXWPyIH+6T7TE40n0LAxcpYQrViyeZhQsjdvzyFUHBEZg/RJ673KALHFItPJlQcP4CMQD/kvaaJWLfw1PHVJBABzdgUu09anAF1+2ZJMzsyFZrBT2zFmDYjEHzviGzhXj3GGI=
```
사이즈가 크다.
## JceMasterKey

## Multiple Region Key


## Refs
https://docs.aws.amazon.com/encryption-sdk/latest/developer-guide/introduction.html


