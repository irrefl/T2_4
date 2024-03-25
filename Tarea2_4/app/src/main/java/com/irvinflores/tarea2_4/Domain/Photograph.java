package com.irvinflores.tarea2_4.Domain;

import android.graphics.Bitmap;

public class Photograph {

    private int Id;
    private String description;
    private String imageBase64;
    private Bitmap Image;

    private Photograph() {

    }


    public static class Builder {
        private int Id;
        private String description;
        private String imageBase64;

        private Bitmap Image;
        public Builder setImage(Bitmap image) {
            this.Image = image;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setImageBase64(String imageBase64) {
            this.imageBase64 = imageBase64;
            return this;
        }

        public Builder setId(int id) {
            this.Id = id;
            return this;
        }

        public Photograph build() {
            Photograph photo = new Photograph();
            photo.description = this.description;
            photo.imageBase64 = this.imageBase64;
            photo.Id = this.Id;
            photo.Image = this.Image;
            return photo;
        }
    }

    public Bitmap getImage (){return Image;}

    public int getId() {
        return Id;
    }
    public String getDescription() {
        return description;
    }

    public String getImageBase64() {
        return imageBase64;
    }
}
