package com.irvinflores.tarea2_4.Application.Encoders;

public class Base64IEncoderFactory implements IEncoderFactory {
    @Override
    public IEncoder createEncoder() {
        return new Base64IEncoder();
    }
}