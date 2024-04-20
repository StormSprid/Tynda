package org.example.musicplayer03;

public class Songs {
    int SongId;
    String Name;
    String genre;
    String url_vocal;
    String url_music;

    int counter;
    public Songs(int songId, String name, String genre, String urlMusic, String urlVocal, int counter) {
        this.SongId = songId;
        this.Name = name;
        this.genre = genre;
        this.url_music = urlMusic;
        this.url_vocal = urlVocal;
        this.counter = counter;
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

    public String getUrl_vocal() {
        return url_vocal;
    }

    public void setUrl_vocal(String url_vocal) {
        this.url_vocal = url_vocal;
    }
    public String getUrl_music() {
        return url_music;
    }

    public void setUrl_music(String url_music) {
        this.url_music = url_music;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

}
