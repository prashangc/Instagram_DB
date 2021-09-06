package com.example.db_successful;

public class yourPhotos {
    private int id;
    private String name;
    private byte[] image;

    public yourPhotos(String name, byte[] image, int id) {
        this.name = name;
        this.image = image;
        this.id = id;
    }

//    public yourPhotos(byte[] image, int id) {
//
//        this.image = image;
//        this.id = id;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}