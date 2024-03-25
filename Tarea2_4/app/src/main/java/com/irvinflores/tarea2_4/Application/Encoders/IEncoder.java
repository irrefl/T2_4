package com.irvinflores.tarea2_4.Application.Encoders;

public interface IEncoder {
    String encode(String path);
    boolean canEncode(String path);
}