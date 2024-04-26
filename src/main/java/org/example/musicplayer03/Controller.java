package org.example.musicplayer03;
import javafx.animation.RotateTransition;
import javafx.scene.transform.Rotate;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.TextFlow;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import java.io.File;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.List;
import java.util.ResourceBundle;
public class Controller implements Initializable {


    String[]  badWords = {"сука","Сука","бля","Бля","ебанной","ебанный","ёбаных","Жопа","жопа"};
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
    private Button MySongsBtn;
    @FXML
    private Button playButton;
    @FXML
    private ScrollPane MySongsScrollPane;

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
    @FXML
    private TilePane PlaylistPane;
    @FXML
    private ScrollPane PlaylistScrollPane;
    @FXML
    private AnchorPane PlAnchor;
    @FXML
    private ScrollPane ExamplePAne;
@FXML
private Button ClosePlbtn;
@FXML
private TilePane ExampleTilePAne;
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
    private int currentIndex = 0; // Индекс текущей песни







    private void updateButtonVisibility() {
        playButton.setVisible(!isPlaying);
        pauseButton.setVisible(isPlaying);
        playButton1.setVisible(!isPlaying);
        pauseButton1.setVisible(isPlaying);

    }


    @FXML
    protected void play() {
        if (!MusicLib.isSongLoaded()) {

        } else {
            pause();
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

                    if (MusicLib.getTrackPositionToInt() == MusicLib.getTotalDuration() ){
                        updateButtonVisibility();

                        playNextSong();
                    }


                    int currentSecond = MusicLib.getTrackPositionToInt();
                    String dir = currentLyrics;
                    boolean foundValidLines = false; // Флаг для отслеживания найденных корректных строк
                    if (dir!= null) {
                        try (BufferedReader br = new BufferedReader(new FileReader(dir))) {
                            String line;
                            while ((line = br.readLine()) != null) {
                                // Проверяем наличие нулевого байта в строке
                                if (line.startsWith("\uFEFF")) {
                                    line = line.substring(1); // Удаляем нулевой байт из строки
                                }

                                String[] parts = line.split(";");
                                // Проверяем количество частей после разделения строки
                                if (parts.length == 2) {
                                    foundValidLines = true; // Устанавливаем флаг в true, если найдена корректная строка

                                    String time = parts[0];
                                    String text = parts[1];


                                    String[] timeParts = time.split(":");
                                    int minute = Integer.parseInt(timeParts[0]);
                                    int seconds = Integer.parseInt(timeParts[1]);


                                    if (currentSecond == (minute * 60 + seconds)) {
                                        for (String badWord : badWords) {
                                            if (text.contains(badWord) || text.contains("$")) {
                                                MusicLib.nonVocalMod();
                                            } else {
                                                MusicLib.vocalMod();
                                            }
                                        }
                                            SongTextArea.appendText(text.replace("$", "") + "\n");

                                    }
                                }


                            }

                            // Если не было найдено корректных строк, выводим соответствующее сообщение
                            if (!foundValidLines || currentLyrics == null) {
                                SongTextArea.setText("Упс! Текст данной песни откроется на платной версии приложения!");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else {
                        SongTextArea.setText("Упс! Текст данной песни откроется на платной версии приложения!");
                    }

                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);

    }
    public List<Playlists> HomePlaylists = new ArrayList<>();

    Playlists ForYouPl = new Playlists(1, "For you", "/icons/radio.jpg");


    private void initializePlaylist(){



        Songs Lizer = new Songs(1, "Гори", "LIZER", "Russia", "src/Music/LIZER/music.wav", "src/Music/LIZER/vocals.wav", "/icons/Лизер.jpg", "src/Lyrics/Гори.txt");
        Songs Rihanna = new Songs(2, "Don't stop the music", "Rihanna", "Pop", "src/Music/Rihanna - Don't stop the music/music.wav", "src/Music/Rihanna - Don't stop the music/vocals.wav", "/icons/6480931.jpg", "src/Lyrics/rihanna.txt");
        Songs roses = new Songs(3, "Roses", "Imanbek", "Pop", "src/Music/roses/music.wav", "src/Music/roses/vocals.wav", "/icons/roses.png", "src/Lyrics/roses.txt");
        Songs Strykalo = new Songs(4, "Kayen", "Strykalo", "Pop", "src/Music/Стрыкало/music.wav", "src/Music/Стрыкало/vocal.wav", "/icons/Смирись_и_расслабься!.jpg", "src/Lyrics/кайен.txt");
        Songs Kayrat_almaty = new Songs(5, "Алматынын тундеры", "Кайрат Нуртас", "Pop", "src/Music/Кайрош/Алматынын тундеры/music.wav", "src/Music/Кайрош/Алматынын тундеры/vocals.wav", "/icons/Kazakh.jpg", "src/Lyrics/Алматынын тундеры.txt");
        Songs Kayrat_myUniverse = new Songs(6, "My universe", "Кайрат Нуртас", "Pop", "src/Music/Кайрош/My Universe/My_Universe_music.wav", "src/Music/Кайрош/My Universe/My_Universe_vocals.wav", "/icons/Kazakh.jpg", "src/Lyrics/MyUniverse.txt");
        Songs Rhapsody = new Songs(7,"Рапсодия конца света","GONE.Fludd","Russia","src/Music/Рапсодия Конца Света/music.wav","src/Music/Рапсодия Конца Света/vocals.wav","/icons/Rapsodiya_Konca_Sveta.png","src/Lyrics/Rapsodiya.txt");
        Songs tesno = new Songs(8,"Тесно","Bushido Zho","Russia","src/Music/Тесно - Bushido ZHO/music.wav","src/Music/Тесно - Bushido ZHO/vocals.wav","/icons/Тесно.jpg","src/Lyrics/Тесно.txt");
        Songs domino = new Songs(9,"Домино","FACE","Russia","src/Music/Face - Домино/music.wav","src/Music/Face - Домино/vocals.wav","/icons/FACE.png","src/Lyrics/домино.txt");
        Songs MenSeniSuyemin = new Songs(10,"Men Seni Suyemin","Son Paskal","Kazakh","src/Music/Son Paskal - Men Seni Suyemin/music.wav","src/Music/Son Paskal - Men Seni Suyemin/vocals.wav","/icons/artworks-JwNW6K1GR42s-0-t500x500.jpg","src/Lyrics/men seni suyemin.txt");
        Songs Mechty = new Songs(11,"Мечты","Aarne,Feduk,Scally Milano","Russian","src/Music/Мечты - Aarne/music.wav","src/Music/Мечты - Aarne/vocal.wav","/icons/Тесно.jpg","src/Lyrics/Мечты - Aarne.txt");
        Songs ComeAsYouAre = new Songs(12,"Come as you are","Nivana","Rock","src/Music/Nirvana - Come As you are/Nirvana-Come-As-You-Are-.wav",null,"/icons/Nevermind-compressed.jpg",null);


        ForYouPl.addSong(Rhapsody);

        ForYouPl.addSong(Lizer);
        ForYouPl.addSong(Rihanna);
        ForYouPl.addSong(roses);
        ForYouPl.addSong(Strykalo);
        ForYouPl.addSong(Kayrat_almaty);
        ForYouPl.addSong(Kayrat_myUniverse);
        ForYouPl.addSong(tesno);
        ForYouPl.addSong(domino);



        Playlists RockPl = new Playlists(2, "Rock", "/icons/rock.jpg");
        Playlists HipHopPl = new Playlists(3, "Hip-hop", "/icons/HipHop.jpg" );
        Playlists KazakhPl = new Playlists(4, "Kazakh music", "/icons/Kazakh.jpg");
        Playlists RussianPl = new Playlists(5, "Russian music", "/icons/Russia.jpg");
        Playlists FromUsPl = new Playlists(6, "From us", "/icons/FromUs.jpg");
        HomePlaylists.add(ForYouPl);
        HomePlaylists.add(RockPl);
        HomePlaylists.add(HipHopPl);
        HomePlaylists.add(KazakhPl);
        HomePlaylists.add(RussianPl);
        HomePlaylists.add(FromUsPl);


        KazakhPl.addSong(Kayrat_almaty);
        KazakhPl.addSong(Kayrat_myUniverse);
        KazakhPl.addSong(MenSeniSuyemin);


        RussianPl.addSong(Lizer);
        RussianPl.addSong(domino);
        RussianPl.addSong(Rhapsody);


        RockPl.addSong(ComeAsYouAre);


        FromUsPl.addSong(Mechty);




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
        SetupHome();
        topSongsPage.setVisible(false);
        PressButton(HomeBtn);
        PlaylistScrollPane.setVisible(false);
        HomePage.setVisible(true);
        MySongsScrollPane.setVisible(false);
        // добавить для других панелей
    }

    @FXML
    private void showTopSongs() {
       HomePage.setVisible(false);
        PlaylistScrollPane.setVisible(false);
        topSongsPage.setVisible(true);
        MySongsScrollPane.setVisible(false);
        PressButton(TopSongsBtn);

        // добавить для других панелей
    }
    @FXML
    private void showMySongs(){
        HomePage.setVisible(false);
        PlaylistScrollPane.setVisible(false);
        topSongsPage.setVisible(false);
        MySongsScrollPane.setVisible(true);
        PressButton(MySongsBtn);
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
        showHome();
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
        if (path!= null) {
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


    }

    @FXML
    protected void playNextSong(){

        nextSong();
    }


    @FXML
    protected void playPreviousSong(){
        previousSong();
    }
    Playlists currentPlaylist;


        // Создаем блок VBox для каждой песни
        public void OpenPlaylist(Playlists playlist) {
            PlaylistPane.getChildren().removeIf(node -> node != ClosePlbtn);
            currentPlaylist = playlist;
            PlaylistScrollPane.setVisible(true);

            for (int i = 0; i < playlist.getSongs().size(); i++) {
                Songs song = playlist.getSongs().get(i);

                HBox songBox = new HBox(10); // HBox с отступом между элементами
                songBox.setAlignment(Pos.CENTER_LEFT); // Выравнивание элементов внутри HBox
                songBox.setPadding(new Insets(5, 10, 5, 10)); // Отступы внутри HBox

                ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream(song.getUrlPhoto())));
                imageView.setFitHeight(70);
                imageView.setFitWidth(70);

                Label nameLabel = new Label(song.getName());
                nameLabel.setStyle("-fx-text-fill: white; -fx-font-size: 22;");

                Label artistLabel = new Label(song.getArtist());
                artistLabel.setStyle("-fx-text-fill: gray; -fx-font-size: 19;");

                VBox textVBox = new VBox(nameLabel, artistLabel);
                textVBox.setAlignment(Pos.CENTER_LEFT);

                songBox.getChildren().addAll(imageView, textVBox);

                VBox container = new VBox(songBox); // Контейнер для HBox и, возможно, линии-разделителя
                Line separator = new Line(0, 0, 790, 0); // Линия длиной в ширину TilePane
                separator.setStrokeWidth(0.5);
                separator.setStroke(Color.GRAY);
                VBox.setMargin(separator, new Insets(10, 0, 2, 0)); // Отступ для линии
                container.getChildren().add(separator);

                Button songButton = new Button();
                songButton.setGraphic(container);
                songButton.setOnMouseEntered(e -> songBox.setStyle("-fx-background-color:  rgba(204, 204, 204, 0.5);"));
                songButton.setOnMouseExited(e -> songBox.setStyle("-fx-background-color: transparent;"));
                songButton.setStyle("-fx-background-color: transparent;");
                songButton.setOnAction(event -> playSongPl(song, playlist));

                PlaylistPane.getChildren().add(songButton);
            }



        }




    public void playSongPl(Songs song, Playlists playlist){
            timeline.play();
        if(!isPlaying){
            isPlaying = true;
        MusicLib.playDouble(song.getUrlMusic(),song.getUrlVocal());
            currentIndex = playlist.getSongs().indexOf(song);;
        }
        else{
            pause();
            playSongPl(song, playlist);
        }

        Image image = new Image(new File("src/main/resources" + song.getUrlPhoto()).toURI().toString());




        UpperSongPh.setImage(image);
        UpperSongPhOpened.setImage(image);
        UpperSongName.setText(song.Name);
        UpperArtistName.setText(song.Artist);
        UpperArtistName1.setText(song.Name);
        UpperSongName1.setText(song.Artist);
        SongTextArea.setText(" ");
        currentLyrics = song.urlLyrics;
        updateButtonVisibility();
        durationLabel.setText(MusicLib.secondsToString(MusicLib.getTotalDuration()));


    }
    public void nextSong() {
        if (currentPlaylist != null && currentIndex < currentPlaylist.getSongs().size() - 1) {
            currentIndex++;


            RotateTransition rotate = new RotateTransition();
            rotate.setDuration(Duration.millis(500));
            rotate.setAxis(Rotate.Y_AXIS);
            rotate.setCycleCount(1);
            rotate.setNode(UpperSongPhOpened);
            rotate.setByAngle(360);
            rotate.play();



            playSongPl(currentPlaylist.getSongs().get(currentIndex), currentPlaylist);
        }
    }

    public void previousSong() {
        if (currentPlaylist != null && currentIndex > 0) {
            currentIndex--;
            RotateTransition rotate = new RotateTransition();
            rotate.setDuration(Duration.millis(500));
            rotate.setAxis(Rotate.Y_AXIS);
            rotate.setCycleCount(1);
            rotate.setNode(UpperSongPhOpened);
            rotate.setByAngle(-360);
            rotate.play();
            playSongPl(currentPlaylist.getSongs().get(currentIndex), currentPlaylist);
        }
    }
    public void SetupHome() {
        ExampleTilePAne.setStyle("-fx-background-color: black;");
        ExampleTilePAne.getChildren().clear();

        // Устанавливаем количество столбцов для TilePane
        PlaylistPane.setPrefColumns(3);  // Число столбцов

        for (Playlists playlist : HomePlaylists) {
            ImageView coverImageView = new ImageView(new Image(getClass().getResourceAsStream(playlist.getUrlPhoto())));
            coverImageView.setFitHeight(200);  // Высота изображения
            coverImageView.setFitWidth(200);   // Ширина изображения

            // Создаем текст для кнопки
            Label buttonText = new Label(playlist.getName());
            buttonText.setFont(Font.font("PT Sans Bold", 35));
            buttonText.setTextFill(Color.WHITE);

            // Создаем VBox для размещения текста и изображения
            VBox imageTextVBox = new VBox(20); // Увеличиваем промежуток между элементами в VBox
            imageTextVBox.getChildren().addAll(buttonText, coverImageView);
            imageTextVBox.setAlignment(Pos.CENTER);

            // Создаем кнопку
            Button playlistButton = new Button();
            playlistButton.setOnMouseEntered(e -> playlistButton.setStyle("-fx-background-color:  rgba(204, 204, 204, 0.5);"));
            playlistButton.setOnMouseExited(e -> playlistButton.setStyle("-fx-background-color: transparent;"));
            playlistButton.setGraphic(imageTextVBox); // Устанавливаем VBox как графическое содержимое кнопки
            playlistButton.setStyle("-fx-background-color: transparent;"); // Прозрачный фон кнопки
            playlistButton.setMaxWidth(Double.MAX_VALUE);

            VBox outerVBox = new VBox(15);  // Внешний VBox для размещения кнопки с увеличенным отступом
            outerVBox.setPadding(new Insets(30)); // Добавляем внешний отступ
            outerVBox.getChildren().addAll(playlistButton); // Добавляем только кнопку
            outerVBox.setAlignment(Pos.CENTER);  // Выравнивание элементов внутри VBox по центру

            ExampleTilePAne.getChildren().add(outerVBox);  // Добавляем внешний VBox в TilePane

            playlistButton.setOnAction(event -> OpenPlaylist(playlist));
        }
    }



    public void ClosePlaylist(){
    PlaylistScrollPane.setVisible(false);
}

}

