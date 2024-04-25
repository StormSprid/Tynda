//package org.example.musicplayer03;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class Settings {
//    public static List<Songs> playlist;
//    public static List<Playlists> HomePlaylists = new ArrayList<>();
//
//     Playlists  ForYouPl =  new Playlists(1, "For you", "/icons/radio.jpg");
//
//
//    public  void initializePlaylist(){
//        playlist = new ArrayList<>();
//
//
//        Songs Lizer = new Songs(1, "Гори", "LIZER", "Russia", "src/Music/LIZER/music.wav", "src/Music/LIZER/vocals.wav", "/icons/Лизер.jpg", "src/Lyrics/Гори.txt");
//        Songs Rihanna = new Songs(2, "Don't stop the music", "Rihanna", "Pop", "src/Music/Rihanna - Don't stop the music/music.wav", "src/Music/Rihanna - Don't stop the music/vocals.wav", "/icons/6480931.jpg", "src/Lyrics/rihanna.txt");
//        Songs roses = new Songs(3, "Roses", "Imanbek", "Pop", "src/Music/roses/music.wav", "src/Music/roses/vocals.wav", "/icons/roses.png", "src/Lyrics/roses.txt");
//        Songs Strykalo = new Songs(4, "Kayen", "Strykalo", "Pop", "src/Music/Стрыкало/music.wav", "src/Music/Стрыкало/vocal.wav", "/icons/Смирись_и_расслабься!.jpg", "src/Lyrics/кайен.txt");
//        Songs Kayrat_almaty = new Songs(5, "Алматынын тундеры", "Кайрат Нуртас", "Pop", "src/Music/Кайрош/Алматынын тундеры/music.wav", "src/Music/Кайрош/Алматынын тундеры/vocals.wav", "/icons/Kazakh.jpg", "src/Lyrics/Алматынын тундеры.txt");
//        Songs Kayrat_myUniverse = new Songs(6, "My universe", "Кайрат Нуртас", "Pop", "src/Music/Кайрош/My Universe/My_Universe_music.wav", "src/Music/Кайрош/My Universe/My_Universe_vocals.wav", "/icons/Kazakh.jpg", "src/Lyrics/MyUniverse.txt");
//        Songs Rhapsody = new Songs(7,"Рапсодия конца света","GONE.fludd","Russia","src/Music/Рапсодия Конца Света/music.wav","src/Music/Рапсодия Конца Света/vocals.wav","/icons/Rapsodiya_Konca_Sveta.png","src/Lyrics/Rapsodiya.txt");
//        Songs tesno = new Songs(8,"Тесно","Bushido Zho","Russia","src/Music/Тесно - Bushido ZHO/music.wav","src/Music/Тесно - Bushido ZHO/vocals.wav","/icons/Тесно.jpg","src/Lyrics/Тесно.txt");
//        Songs domino = new Songs(9,"Домино","FACE","Russia","src/Music/Face - Домино/music.wav","src/Music/Face - Домино/vocals.wav","/icons/FACE.png","src/Lyrics/домино.txt");
//
//        ForYouPl.addSong(Rhapsody);
//
//        ForYouPl.addSong(Lizer);
//        ForYouPl.addSong(Rihanna);
//        ForYouPl.addSong(roses);
//        ForYouPl.addSong(Strykalo);
//        ForYouPl.addSong(Kayrat_almaty);
//        ForYouPl.addSong(Kayrat_myUniverse);
//        ForYouPl.addSong(tesno);
//        ForYouPl.addSong(domino);
//
//
//        playlist.add(Rhapsody);
//        Playlists RockPl = new Playlists(2, "Rock", "/icons/rock.jpg");
//        Playlists HipHopPl = new Playlists(3, "Hip-hop", "/icons/HipHop.jpg" );
//        Playlists KazakhPl = new Playlists(4, "Kazakh music", "/icons/Kazakh.jpg");
//        Playlists RussianPl = new Playlists(5, "Russian music", "/icons/Russia.jpg");
//        Playlists FromUsPl = new Playlists(6, "From us", "/icons/FromUs.jpg");
//        HomePlaylists.add(ForYouPl);
//        HomePlaylists.add(RockPl);
//        HomePlaylists.add(HipHopPl);
//        HomePlaylists.add(KazakhPl);
//        HomePlaylists.add(RussianPl);
//        HomePlaylists.add(FromUsPl);
//        RockPl.addSong(Kayrat_almaty);
//        RockPl.addSong(Kayrat_myUniverse);
//        RockPl.addSong(Lizer);
//
//        RussianPl.addSong(Lizer);
//        RussianPl.addSong(domino);
//        RussianPl.addSong(Rhapsody);
//
//
//
//
//
//
//    }
//}
