package com.irvinflores.tarea2_4.Application.Validations;

import android.text.Editable;
import android.text.TextWatcher;

import com.google.android.material.textfield.TextInputEditText;

public class DescriptionTextWatcher implements TextWatcher {

    TextInputEditText descriptionText;
    final int LongName = "José Salvador Alvarenga Espinoza".length();

    public DescriptionTextWatcher(TextInputEditText nombre) {
        descriptionText = nombre;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String input = descriptionText.getText().toString().trim();

        boolean isLongName = input.length() > LongName;

        if (!input.matches("^[a-zA-ZáéíóúÁÉÍÓÚ\\s]+$")) {
            descriptionText.setError("Debe escribir un nombre válido.");
        } else if (isLongName) {
            descriptionText.setText(input.substring(0, LongName));
            descriptionText.setError("El nombre es demasiado largo, que el nombre mas largo registrado en Honduras.");
            descriptionText.setSelection(LongName);
        } else {
            descriptionText.setError(null);
        }
    }
}
