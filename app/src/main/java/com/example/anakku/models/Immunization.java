package com.example.anakku.models;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.PropertyName;

import java.util.Map;

public class Immunization {
    @DocumentId
    private String documentId;
    private String childDocumentId;
    private Map<String, Boolean> category1;
    private Map<String, Boolean> category2;
    private Map<String, Boolean> category3;

    public Immunization() {
    }

    public Immunization(String childDocumentId) {
        this.childDocumentId = childDocumentId;
    }

    public Immunization(String documentId, String childDocumentId, Map<String, Boolean> category1, Map<String, Boolean> category2, Map<String, Boolean> category3) {
        this.documentId = documentId;
        this.childDocumentId = childDocumentId;
        this.category1 = category1;
        this.category2 = category2;
        this.category3 = category3;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getChildDocumentId() {
        return childDocumentId;
    }

    public void setChildDocumentId(String childDocumentId) {
        this.childDocumentId = childDocumentId;
    }

    public Map<String, Boolean> getCategory1() {
        return category1;
    }

    public void setCategory1(Map<String, Boolean> category1) {
        this.category1 = category1;
    }

    public Map<String, Boolean> getCategory2() {
        return category2;
    }

    public void setCategory2(Map<String, Boolean> category2) {
        this.category2 = category2;
    }

    public Map<String, Boolean> getCategory3() {
        return category3;
    }

    public void setCategory3(Map<String, Boolean> category3) {
        this.category3 = category3;
    }
}
