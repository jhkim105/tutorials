@TypeDef(
    name="encryptedString",
    typeClass= EncryptedStringType.class,
    parameters= {
        @Parameter(name="encryptorRegisteredName", value="hibernateStringEncryptor")
    }
)
package com.example.demo.user;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.TypeDef;
import org.jasypt.hibernate5.type.EncryptedStringType;

