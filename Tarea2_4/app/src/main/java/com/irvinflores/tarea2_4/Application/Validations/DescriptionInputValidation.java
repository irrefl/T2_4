package com.irvinflores.tarea2_4.Application.Validations;

import android.content.Context;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.function.Predicate;
import java.util.regex.Pattern;

public class DescriptionInputValidation extends AppCompatActivity {

    TextInputEditText DescriptionText;
    Context _context;

    public DescriptionInputValidation(TextInputEditText descriptionText, Context context) {
        DescriptionText = descriptionText;
        _context = context;
    }

    public boolean ValidateDescription(String description){
        Predicate<String> descriptionValidator = input -> input.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+");

        return !descriptionValidator.test(description);
    }

    public void ShowDescriptionError(){
        String input = DescriptionText.getText().toString().trim();
        DescriptionText.setText(input);
        DescriptionText.setError("Description inválido. Debe contener solo letras y espacios.");
        DescriptionText.setSelection(input.length());
        DescriptionText.requestFocus();
    }

    public  void ShowImageError(){
        Toast.makeText(_context, "Firma invalida, por favor firme arriba.",
                Toast.LENGTH_SHORT).show();
    }

    public boolean ValidateBase64(String imageBase64) {

        Predicate<String> firmaBase64Validator = input -> Pattern.matches("^[a-zA-Z0-9+/]*={0,2}$", input);

        boolean isBase54 = imageBase64.isEmpty();

        return isBase54;
    }

}
