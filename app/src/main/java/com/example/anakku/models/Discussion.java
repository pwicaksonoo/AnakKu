package com.example.anakku.models;

import com.google.firebase.firestore.DocumentId;

import java.util.Date;

public class Discussion {
    @DocumentId
    private String documentId;

    private String userDocumentId;
    private String text;
    private Date created;

    public Discussion() {}

    public Discussion(String userDocumentId, String text, Date created) {
        this.userDocumentId = userDocumentId;
        this.text = text;
        this.created = created;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getUserDocumentId() {
        return userDocumentId;
    }

    public void setUserDocumentId(String userDocumentId) {
        this.userDocumentId = userDocumentId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
