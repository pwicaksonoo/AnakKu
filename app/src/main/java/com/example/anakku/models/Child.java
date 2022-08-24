package com.example.anakku.models;

import com.google.firebase.firestore.DocumentId;

import java.util.Date;

public class Child {
    @DocumentId
    private String documentId;

    private String uid;
    private String nama;
    private String jenisKelamin;
    private Date tanggalLahir;
    private Integer tinggiBadan;
    private Integer beratBadan;

    public Child() {

    }

    public Child(String uid, String nama, String jenisKelamin, Date tanggalLahir, Integer tinggiBadan, Integer beratBadan) {
        this.uid = uid;
        this.nama = nama;
        this.jenisKelamin = jenisKelamin;
        this.tanggalLahir = tanggalLahir;
        this.tinggiBadan = tinggiBadan;
        this.beratBadan = beratBadan;
    }

    public Child(String uid, String nama, String jenisKelamin, Date tanggalLahir) {
        this.uid = uid;
        this.nama = nama;
        this.jenisKelamin = jenisKelamin;
        this.tanggalLahir = tanggalLahir;
    }

    public String getDocumentId() { return documentId; }

    public void setDocumentId(String documentId) { this.documentId = documentId; }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public Date getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(Date tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public Integer getTinggiBadan() {
        return tinggiBadan;
    }

    public void setTinggiBadan(Integer tinggiBadan) {
        this.tinggiBadan = tinggiBadan;
    }

    public Integer getBeratBadan() {
        return beratBadan;
    }

    public void setBeratBadan(Integer beratBadan) {
        this.beratBadan = beratBadan;
    }
}
