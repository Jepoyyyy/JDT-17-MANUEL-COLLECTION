package com.indivaragroup.collection.exam.film;

import java.time.LocalDate;

class Film {
    private String artistName;
    private String avCode;
    private LocalDate releaseDate;
    private String category;
    private String subCategory;

    public Film(String artistName, String avCode, LocalDate tanggalRelease, String category, String subCategory) {
        this.artistName = artistName;
        this.avCode = avCode;
        this.releaseDate = tanggalRelease;
        this.category = category;
        this.subCategory = subCategory;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getAvCode() {
        return avCode;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public String getCategory() {
        return category;
    }

    public String getSubCategory() {
        return subCategory;
    }
}
