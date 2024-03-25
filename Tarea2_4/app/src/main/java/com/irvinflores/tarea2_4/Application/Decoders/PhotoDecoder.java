package com.irvinflores.tarea2_4.Application.Decoders;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.irvinflores.tarea2_4.Application.Helpers.Tuple;

public class PhotoDecoder {

    public Tuple<String, Bitmap> GetImage(byte[] image){
        byte[] byteArray = Base64.decode(image, Base64.DEFAULT);

        String base64String = Base64.encodeToString(byteArray, Base64.DEFAULT);

        Bitmap imageBase64 = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        return new Tuple<String, Bitmap>(base64String,imageBase64 );
    }

    public  Bitmap DecodeBase64ToBitmap(String base64String) {
        byte[] decodedBytes = Base64.decode(base64String, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    public String DecodeBytesToBase64(byte[] image){
        byte[] byteArray = Base64.decode(image, Base64.DEFAULT);

        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }


    public Bitmap DecodeImage(byte[] image){
        byte[] byteArray = Base64.decode(image, Base64.DEFAULT);

        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }
}
