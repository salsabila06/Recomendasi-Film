package com.example.tubes;

import java.util.Date;

public class Film {

    private int idFilm;
    private String judul;
    private String gambar;
    private String caption;
    private Date tanggal;
    private String genre;
    private String rating;
    private String sinopsis;
    private String link;

    public Film(int idFilm, String judul, String gambar, String caption, Date tanggal, String genre, String rating, String sinopsis, String link) {
        this.idFilm = idFilm;
        this.judul = judul;
        this.gambar = gambar;
        this.caption = caption;
        this.tanggal = tanggal;
        this.genre = genre;
        this.rating = rating;
        this.sinopsis = sinopsis;
        this.link = link;
    }

    public int getIdFilm() {
        return idFilm;
    }

    public void setIdFilm(int idFilm) {
        this.idFilm = idFilm;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}