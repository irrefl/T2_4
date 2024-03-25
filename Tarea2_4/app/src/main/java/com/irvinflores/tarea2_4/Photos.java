package com.irvinflores.tarea2_4;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import android.widget.SearchView;

import com.irvinflores.tarea2_4.Application.ContactListAdapter;
import com.irvinflores.tarea2_4.Database.PhotographRepo;
import com.irvinflores.tarea2_4.Domain.Photograph;

import java.util.ArrayList;

public class Photos extends AppCompatActivity implements SearchView.OnQueryTextListener{

    SearchView search_view;
    ArrayList<Photograph> contactsArray;
    RecyclerView contactList;
    PhotographRepo photographRepo;
    ContactListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle("Lista de contactos");
        }

        contactList = findViewById(R.id.contactListView);
        search_view= findViewById(R.id.search_view);

        photographRepo = new PhotographRepo(this);

        contactsArray = photographRepo.Get();

        contactList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ContactListAdapter(contactsArray);
        contactList.setAdapter(adapter);

        search_view.setOnQueryTextListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.SearchImageFilter(newText);
        return false;
    }
}