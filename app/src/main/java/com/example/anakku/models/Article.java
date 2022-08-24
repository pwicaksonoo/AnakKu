package com.example.anakku.models;

import com.google.firebase.firestore.DocumentId;

import java.util.ArrayList;

public class Article {
    @DocumentId
    private String documentId;

    private String judul;
    private String image;
    private String description;

    public Article() {}

    public Article(String judul, String image, String description, ArrayList<Discussion> discussions) {
        this.judul = judul;
        this.image = image;
        this.description = description;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
