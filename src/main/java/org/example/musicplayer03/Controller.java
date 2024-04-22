package org.example.musicplayer03;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;
import java.io.File;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class Controller implements Initializable {


    int currentSongid = 0;

    @FXML
    private Label label_welcome;
    @FXML
    private Button button_logout;
    @FXML
    private Button HomeBtn;
    @FXML
    private Button TopSongsBtn;
    @FXML
    private Button playButton;

    @FXML
    private Button pauseButton;
    @FXML
    private Button pauseButton1;
    @FXML
    private Button playButton1;


    @FXML
    private Label volumeLabel;
    @FXML
    private Label karaokeLabel;

    @FXML
    private Label volumeLabelShtorka;

    @FXML
    private Slider volumeSlider;

    @FXML
    private Slider karaokeSlider;
    @FXML
    private Slider volumeSliderShtorka;
    @FXML
    private Slider trackSlider;
    @FXML
    private Slider trackSliderShtorka;
    @FXML
    private Button ShtorkaVrskytieBtn;
    @FXML
    private Pane Shtorka;
    private Boolean isShtorkaOpen = false;
    @FXML
    private ImageView UpperSongPhOpened;
    @FXML
    private ImageView UpperSongPh;
    @FXML
    private TextArea SongTextArea;
    @FXML
    private TextFlow ArtistSongNameText;
    @FXML
    private Label UpperArtistName;
    @FXML
    private Label UpperArtistName1;
    @FXML
    private Label UpperSongName1;
    @FXML
    private Label durationLabel;
    @FXML
    private Label timerLabel;
    @FXML
    private Label UpperSongName;
    @FXML
    private HBox OpenedSliderHBox;
    @FXML
    private HBox OpenedButtonsHBox;
    @FXML
    private HBox UpperButtonsHBox;
    @FXML
    private Button CloseShtorka;

    private Timeline timeline;



    @FXML
    private ScrollPane HomePage;
    @FXML
    private ScrollPane topSongsPage;
    @FXML
    private TextField lyricsField;
    private boolean isPlaying = false;
    private Button currentActiveButton = null;

    String currentLyrics = " ";






    private void updateButtonVisibility() {
        playButton.setVisible(!isPlaying);
        pauseButton.setVisible(isPlaying);
        playButton1.setVisible(!isPlaying);
        pauseButton1.setVisible(isPlaying);

    }


    @FXML
    protected void play() {
        if (!MusicLib.isSongLoaded()) {


            isPlaying = true;

            updateButtonVisibility();
            timeline.play();
            playPlaylist();
            durationLabel.setText(MusicLib.secondsToString(MusicLib.getTotalDuration()));


        } else {
            pause();


        }

    }
//    @FXML
//    protected   void playNextSong(){
//        Player.playNextSong();
//    }



    @FXML
    protected void stop() {
        isPlaying = !isPlaying;
        if (isPlaying) {
            MusicLib.stopDouble();

        }

    }



    private void initializeSliders() {
        // Инициализация слайдеров
        trackSlider.setMin(0);
        trackSlider.setMax(100);
        trackSlider.setValue(0);
        trackSliderShtorka.setMin(0);
        trackSliderShtorka.setMax(100);
        trackSliderShtorka.setValue(0);


        // Обработчик изменения значения слайдера
        trackSlider.setOnMouseDragEntered(event -> {
            if (isPlaying) {
                MusicLib.setTrackPosition(trackSlider.getValue());
            }


        });
        trackSlider.setOnMouseClicked(event -> {
            if (isPlaying) {
                MusicLib.setTrackPosition(trackSlider.getValue());
            }

        });
        trackSlider.setOnDragDone(event -> {
            if (isPlaying) {
                MusicLib.setTrackPosition(trackSlider.getValue());
            }

        });


        trackSliderShtorka.setOnMouseDragEntered(event -> {
            if (isPlaying) {
                MusicLib.setTrackPosition(trackSliderShtorka.getValue());
            }


        });
        trackSliderShtorka.setOnMouseClicked(event -> {
            if (isPlaying) {
                MusicLib.setTrackPosition(trackSliderShtorka.getValue());
            }

        });
        trackSliderShtorka.setOnDragDone(event -> {
            if (isPlaying) {
                MusicLib.setTrackPosition(trackSliderShtorka.getValue());
            }

        });
    }

    private void initializeTimeline() {
        // Создание таймлайна для обновления слайдера каждую секунду
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    if (MusicLib.isTrackDone()){
                        updateButtonVisibility();
                    }
                    if (isPlaying && !trackSlider.isValueChanging()) {

                        trackSlider.setValue((double) MusicLib.getTrackPosition());

                    }
                    if (isPlaying && !trackSliderShtorka.isValueChanging()) {

                        trackSliderShtorka.setValue((double) MusicLib.getTrackPosition());

                    }


                    timerLabel.setText(MusicLib.secondsToString(MusicLib.getTrackPositionToInt()));


                    int currentSecond = MusicLib.getTrackPositionToInt();
                    String dir = currentLyrics;
                    try (BufferedReader br = new BufferedReader(new FileReader(dir))) {
                        String line;
                        while ((line = br.readLine()) != null) {
                            String[] parts = line.split(";");
                            if (parts.length == 2) {
                                String time = parts[0];
                                String text = parts[1];

                                String[] timeParts = time.split(":");
                                int minute = Integer.parseInt(timeParts[0]);
                                int seconds = Integer.parseInt(timeParts[1]);

                                if (currentSecond ==(minute*60 + seconds)){
                                    SongTextArea.appendText(text + "\n");
                                }
                            }

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);

    }

    private List<Songs> playlist;

    private void initializePlaylist(){
        playlist = new ArrayList<>();

        Songs Lizer = new Songs(1, "Гори", "LIZER", "Russia", "src/Music/LIZER/music.wav", "src/Music/LIZER/vocals.wav", "src/main/resources/icons/Лизер.jpg", "src/Lyrics/Гори.txt");
        Songs Rihanna = new Songs(2, "Don't stop the music", "Rihanna", "Pop", "src/Music/Rihanna - Don't stop the music/music.wav", "src/Music/Rihanna - Don't stop the music/vocals.wav", "src/main/resources/icons/6480931.jpg", "src/Lyrics/Гори.txt");
        Songs roses = new Songs(3, "Roses", "Imanbek", "Pop", "src/Music/roses/music.wav", "src/Music/roses/vocals.wav", "src/main/resources/icons/roses.png", "src/Lyrics/Гори.txt");
        Songs Strykalo = new Songs(4, "Kayen", "Strykalo", "Pop", "src/Music/Стрыкало/music.wav", "src/Music/Стрыкало/vocal.wav", "src/main/resources/icons/Смирись_и_расслабься!.jpg", "src/Lyrics/кайен.txt");
        Songs Kayrat_almaty = new Songs(5, "Алматынын тундеры", "Кайрат Нуртас", "Pop", "src/Music/Кайрош/Алматынын тундеры/music.wav", "src/Music/Кайрош/Алматынын тундеры/vocals.wav", "src/main/resources/icons/Kazakh.jpg", "src/Lyrics/Алматынын тундеры.txt");
        Songs Kayrat_myUniverse = new Songs(6, "My universe", "Кайрат Нуртас", "Pop", "src/Music/Кайрош/My Universe/My_Universe_music.wav", "src/Music/Кайрош/My Universe/My_Universe_vocals.wav", "src/main/resources/icons/Kazakh.jpg", "src/Lyrics/MyUniverse.txt");
        playlist.add(Lizer);
        playlist.add(Rihanna);
        playlist.add(roses);
        playlist.add(Strykalo);
        playlist.add(Kayrat_almaty);
        playlist.add(Kayrat_myUniverse);
    }



    @FXML
    protected void pause() {
        isPlaying = !isPlaying;
        MusicLib.pauseDouble();
        updateButtonVisibility();
        timeline.play();

    }

    @FXML
    protected void setVolume() {
        MusicLib.setVolume(volumeSlider.getValue());
        MusicLib.setVolume(volumeSliderShtorka.getValue());

        if (volumeSlider.isValueChanging()) {
            double currentVolume = volumeSlider.getValue();

            volumeSlider.setValue(currentVolume);
            volumeSliderShtorka.setValue(currentVolume);

            volumeLabel.setText(String.format("%.0f%%", currentVolume));
            volumeLabelShtorka.setText(String.format("%.0f%%", currentVolume));


        } else if (volumeSliderShtorka.isValueChanging()){
            double currentVolume = volumeSliderShtorka.getValue();

            volumeSlider.setValue(currentVolume);
            volumeSliderShtorka.setValue(currentVolume);

            volumeLabel.setText(String.format("%.0f%%", currentVolume));
            volumeLabelShtorka.setText(String.format("%.0f%%", currentVolume));
        }
    }
    @FXML
    protected void setKaraokeVolume(){
        MusicLib.setVocalVolume(karaokeSlider.getValue());
        double currentVolume = karaokeSlider.getValue();
        karaokeLabel.setText(String.format("%.0f%%",currentVolume));
    }

    @FXML
    private void showHome() {
        HomePage.setVisible(true);
        topSongsPage.setVisible(false);
        PressButton(HomeBtn);
        // добавить для других панелей
    }

    @FXML
    private void showTopSongs() {
        HomePage.setVisible(false);
        topSongsPage.setVisible(true);
        PressButton(TopSongsBtn);

        // добавить для других панелей
    }



    private void PressButton(Button button) {

        if (currentActiveButton != null) {
            UnpressButton(currentActiveButton);
        }
        button.setStyle("-fx-background-color: rgba(0, 0, 0, 0.57);");
        currentActiveButton = button;
    }

    private void UnpressButton(Button button) {
        button.setStyle("-fx-background-color: #ffdcbd;");
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        button_logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "login.fxml", null);
            }
        });
        updateButtonVisibility(); // Установка начального состояния кнопок
        initializeSliders();
        initializeTimeline();
        initializePlaylist();
    }

    public void setUserInformation(String username) {
        label_welcome.setText("Welcome " + username + "!");
    }



    @FXML
    private  void setTrackPosition(){
        MusicLib.setTrackPosition(trackSlider.getValue());
        MusicLib.setTrackPosition(trackSliderShtorka.getValue());
    }

    @FXML
    public void RotateShtorka() {
        animateSize(); // Запуск анимации изменения размеров
        animateImage(); // Запуск анимации изображения
        animateTextArea();
        animateArtistSongName();
        animateSlider();
        animateButtons();
        if(isShtorkaOpen){
            CloseShtorka.setVisible(true);
        }
        else{
            CloseShtorka.setVisible(false);
        }
    }

    private void animateSize() {
        Timeline timeline = new Timeline();

        KeyValue kvWidth;
        KeyValue kvHeight;

        if (!isShtorkaOpen) {
            kvWidth = new KeyValue(Shtorka.prefWidthProperty(), 898);
            kvHeight = new KeyValue(Shtorka.prefHeightProperty(), 760);
            isShtorkaOpen = true;
        } else {
            kvWidth = new KeyValue(Shtorka.prefWidthProperty(), 898);
            kvHeight = new KeyValue(Shtorka.prefHeightProperty(), 82);
            isShtorkaOpen = false;
        }

        KeyFrame kfSize = new KeyFrame(Duration.seconds(0.6), kvWidth, kvHeight);
        timeline.getKeyFrames().add(kfSize);
        timeline.play(); // Запуск анимации изменения размеров
    }

    private void animateImage() {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.2), UpperSongPhOpened);

        if (isShtorkaOpen) {
            UpperSongPhOpened.setVisible(true);
            UpperSongPhOpened.setOpacity(0.0);
            fadeTransition.setFromValue(0.0);
            fadeTransition.setToValue(1.0);
            fadeTransition.setDelay(Duration.seconds(0.5));
            UpperSongPh.setVisible(false);// Задержка перед началом fade-in
        } else {
            UpperSongPh.setVisible(true);
            fadeTransition.setFromValue(1.0);
            fadeTransition.setToValue(0.0);
            fadeTransition.setDelay(Duration.seconds(0.0)); // Без задержки

            // Установка действия по завершении fade-out
            fadeTransition.setOnFinished(event -> UpperSongPhOpened.setVisible(false));
        }

        fadeTransition.play(); // Запуск анимации fade
    }
    private void animateTextArea() {
        // Создаём объект FadeTransition с указанием длительности анимации и объекта TextArea
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.2), SongTextArea);

        if (isShtorkaOpen) {
            // Устанавливаем видимость TextArea и начальное значение прозрачности
            SongTextArea.setVisible(true);
            SongTextArea.setOpacity(0.0);
            // Устанавливаем значения начальной и конечной прозрачности
            fadeTransition.setFromValue(0.0);
            fadeTransition.setToValue(1.0);
            // Задержка перед началом fade-in
            fadeTransition.setDelay(Duration.seconds(0.5));
            loadText(currentLyrics);
        } else {
            // Устанавливаем значения начальной и конечной прозрачности
            fadeTransition.setFromValue(1.0);
            fadeTransition.setToValue(0.0);
            // Установка видимости TextArea на false после завершения fade-out анимации
            fadeTransition.setOnFinished(event -> SongTextArea.setVisible(false));
        }

        // Запуск анимации fade
        fadeTransition.play();
    }
    private void animateArtistSongName() {
        // Создаём объект FadeTransition с указанием длительности анимации и объекта TextArea
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.2), ArtistSongNameText);

        if (isShtorkaOpen) {
            // Устанавливаем видимость TextArea и начальное значение прозрачности
            ArtistSongNameText.setVisible(true);
            ArtistSongNameText.setOpacity(0.0);
            // Устанавливаем значения начальной и конечной прозрачности
            fadeTransition.setFromValue(0.0);
            fadeTransition.setToValue(1.0);
            // Задержка перед началом fade-in
            fadeTransition.setDelay(Duration.seconds(0.5));
            UpperArtistName.setVisible(false);
            UpperSongName.setVisible(false);
        } else {
            // Устанавливаем значения начальной и конечной прозрачности
            fadeTransition.setFromValue(1.0);
            fadeTransition.setToValue(0.0);
            UpperArtistName.setVisible(true);
            UpperSongName.setVisible(true);
            // Установка видимости TextArea на false после завершения fade-out анимации
            fadeTransition.setOnFinished(event -> ArtistSongNameText.setVisible(false));
        }

        // Запуск анимации fade
        fadeTransition.play();
    }



    public void animateSlider() {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.2), OpenedSliderHBox);
        if (isShtorkaOpen) {
            OpenedSliderHBox.setOpacity(0.0);
            OpenedSliderHBox.setVisible(true);
            fadeTransition.setFromValue(0.0);
            fadeTransition.setToValue(1.0);
            fadeTransition.setDelay(Duration.seconds(0.5));
            trackSlider.setVisible(false);
        } else {
            fadeTransition.setFromValue(1.0);
            fadeTransition.setToValue(0.0);
            fadeTransition.setOnFinished(event -> {
                OpenedSliderHBox.setVisible(false);

            });
            trackSlider.setVisible(true);
        }
        fadeTransition.play();
    }

    public void animateButtons() {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.2), OpenedButtonsHBox);
        if (isShtorkaOpen) {
            OpenedButtonsHBox.setOpacity(0.0);
            OpenedButtonsHBox.setVisible(true);
            fadeTransition.setFromValue(0.0);
            fadeTransition.setToValue(1.0);
            fadeTransition.setDelay(Duration.seconds(0.5));
            volumeSlider.setVisible(false);
            volumeLabel.setVisible(false);
            UpperButtonsHBox.setVisible(false);

        } else {
            fadeTransition.setFromValue(1.0);
            fadeTransition.setToValue(0.0);
            fadeTransition.setOnFinished(event -> {
                OpenedButtonsHBox.setVisible(false);
            });
            volumeSlider.setVisible(true);
            volumeLabel.setVisible(true);
            UpperButtonsHBox.setVisible(true);
        }
        fadeTransition.play();
    }


    // Чтение текста из файла и установка его в TextArea
    @FXML
    public void loadText(String path ) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            StringBuilder content = new StringBuilder();
            String line;

            // Читаем файл построчно
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }



        } catch (IOException e) {
            // Обработка ошибок чтения файла
            System.err.println("Error reading file: " + e.getMessage());
        }
    }


    public void playPlaylist(){
        System.out.println(currentSongid);
        Songs song = playlist.get(currentSongid);
        MusicLib.playDouble(song.getUrlMusic(),song.getUrlVocal());
        Image image = new Image(new File(song.getUrlPhoto()).toURI().toString());
        UpperSongPh.setImage(image);
        UpperSongPhOpened.setImage(image);

        UpperSongName.setText(song.Name);
        UpperArtistName.setText(song.Artist);


        UpperArtistName1.setText(song.Name);
        UpperSongName1.setText(song.Artist);
        SongTextArea.setText(" ");


        currentLyrics = song.urlLyrics;
        durationLabel.setText(MusicLib.secondsToString(MusicLib.getTotalDuration()));

    }
    @FXML
    protected void playNextSong(){

        currentSongid++;
        if (currentSongid<playlist.size()) {

            Songs song = playlist.get(currentSongid);
            Image image = new Image(new File(song.getUrlPhoto()).toURI().toString());
            UpperSongPh.setImage(image);
            UpperSongPhOpened.setImage(image);

            UpperSongName.setText(song.Name);
            UpperArtistName.setText(song.Artist);


            UpperArtistName1.setText(song.Name);
            UpperSongName1.setText(song.Artist);
            currentLyrics = song.urlLyrics;
            SongTextArea.setText(" ");
            durationLabel.setText(MusicLib.secondsToString(MusicLib.getTotalDuration()));

            MusicLib.stopDouble();
            MusicLib.playDouble(song.getUrlMusic(), song.getUrlVocal());
            if (playButton.isVisible()) {
                playButton.setVisible(false);
                pauseButton.setVisible(true);
            }
        }
    }

    @FXML
    protected void playPreviousSong(){

        currentSongid--;
        if (currentSongid<playlist.size()) {

            Songs song = playlist.get(currentSongid);
            Image image = new Image(new File(song.getUrlPhoto()).toURI().toString());
            UpperSongPh.setImage(image);
            UpperSongPhOpened.setImage(image);

            UpperSongName.setText(song.Name);
            UpperArtistName.setText(song.Artist);
            currentLyrics = song.urlLyrics;
            SongTextArea.setText(" ");

            UpperArtistName1.setText(song.Name);
            UpperSongName1.setText(song.Artist);
            durationLabel.setText(MusicLib.secondsToString(MusicLib.getTotalDuration()));
            MusicLib.stopDouble();
            MusicLib.playDouble(song.getUrlMusic(), song.getUrlVocal());
            if (playButton.isVisible()) {
                playButton.setVisible(false);
                pauseButton.setVisible(true);
            }
        }
    }
}


