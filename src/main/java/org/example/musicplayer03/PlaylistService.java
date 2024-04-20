package org.example.musicplayer03;

import java.util.ArrayList;
import java.util.List;

public class PlaylistService {
    private List<Playlists> playlists = new ArrayList<>();

    public PlaylistService() {
        // Инициализация некоторых плейлистов
        Playlists playlist = new Playlists();
        playlist.setName("My Favorite Songs");
        playlist.addSong(new Songs(1, "My Universe", "Kazakh pop", "src/Music/Кайрош/My Universe/My_Universe_music.wav", "src/Music/Кайрош/My Universe/My_Universe_vocals.wav", 0));
        playlist.addSong(new Songs(2, "ГОРИ", "Russian pop", "src/Music/ЛИЗЕР/lizer-mayot-gori-mp3 [music].wav", "src/Music/ЛИЗЕР/lizer-mayot-gori-mp3 [vocals].wav", 0));
        playlists.add(playlist);
    }

    public List<Playlists> getPlaylists() {
        return playlists;
    }

    public Playlists getPlaylistByName(String name) {
        return playlists.stream()
                .filter(p -> p.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
