package com.irvinflores.tarea2_4.Application.Encoders;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class Base64IEncoder implements IEncoder {
    @Override
    public String encode(String path) {

        if(path.isEmpty()){
            return "";
        }

        Bitmap bitmap = BitmapFactory.decodeFile(path);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);

    }

    public boolean canEncode(String path) {

        File file = new File(path);
        return file.exists() && file.isFile() && path.toLowerCase().endsWith(".jpg");
    }
}