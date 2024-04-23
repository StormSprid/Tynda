package org.example.musicplayer03;

import java.util.ArrayList;
import java.util.List;

public class Playlists {
    int PlaylistId;
    String name;
    int UserId;
    String urlPhoto;
    public Playlists(int PlaylistId, String name, String urlPhoto){
        this.PlaylistId= PlaylistId;
        this.name = name;
        this.urlPhoto=urlPhoto;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String name) {
        this.urlPhoto = urlPhoto;
    }
    private List<Songs> songs = new ArrayList<>();;

    public int getPlaylistId() {
        return PlaylistId;
    }

    public void setPlaylistId(int playlistId) {
        PlaylistId = playlistId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void addSong(Songs song) {
        songs.add(song);
    }

    public List<Songs> getSongs() {
        return songs;
    }
}
