package com.aceleradora.pedidosentregas.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class StringDataEncrypter implements DataEncrypter<String> {

    private final TextEncryptor textEncryptor;

    public StringDataEncrypter(@Value("${security.data.encrypt.password}") String encryptionPassword,
                               @Value("${security.data.encrypt.salt}") String encryptionSalt) {
       if (StringUtils.isEmpty(encryptionPassword) || StringUtils.isEmpty(encryptionSalt)) {
           throw new RuntimeException("ENV variables should be defined: DATA_ENCRYPT_PASSWORD, DATA_ENCRYPT_SALT");
       }
        textEncryptor =
                Encryptors.queryableText(encryptionPassword, encryptionSalt);
    }

    @Override
    public String encrypt(String data) {
        return textEncryptor.encrypt(data);
    }

    @Override
    public String decrypt(String encryptedData) {
        return textEncryptor.decrypt(encryptedData);
    }

}
