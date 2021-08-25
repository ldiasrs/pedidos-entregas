package com.aceleradora.pedidosentregas.service;


import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

import static org.assertj.core.api.Assertions.assertThat;

public class StringDataEncrypterTest {

    private static final String PASSWORD = "a1a1a1a1";
    private static final String SALT = "b1b1b1";

    @Test
    public void shouldEncodeWithSpringCrypt() {
        String personName = "Leo Draw . IO";
        StringDataEncrypter stringEncoder = new StringDataEncrypter(PASSWORD, SALT);
        String encryptValue = stringEncoder.encrypt(personName);

        TextEncryptor textEncryptor = Encryptors.queryableText(PASSWORD, SALT);
        String rightEncryptValue = textEncryptor.encrypt(personName);

        assertThat(encryptValue).isEqualTo(rightEncryptValue);
    }

    @Test
    public void shouldDecodeWithSuccess() {
        String personName = "Leo Draw . IO";
        StringDataEncrypter stringEncoder = new StringDataEncrypter(PASSWORD, SALT);
        String encryptValue = stringEncoder.decrypt(stringEncoder.encrypt(personName));

        assertThat(encryptValue).isEqualTo(personName);
    }
}
