package com.irvinflores.tarea2_4;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.irvinflores.tarea2_4.Application.ContactListAdapter;
import com.irvinflores.tarea2_4.Database.PhotographRepo;
import com.irvinflores.tarea2_4.Domain.Photograph;


import java.util.ArrayList;

public class ContactManagerActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{



    FloatingActionButton editButton, deleteContactButton,
            share_image_fab, ver_image_fab, edit_contact_fab;
    ExtendedFloatingActionButton mAddFab;
    TextView addAlarmActionText, addPersonActionText;
    Boolean isAllFabsVisible;
    SearchView search_view;
    ArrayList<Photograph> contactsArray;
    RecyclerView contactList;

    PhotographRepo photographRepo;
    ContactListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_manager);



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

        mAddFab = findViewById(R.id.add_fab);
        // FAB button
        editButton = findViewById(R.id.edit_contact_fab);
        deleteContactButton = findViewById(R.id.delete_contact_fab);
        addAlarmActionText = findViewById(R.id.add_alarm_action_text);
        addPersonActionText = findViewById(R.id.add_person_action_text);
        share_image_fab = findViewById(R.id.share_image_fab);


        editButton.setVisibility(View.GONE);
        deleteContactButton.setVisibility(View.GONE);
        addAlarmActionText.setVisibility(View.GONE);
        addPersonActionText.setVisibility(View.GONE);

        findViewById(R.id.share_image_fab).setVisibility(View.GONE);
        findViewById(R.id.share_image_text).setVisibility(View.GONE);

        findViewById(R.id.ver_image_fab).setVisibility(View.GONE);
        findViewById(R.id.ver_imagen_action_text).setVisibility(View.GONE);

        photographRepo = new PhotographRepo(this);

        contactsArray = photographRepo.Get();

        contactList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ContactListAdapter(contactsArray);
        contactList.setAdapter(adapter);



        isAllFabsVisible = false;

        mAddFab.shrink();

        mAddFab.setOnClickListener(
                view -> {
                    if (!isAllFabsVisible) {

                        editButton.show();
                        deleteContactButton.show();
                        addAlarmActionText.setVisibility(View.VISIBLE);
                        addPersonActionText.setVisibility(View.VISIBLE);

                        findViewById(R.id.share_image_fab).setVisibility(View.VISIBLE);
                        findViewById(R.id.share_image_text).setVisibility(View.VISIBLE);

                        findViewById(R.id.ver_image_fab).setVisibility(View.VISIBLE);
                        findViewById(R.id.ver_imagen_action_text).setVisibility(View.VISIBLE);

                        mAddFab.extend();

                        isAllFabsVisible = true;
                    } else {

                        editButton.hide();
                        deleteContactButton.hide();
                        addAlarmActionText.setVisibility(View.GONE);
                        addPersonActionText.setVisibility(View.GONE);

                        mAddFab.shrink();

                        findViewById(R.id.share_image_fab).setVisibility(View.GONE);
                        findViewById(R.id.share_image_text).setVisibility(View.GONE);

                        findViewById(R.id.ver_image_fab).setVisibility(View.GONE);
                        findViewById(R.id.ver_imagen_action_text).setVisibility(View.GONE);

                        isAllFabsVisible = false;
                    }
                });

        editButton.setOnClickListener(view->{
            Intent intent = new Intent(this, NewEditContact.class);

            intent.putExtra("ID", adapter.Id);
            startActivity(intent);
        });

        share_image_fab.setOnClickListener(
                view -> {

                    if (adapter.Id != -1) {
                        sharePic(adapter.Id);
                    } else {
                        Toast.makeText(getApplicationContext(), "Ninguna Contacto seleccionada", Toast.LENGTH_SHORT).show();
                    }
                });

        deleteContactButton.setOnClickListener(
                view -> {
                    if(adapter.Id==0){
                        Toast.makeText(this, "Seleciona algo primero", Toast.LENGTH_LONG).show();
                        return;
                    }

                    photographRepo.DeleteBy(adapter.Id  );
                    lista();
                    Toast.makeText(getApplicationContext(), "Contacto removido", Toast.LENGTH_SHORT).show();

                });

        search_view.setOnQueryTextListener(this);

    }

    private void sharePic(int id) {
        Photograph contactSelected = photographRepo.GetBy(id);

        String message = new StringBuilder()
                .append("description: ").append(contactSelected.getDescription()).append("\n")
                .toString();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Contact Info");
        intent.putExtra(Intent.EXTRA_TEXT, message);

        startActivity(Intent.createChooser(intent, "Share data"));
    }


    private void lista(){
        Intent intent = new Intent(this, ContactManagerActivity.class);
        startActivity(intent);
    }

    //back button
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