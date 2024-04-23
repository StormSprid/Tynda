package org.example.musicplayer03;

public class Songs {
    int SongId;
    String Name;
    String Artist;
    String genre;

    String urlMusic;
    String urlVocal;
    String urlPhoto;
    String urlLyrics;

    int counter;

    public Songs(int songId, String name, String artist, String genre, String urlMusic, String urlVocal, String urlPhoto, String urlLyrics) {
        SongId = songId;
        Name = name;
        Artist = artist;
        this.genre = genre;
        this.urlMusic = urlMusic;
        this.urlVocal = urlVocal;
        this.urlPhoto = urlPhoto;
        this.urlLyrics = urlLyrics;
    }

    public String getArtist() {
        return Artist;
    }

    public void setArtist(String artist) {
        Artist = artist;
    }

    public String getUrlMusic() {
        return urlMusic;
    }

    public void setUrlMusic(String urlMusic) {
        this.urlMusic = urlMusic;
    }

    public String getUrlVocal() {
        return urlVocal;
    }

    public void setUrlVocal(String urlVocal) {
        this.urlVocal = urlVocal;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    public String getUrlLyrics() {
        return urlLyrics;
    }

    public void setUrlLyrics(String urlLyrics) {
        this.urlLyrics = urlLyrics;
    }


    public int getSongId() {
        return SongId;
    }

    public void setSongId(int songId) {
        SongId = songId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }



    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

}
