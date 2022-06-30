package com.example.anakku.models;

import java.util.Date;

public class User {
    private String uid;
    private String nama;
    private String jenisKelamin;
//    private Date tanggalLahir;
    private Integer jumlahAnak;

    public User(String uid, String nama, String jenisKelamin, Integer jumlahAnak) {
        super();

        this.uid = uid;
        this.nama = nama;
        this.jenisKelamin = jenisKelamin;
        this.jumlahAnak = jumlahAnak;
    }

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

    public Integer getJumlahAnak() {
        return jumlahAnak;
    }

    public void setJumlahAnak(Integer jumlahAnak) {
        this.jumlahAnak = jumlahAnak;
    }
}
