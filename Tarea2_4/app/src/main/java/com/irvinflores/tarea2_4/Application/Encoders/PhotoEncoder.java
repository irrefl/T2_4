package com.irvinflores.tarea2_4.Application.Encoders;

public class PhotoEncoder {
    private IEncoderFactory IEncoderFactory;

    public PhotoEncoder(IEncoderFactory IEncoderFactory) {
        this.IEncoderFactory = IEncoderFactory;
    }

    public String encodeImage(String path) {
        IEncoder IEncoder = IEncoderFactory.createEncoder();
        return IEncoder.encode(path);
    }
}