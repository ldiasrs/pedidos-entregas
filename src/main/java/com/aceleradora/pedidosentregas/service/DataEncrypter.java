package com.aceleradora.pedidosentregas.service;

public interface DataEncrypter<T> {
    T encrypt(T data);

    T decrypt(T data);
}
