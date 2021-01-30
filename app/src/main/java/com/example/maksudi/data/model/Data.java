package com.example.maksudi.data.model;

public class Data {
    String namaBarang,barcode, jenisBarang,gambarBarang,tanggal,id;
    Integer hargaBarang,jumlahBarang,stokMasuk,stokKeluar;

    public Data() {
        this.id = id;
        this.tanggal = tanggal;
        this.namaBarang = namaBarang;
        this.gambarBarang = gambarBarang;
        this.barcode = barcode;
        this.jenisBarang = jenisBarang;
        this.hargaBarang = hargaBarang;
        this.jumlahBarang = jumlahBarang;
        this.stokMasuk = stokMasuk;
        this.stokKeluar = stokKeluar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getGambarBarang() {
        return gambarBarang;
    }

    public void setGambarBarang(String gambarBarang) {
        this.gambarBarang = gambarBarang;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getJenisBarang() {
        return jenisBarang;
    }

    public void setJenisBarang(String jenisBarang) {
        this.jenisBarang = jenisBarang;
    }

    public Integer getHargaBarang() {
        return hargaBarang;
    }

    public void setHargaBarang(Integer hargaBarang) {
        this.hargaBarang = hargaBarang;
    }

    public Integer getJumlahBarang() {
        return jumlahBarang;
    }

    public void setJumlahBarang(Integer jumlahBarang) {
        this.jumlahBarang = jumlahBarang;
    }

    public Integer getStokMasuk() {
        return stokMasuk;
    }

    public void setStokMasuk(Integer stokMasuk) {
        this.stokMasuk = stokMasuk;
    }

    public Integer getStokKeluar() {
        return stokKeluar;
    }

    public void setStokKeluar(Integer stokKeluar) {
        this.stokKeluar = stokKeluar;
    }
}
