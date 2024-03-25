package com.irvinflores.tarea2_4.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.util.Base64;


import com.irvinflores.tarea2_4.Domain.Photograph;

import java.util.ArrayList;


public class PhotographRepo extends DataIgniter {

    private Context context;


    public PhotographRepo(Context c) {
        super(c);
        context = c;
    }

    public long Add(Photograph contact) {
        long id = 0;
        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("description", contact.getDescription());
            values.put("image",contact.getImageBase64());

            id = db.insert(TABLE, null, values);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return id;
    }

    public ArrayList<Photograph> Get() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Photograph> contactList = new ArrayList<>();

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT * FROM ").append(TABLE).append(" ORDER BY description ASC");
        String queryString = queryBuilder.toString();

        Cursor dataCursor = db.rawQuery(queryString, null);

        boolean hasData = dataCursor.moveToFirst();

        if (hasData) {
            do {
                byte[] image = dataCursor.getBlob(2);
                int id = dataCursor.getInt(0);
                String description = dataCursor.getString(1);

                byte[] byteArray = Base64.decode(image, Base64.DEFAULT);

                String base64String = Base64.encodeToString(byteArray, Base64.DEFAULT);

                Photograph contact = new Photograph.Builder()
                        .setId(id)
                        .setDescription(description)
                        .setImageBase64(base64String)
                        .build();


                contactList.add(contact);
            } while (dataCursor.moveToNext());
        }
        dataCursor.close();
        return contactList;
    }



    public Photograph GetBy(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Photograph contact = null;
        Cursor dataCursor = db.rawQuery("SELECT * FROM " + TABLE + " WHERE id = " + id + " LIMIT 1", null);
        if (dataCursor.moveToFirst()) {
             contact = new Photograph.Builder()
                    .setId(dataCursor.getInt(0))
                    .setDescription(dataCursor.getString(1))
                    .setImageBase64(dataCursor.getString(2))
                    .build();

        }
        dataCursor.close();
        return contact;
    }

    public String GetUriById(int id) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor dataCursor = db.rawQuery("SELECT * FROM " + TABLE + " WHERE id = " + id + " LIMIT 1", null);
        String uri = "";
        if (dataCursor.moveToFirst()) {

            uri = dataCursor.getString(5);

        }
        dataCursor.close();
        return uri;
    }

    public boolean Update(Photograph contact) {
        SQLiteDatabase db = getWritableDatabase();
        boolean ok;
        try {
            ContentValues values = new ContentValues();
            values.put("description", contact.getDescription());
            values.put("image",contact.getImageBase64());
            int rowsAffected = db.update(TABLE, values, "id = ?", new String[]{String.valueOf(contact.getId())});
            ok = rowsAffected > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            ok = false;
        }
        return ok;
    }

    public boolean DeleteBy(int id) {
        SQLiteDatabase db = getWritableDatabase();
        boolean ok;
        try {
            int rowsAffected = db.delete(TABLE, "id = ?", new String[]{String.valueOf(id)});
            ok = rowsAffected > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            ok = false;
        }
        return ok;
    }
}