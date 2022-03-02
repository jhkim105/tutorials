@TypeDef(
    name="encryptedString",
    typeClass= EncryptedStringType.class,
    parameters= {
        @Parameter(name="encryptorRegisteredName", value="hibernateStringEncryptor")
    }
)
package jhkim105.tutorials.spring.data.encrypt.jasypt;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.TypeDef;
import org.jasypt.hibernate5.type.EncryptedStringType;

