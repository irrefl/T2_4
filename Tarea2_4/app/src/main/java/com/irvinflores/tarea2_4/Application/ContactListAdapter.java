package com.irvinflores.tarea2_4.Application;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.irvinflores.tarea2_4.Application.Decoders.PhotoDecoder;
import com.irvinflores.tarea2_4.Domain.Photograph;
import com.irvinflores.tarea2_4.NewEditContact;
import com.irvinflores.tarea2_4.R;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactViewHolder> {

    ArrayList<Photograph> _contactList;
    ArrayList<Photograph> original;
    public int Id;
    View selectedItem = null;
    private PhotoDecoder decoder;

    public ContactListAdapter(ArrayList<Photograph> contactList) {
        _contactList = contactList;
        original = new ArrayList<>();
        original.addAll(contactList);
        Id = 0;
        decoder = new PhotoDecoder();
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        ContactViewHolder card = new ContactViewHolder(view);
        return card;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Photograph photograph = _contactList.get(position);
        holder.viewDescription.setText(photograph.getDescription());


        Bitmap image = decoder.DecodeBase64ToBitmap(photograph.getImageBase64());

        holder.viewPhoto.setScaleType(com.google.android.material.imageview.ShapeableImageView.ScaleType.FIT_CENTER);
        holder.viewPhoto.setImageBitmap(image);

    }


    @Override
    public int getItemCount() {
        return _contactList.size();
    }
    public class ContactViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView viewDescription;
        ShapeableImageView viewPhoto;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);

            viewDescription = itemView.findViewById(R.id.descriptionTextView);
            viewPhoto = itemView.findViewById(R.id.photoImageView);

            itemView.setOnClickListener(view -> {
                Id = _contactList.get(getAdapterPosition()).getId();
                Context context = view.getContext();
                Intent intent = new Intent(context, NewEditContact.class);
                intent.putExtra("ID", Id);
                context.startActivity(intent);
            });

        }
    }

    public void SearchImageFilter(final String txtBuscar) {
        int longitud = txtBuscar.length();
        if (longitud == 0) {
            _contactList.clear();
            _contactList.addAll(original);
        } else {
            List<Photograph> collecion = _contactList.stream()
                    .filter(i -> i.getDescription().toLowerCase().contains(txtBuscar.toLowerCase()))
                    .collect(Collectors.toList());
            _contactList.clear();
            _contactList.addAll(collecion);
        }
        notifyDataSetChanged();
    }

}
