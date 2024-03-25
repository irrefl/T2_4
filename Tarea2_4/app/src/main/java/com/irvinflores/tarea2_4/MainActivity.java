package com.irvinflores.tarea2_4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import com.irvinflores.tarea2_4.Application.Encoders.Base64IEncoderFactory;
import com.irvinflores.tarea2_4.Application.Encoders.IEncoderFactory;
import com.irvinflores.tarea2_4.Application.Encoders.PhotoEncoder;
import com.irvinflores.tarea2_4.Application.Validations.DescriptionTextWatcher;
import com.irvinflores.tarea2_4.Application.Validations.DescriptionInputValidation;
import com.irvinflores.tarea2_4.Database.SignatureRepo;
import com.irvinflores.tarea2_4.Domain.Photograph;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private String currentPhotoPath;
    static final int peticion_camara = 100;
    static final int peticion_foto = 102;
    private com.google.android.material.imageview.ShapeableImageView avatar;
    private  com.google.android.material.button.MaterialButton addButton, photoListButton, cleanSignatureButton;
    com.google.android.material.textfield.TextInputEditText DescriptionText;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int seleccionar_foto = 103;

    IEncoderFactory base64IEncoderFactory;
    PhotoEncoder photoEncoder;
    private SignaturePad signaturePad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signaturePad =  findViewById(R.id.lienzo);
        DescriptionText = findViewById(R.id.Description);
        currentPhotoPath = "";
        DescriptionText.setText("");
         base64IEncoderFactory = new Base64IEncoderFactory();
         photoEncoder = new PhotoEncoder(base64IEncoderFactory);


        DescriptionText.addTextChangedListener(new DescriptionTextWatcher(DescriptionText) );

        cleanSignatureButton = findViewById(R.id.takePicture);
        cleanSignatureButton.setOnClickListener(v -> {
            signaturePad.limpiarFirma();
        });

        photoListButton  = findViewById(R.id.photoListButton);

        photoListButton.setOnClickListener(v -> {

            Intent intent = new Intent(this, Photos.class);
            startActivity(intent);
        });

        addButton = findViewById(R.id.submitButton);

        addButton.setOnClickListener(v -> {

            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Registrar Contacto");
            builder.setMessage("Desea registrar el contacto actual.?");

            builder.setPositiveButton("Aceptar", (di, i) ->  Add());
            builder.setNegativeButton("No", (d, i) -> limpiarDatos());
            builder.show();
        });

    }


    private void limpiarDatos() {
        DescriptionText.setText("");
        signaturePad.limpiarFirma();
    }

    private void Add(){


        Context myAppContext = getApplicationContext();

        String imageBase64 = signaturePad.convertirFirmaABase64();
        String description = String.valueOf(DescriptionText.getText());

        DescriptionInputValidation val = new DescriptionInputValidation(DescriptionText, myAppContext);

        boolean base64Empty = val.ValidateBase64(imageBase64);
        if(base64Empty){
            val.ShowImageError();
            return;
        }

        boolean isDescriptionNull = val.ValidateDescription( description);
        if(isDescriptionNull){
            val.ShowDescriptionError();

            return;
        }


        Photograph photo = new Photograph.Builder()
                .setDescription(description)
                .setImageBase64(imageBase64)
                .build();

        SignatureRepo dbPersonas = new SignatureRepo(myAppContext);
        long id = dbPersonas.Add(photo);

        if (id >= 0) {
            Toast.makeText(myAppContext, "Contacto guardado", Toast.LENGTH_LONG).show();
            limpiarDatos();

        } else {
            Toast.makeText(myAppContext, "Error guardando contacto", Toast.LENGTH_LONG).show();
        }

    }


    public static String getPath(Context context, Uri uri) {
        String result = null;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(projection[0]);
                result = cursor.getString(columnIndex);
            }
            cursor.close();
        }
        if (result == null) {
            result = "Not found";
        }
        return result;
    }



    private void savePicOnPhone(Bitmap bitmap) {
        String nombreImagen = "image-" + System.currentTimeMillis() ;
        String rutaImagen = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, nombreImagen, null);

        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        if (rutaImagen != null) {
            Intent intentScan = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uriImagen = Uri.parse(rutaImagen);

            currentPhotoPath = getPath(getApplicationContext(), uriImagen);
            intentScan.setData(uriImagen);
            sendBroadcast(intentScan);

            Toast.makeText(getApplicationContext(), "Image saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Error to save", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == peticion_camara){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                tomarfoto();
            }
        }else{
            Toast.makeText(getApplicationContext(), "Se necesitan permisos de acceso", Toast.LENGTH_LONG).show();
        }
    }


    private void Permisos() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},
                    peticion_camara);
        } else {
            tomarfoto();
        }
    }


    private void tomarfoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, peticion_foto);
        }
    }




}