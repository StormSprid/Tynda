package org.example.musicplayer03;
import javafx.animation.RotateTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
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
import javafx.stage.FileChooser;
import javafx.util.Duration;
import java.io.File;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

import java.sql.*;
import java.util.*;

import static javafx.scene.input.KeyCode.L;
import static javafx.scene.input.KeyCode.S;

public class Controller implements Initializable {


    String[]  badWords = {"сука","Сука","бля","Бля","ебанной","ебанный","ёбаных","Жопа","жопа"};

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
    private Button AddSongBtn;
    @FXML
    private AnchorPane AddSongPage;
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
    private Pane searchPane;
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
    private TilePane TopSongsTilePane;
    @FXML
    private TextField lyricsField;
    private boolean isPlaying = false;
    private Button currentActiveButton = null;

    String currentLyrics = " ";
    private int currentIndex = 0; // Индекс текущей песни
    private Playlistinitializer playlistinitializer;
    private Connection connection;
    Animations animations = new Animations();

    // Метод для установления соединения с базой данных
    public void connectToDatabase() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/javafx-tynda";
        String username = "root";
        String password = "admin";
        connection = DriverManager.getConnection(url, username, password);
    }

    // Метод для закрытия соединения с базой данных
    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public Controller(){
        this.playlistinitializer = new Playlistinitializer();
    }




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
public boolean isChildModeActive = false;
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

                        playNextSong();

                    }


                    Settings.setTextOnTextArea(currentLyrics,SongTextArea,isChildModeActive);

                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);

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
        double currentVolume = volumeSlider.getValue();
        MusicLib.setVolume(currentVolume);

        volumeLabel.setText(String.format("%.0f%%", currentVolume));

        if (volumeSlider.isValueChanging()) {
            volumeSlider.setValue(currentVolume);
            volumeSliderShtorka.setValue(currentVolume);
        }
    }
    @FXML
    protected void setKaraokeVolume() {
        double currentVolume = karaokeSlider.getValue();
        MusicLib.setVocalVolume(currentVolume);
        karaokeLabel.setText(String.format("%.0f%%", currentVolume));
    }


    @FXML
    private void showHome() {
        topSongsPage.setVisible(false);
        PressButton(HomeBtn);
        PlaylistScrollPane.setVisible(false);
        HomePage.setVisible(true);
        MySongsScrollPane.setVisible(false);

        AddSongPage.setVisible(false);
        SetupHome();
        // добавить для других панелей
    }
    SetupTopSongs setup = new SetupTopSongs();
    @FXML
    private void showTopSongs() {
       HomePage.setVisible(false);
        PlaylistScrollPane.setVisible(false);
        topSongsPage.setVisible(true);
        MySongsScrollPane.setVisible(false);
        PressButton(TopSongsBtn);
        AddSongPage.setVisible(false);
        top5(TopSongsTilePane );

        // добавить для других панелей
    }
    @FXML
    private void showMySongs(){
        HomePage.setVisible(false);
        PlaylistScrollPane.setVisible(false);
        topSongsPage.setVisible(false);
        MySongsScrollPane.setVisible(true);
        AddSongPage.setVisible(false);
        PressButton(MySongsBtn);
    }
    @FXML
    private void showAddSong(){
        HomePage.setVisible(false);
        PlaylistScrollPane.setVisible(false);
        topSongsPage.setVisible(false);
        MySongsScrollPane.setVisible(false);
        PressButton(AddSongBtn);
        AddSongPage.setVisible(true);
        Settings.loadArtists(artistSelector);
    }




    private void PressButton(Button button) {

        if (currentActiveButton != null) {
            UnpressButton(currentActiveButton);
        }
        button.setStyle("-fx-background-color: rgba(0, 0, 0, 0.57);");
        button.setOnMouseEntered(null);
        button.setOnMouseExited(null);
        currentActiveButton = button;
    }

    private void UnpressButton(Button button) {
        button.setStyle("-fx-background-color: #ffdcbd;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color:  rgba(0,0,0,0.57);"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #ffdcbd;"));
    }




  @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        button_logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "login.fxml", null);
            }
        });


      // Попытка установить соединение с базой данных
      try {
          connectToDatabase();
          System.out.println("Соединение с базой данных установлено!");
      } catch (SQLException e) {
          System.out.println("Ошибка при установлении соединения с базой данных: " + e.getMessage());
      }

        updateButtonVisibility(); // Установка начального состояния кнопок
        initializeSliders();
        initializeTimeline();
       playlistinitializer. initializePlaylist();
        showMySongs();


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
       isShtorkaOpen = animations.animateSize(isShtorkaOpen,Shtorka); // Запуск анимации изменения размеров
        animations.animateImage(isShtorkaOpen,UpperSongPhOpened,UpperSongPh); // Запуск анимации изображения
        animations.animateTextArea(isShtorkaOpen,SongTextArea,currentLyrics);
        animations.animateArtistSongName(isShtorkaOpen,ArtistSongNameText,UpperArtistName,UpperSongName);
        animations.animateSlider(isShtorkaOpen,OpenedSliderHBox,trackSlider);
        animations.animateButtons(isShtorkaOpen,OpenedButtonsHBox,UpperButtonsHBox,volumeSlider,volumeLabel);
        if(isShtorkaOpen){
            CloseShtorka.setVisible(true);
        }
        else{
            CloseShtorka.setVisible(false);
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
    int currentPlaylistId = -1;



        public void OpenPlaylist(int playlistId) {
            PlaylistPane.getChildren().removeIf(node -> node != ClosePlbtn);
            PlaylistScrollPane.setVisible(true);

            String query = "SELECT s.song_id, s.title, a.name AS artist, s.urlPhoto, s.duration\n" +
                    "FROM Songs s\n" +
                    "JOIN Artists a ON s.artist_id = a.artist_id\n" +
                    "JOIN Playlist_Songs ps ON s.song_id = ps.song_id\n" +
                    "WHERE ps.playlist_id = ?";

            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, playlistId);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    String songName = rs.getString("title");
                    String artistName = rs.getString("artist");
                    String urlPhoto = rs.getString("urlPhoto");
                    String duration = rs.getString("duration");
                    int songId = rs.getInt("song_id");


                    HBox songBox = new HBox(10);
                    songBox.setAlignment(Pos.CENTER_LEFT);
                    songBox.setPadding(new Insets(5, 10, 5, 10));

                    ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream(urlPhoto)));
                    imageView.setFitHeight(70);
                    imageView.setFitWidth(70);

                    Label nameLabel = new Label(songName);
                    nameLabel.setStyle("-fx-text-fill: white; -fx-font-size: 22;");

                    Label durationLabel = new Label(duration);
                    durationLabel.setStyle("-fx-text-fill: gray; -fx-font-size: 22;");

                    Label artistLabel = new Label(artistName);
                    artistLabel.setStyle("-fx-text-fill: gray; -fx-font-size: 19;");

                    VBox textVBox = new VBox(nameLabel, artistLabel,durationLabel);
                    textVBox.setAlignment(Pos.CENTER_LEFT);


                    songBox.getChildren().addAll(imageView, textVBox);

                    VBox container = new VBox(songBox);
                    Line separator = new Line(0, 0, 790, 0);
                    separator.setStrokeWidth(0.5);
                    separator.setStroke(Color.GRAY);
                    VBox.setMargin(separator, new Insets(10, 0, 2, 0));
                    container.getChildren().add(separator);

                    Button songButton = new Button();
                    songButton.setGraphic(container);

                    songButton.setOnMouseEntered(e -> songBox.setStyle("-fx-background-color: rgba(204, 204, 204, 0.5);"));
                    songButton.setOnMouseExited(e -> songBox.setStyle("-fx-background-color: transparent;"));
                    songButton.setStyle("-fx-background-color: transparent;");
                    songButton.setOnAction(event -> playSongPl(songId, playlistId));  // Adjust playSongPl method to handle songName or songId

                    PlaylistPane.getChildren().add(songButton);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

@FXML VBox karaokeVbox;



    public void playSongPl(int songId, int playlistId) {
        try {
            String sql = "SELECT s.*, a.name AS artist,ps.order_number\n" +
                    "FROM Songs s\n" +
                    "JOIN Artists a ON s.artist_id = a.artist_id\n" +
                    "JOIN playlist_songs ps ON s.song_id = ps.song_id\n" +
                    "WHERE s.song_id = ? AND ps.playlist_id = ?";

            if (playlistId == 0) {
                // Если playlistId равен 0, изменяем SQL-запрос
                sql = "SELECT s.*, a.name AS artist\n" +
                        "FROM Songs s\n" +
                        "JOIN Artists a ON s.artist_id = a.artist_id\n" +
                        "WHERE s.song_id = ?";
            }

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, songId);
            if (playlistId != 0) {
                statement.setInt(2, playlistId);

            }

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Создаем объект песни на основе данных из базы данных
                Songs song = new Songs(
                        resultSet.getInt("song_id"),
                        resultSet.getString("title"),
                        resultSet.getString("artist"),
                        resultSet.getString("genre"),
                        resultSet.getString("urlMusic"),
                        resultSet.getString("urlVocal"),
                        resultSet.getString("urlPhoto"),
                        resultSet.getString("urlLyric")
                );

                // Здесь продолжайте ваш код воспроизведения песни с использованием объекта song
                timeline.play();
                if (!isPlaying) {

                    MusicLib.stopDouble();
                    isPlaying = true;
                    MusicLib.playDouble(song.getUrlMusic(), song.getUrlVocal());
                    song.updateKaraokeVisbility(karaokeVbox,song.getUrlVocal());
                    if(playlistId!=0) {


                        currentIndex = resultSet.getInt("order_number");
                    }
                    currentPlaylistId = playlistId; // Сохраняем идентификатор текущего плейлиста
                    song.addCounter(song.getSongId());

                } else {
                    pause();
                    playSongPl(songId, playlistId);
                }

                Image image = new Image(new File("src/main/resources" + song.getUrlPhoto()).toURI().toString());
                UpperSongPh.setImage(image);
                UpperSongPhOpened.setImage(image);
                UpperSongName.setText(song.getName());
                UpperArtistName.setText(song.getArtist());
                UpperArtistName1.setText(song.getName());
                UpperSongName1.setText(song.getArtist());
                SongTextArea.setText(" ");
                currentLyrics = song.getUrlLyrics();
                updateButtonVisibility();
                durationLabel.setText(MusicLib.secondsToString(MusicLib.getTotalDuration()));
            } else {
                System.out.println("Песня с songId " + songId + " не найдена в базе данных.");
            }

            // Закрываем ресурсы
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void nextSong() {
    if (currentPlaylistId >0 && currentIndex < getMaxIndex(currentPlaylistId)) {
        currentIndex++;
        Animations.rotateImage(UpperSongPhOpened, 360);
        int songId = getSongIdByIndex(currentIndex, currentPlaylistId); // Получаем ID песни по порядковому номеру
        playSongPl(songId, currentPlaylistId);
    }
}

    public void previousSong() {
        if (currentPlaylistId >0 && currentIndex > 0) {
            currentIndex--;
            Animations.rotateImage(UpperSongPhOpened, -360);
            int songId = getSongIdByIndex(currentIndex, currentPlaylistId); // Получаем ID песни по порядковому номеру
            playSongPl(songId, currentPlaylistId);
        }
    }
    private int getSongIdByIndex(int index, int playlistId) {
        int songId = -1;  // Инициализируем переменную для ID песни
        try {
            // Подготавливаем SQL запрос для получения ID песни по порядковому номеру и ID плейлиста
            String sql = "SELECT song_id FROM playlist_songs WHERE playlist_id = ? AND order_number = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, playlistId);
            statement.setInt(2, index);

            // Выполняем запрос и обрабатываем результаты
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                songId = resultSet.getInt("song_id");
            }

            // Закрываем ресурсы
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return songId;  // Возвращаем ID песни
    }
    private int getMaxIndex(int playlistId) {
        int maxIndex = -1; // Инициализация с -1 для случая, когда запрос не возвращает результатов
        try {
            String sql = "SELECT MAX(order_number) AS max_order FROM playlist_songs WHERE playlist_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, playlistId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                maxIndex = resultSet.getInt("max_order");
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maxIndex;
    }




    public void SetupHome() {
        ExampleTilePAne.setStyle("-fx-background-color: black;");
        ExampleTilePAne.getChildren().clear();

        // Устанавливаем количество столбцов для TilePane
        PlaylistPane.setPrefColumns(3);  // Число столбцов

        try {
            // Запрос на получение плейлистов с user_id = 1
            String query = "SELECT * FROM playlists WHERE user_id = ? AND playlist_id <> 7";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, 1); // Пользователь с user_id = 1

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int playlistId = rs.getInt("playlist_id");
                String playlistName = rs.getString("title");
                String urlPhoto = rs.getString("urlPhoto");

                ImageView coverImageView = new ImageView(new Image(getClass().getResourceAsStream(urlPhoto)));
                coverImageView.setFitHeight(200);  // Высота изображения
                coverImageView.setFitWidth(200);   // Ширина изображения

                // Создаем текст для кнопки
                Label buttonText = new Label(playlistName);
                buttonText.setFont(Font.font("PT Sans Bold", 35));
                buttonText.setTextFill(Color.WHITE);

                // Создаем VBox для размещения текста и изображения
                VBox imageTextVBox = new VBox(20); // Увеличиваем промежуток между элементами в VBox
                imageTextVBox.getChildren().addAll(buttonText, coverImageView);
                imageTextVBox.setAlignment(Pos.CENTER);

                // Создаем кнопку
                Button playlistButton = new Button();
                playlistButton.setGraphic(imageTextVBox); // Устанавливаем VBox как графическое содержимое кнопки
                playlistButton.setStyle("-fx-background-color: transparent;"); // Прозрачный фон кнопки
                playlistButton.setMaxWidth(Double.MAX_VALUE);

                VBox outerVBox = new VBox(15);  // Внешний VBox для размещения кнопки с увеличенным отступом
                outerVBox.setPadding(new Insets(30)); // Добавляем внешний отступ
                outerVBox.getChildren().addAll(playlistButton); // Добавляем только кнопку
                outerVBox.setAlignment(Pos.CENTER);  // Выравнивание элементов внутри VBox по центру

                ExampleTilePAne.getChildren().add(outerVBox);  // Добавляем внешний VBox в TilePane

                playlistButton.setOnAction(event -> OpenPlaylist(playlistId));

                // Устанавливаем обработчики событий после добавления кнопки в родительский контейнер
                // Это гарантирует, что кнопка уже существует в момент установки обработчиков
                playlistButton.setOnMouseEntered(e -> playlistButton.setStyle("-fx-background-color:  rgba(204, 204, 204, 0.5);"));
                playlistButton.setOnMouseExited(e -> playlistButton.setStyle("-fx-background-color: transparent;"));
            }

            // Закрываем ресурсы
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void ClosePlaylist(){
    PlaylistScrollPane.setVisible(false);
}
public void SetupTopSongs(){

}


    @FXML
    protected  TextField searchField;

        @FXML protected ScrollPane searchScrollPane;

    @FXML
    private VBox LeftPaneButtonsVBox;
    @FXML
    private VBox searchVBox;
    public void keyListenerSearch(KeyEvent event) throws SQLException {
        if (event.getCode() == KeyCode.ENTER) {

            LeftPaneButtonsVBox.setVisible(false);
            searchScrollPane.setVisible(true);
            String searchText = searchField.getText();



            if (searchText.equalsIgnoreCase("bts")){
                System.exit(0);
            }



            if (!searchText.isEmpty()) {
                try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx-tynda", "root", "admin")) {
                    String sql = "SELECT * FROM SONGS s JOIN ARTISTS a ON s.artist_id = a.artist_id WHERE s.title LIKE CONCAT('%', ?, '%')";
                    try (PreparedStatement statement = connection.prepareStatement(sql)) {
                        statement.setString(1, searchText);
                        try (ResultSet resultSet = statement.executeQuery()) {

                            searchVBox.getChildren().clear();

                            // Создаем элементы контейнера и разделителя один раз перед циклом
                            VBox container = new VBox();
                            Line separator = new Line(0, 0, 790, 0);
                            separator.setStrokeWidth(0.5);
                            separator.setStroke(Color.GRAY);
                            VBox.setMargin(separator, new Insets(10, 0, 2, 0));


                            while (resultSet.next()) {
                                String urlPhoto = resultSet.getString("s.urlPhoto");
                                String title = resultSet.getString("s.title");
                                String artist = resultSet.getString("a.name");
                                int songId = resultSet.getInt("song_id");

                                ImageView imageView = new ImageView(new Image(new File("src/main/resources" + urlPhoto).toURI().toString()));
                                imageView.setFitHeight(70);
                                imageView.setFitWidth(70);

                                Label nameLabel = new Label(title);
                                nameLabel.setStyle("-fx-text-fill: black; -fx-font-size: 22;");

                                Label artistLabel = new Label(artist);
                                artistLabel.setStyle("-fx-text-fill: gray; -fx-font-size: 19;");

                                VBox textVBox = new VBox(nameLabel, artistLabel);
                                textVBox.setAlignment(Pos.CENTER_LEFT);

                                VBox songBox = new VBox(imageView, textVBox);
                                songBox.setAlignment(Pos.CENTER_LEFT);
                                songBox.setPadding(new Insets(5, 0, 5, 10));
                                Button songButton = new Button();
                                songBox.setPrefWidth(225);
                                songButton.setGraphic(songBox);

                                songBox.setOnMouseEntered(e -> songBox.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);"));
                                songBox.setOnMouseExited(e -> songBox.setStyle("-fx-background-color: transparent;"));
                                songButton.setStyle("-fx-background-color: transparent;");
                                songButton.setOnAction(e -> playSongPl(songId, 0));
                                container.getChildren().addAll(songButton, new Line(0, 0, 790, 0)); // Добавляем кнопку и линию после неё
                            }

                            searchVBox.getChildren().add(container); // Добавляем container в searchVBox
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                LeftPaneButtonsVBox.setVisible(true);
                searchScrollPane.setVisible(false);
            }
        }
    }



    @FXML
    javafx.scene.control.TextField songNameField;
    @FXML
    javafx.scene.control.TextField ArtistNameIdField;
    @FXML
    javafx.scene.control.TextField genreField;
    @FXML
    javafx.scene.control.TextField urlPhotoField;
    @FXML
    javafx.scene.control.TextField urlMusicField;
    @FXML
    javafx.scene.control.TextField urlVocalField;
    @FXML
    javafx.scene.control.TextField urlLyricsField;
    @FXML
    javafx.scene.control.TextField durationField;
    @FXML ComboBox<String> artistSelector;


    @FXML
    private void confirmButtonSendToSqlServer(ActionEvent event) {
        String songName = songNameField.getText();
        String selectedArtist = artistSelector.getValue();
        int artistId = -1;


        String genre = genreField.getText();

        FileChooser musicChooser = new FileChooser();
        musicChooser.setTitle("Select Music");

        File musicFile = musicChooser.showOpenDialog(null);
        String urlMusicFull = musicFile.toURI().toString();
        String delimetr = "src";
        int index = urlMusicFull.indexOf(delimetr);
        String urlMusic = null;
        if (index != -1) {
            urlMusic = urlMusicFull.substring(index);
        }
        System.out.println(urlMusic + " saved ");


        FileChooser vocalChooser = new FileChooser();
        vocalChooser.setTitle("Select Vocal");
        File vocalFile = vocalChooser.showOpenDialog(null);
        String urlVocal = null;
        if (vocalFile != null) {


            String urlVocalFull = vocalFile.toURI().toString();
            index = urlVocalFull.indexOf(delimetr);

            if (index != -1) {
                urlVocal = urlVocalFull.substring(index);
            }
        }else {
                urlVocal = null;
            }

            System.out.println(urlVocal + " saved");


            FileChooser photoChooser = new FileChooser();
            photoChooser.setTitle("Select Photo");
            File photoFile = photoChooser.showOpenDialog(null);
            String urlPhotoFull = photoFile.toURI().toString();
            delimetr = "/icons";
            index = urlPhotoFull.indexOf(delimetr);
            String urlPhoto = null;
            if (index != -1) {
                urlPhoto = urlPhotoFull.substring(index);
            }
            System.out.println(urlPhoto + " saved");


            FileChooser lyricChooser = new FileChooser();
            lyricChooser.setTitle("Select Lyric");
            File LyricFile = lyricChooser.showOpenDialog(null);
            String urlLyric = null;
            if (LyricFile != null) {
                String urlLyricFull = LyricFile.toURI().toString();
                delimetr = "src";
                index = urlLyricFull.indexOf(delimetr);

                if (index != -1) {
                    urlLyric = urlLyricFull.substring(index);
                }
            } else {
                urlLyric = null;
            }
            System.out.println(urlLyric + " saved");


            String duration = durationField.getText();

            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx-tynda", "root", "admin")) {
                // Получаем айди артиста
                String artistIdSql = "SELECT artist_id FROM artists WHERE name = ?";
                try (PreparedStatement artistStatement = connection.prepareStatement(artistIdSql)) {
                    artistStatement.setString(1, selectedArtist);
                    ResultSet resultSet = artistStatement.executeQuery();
                    if (resultSet.next()) {
                        artistId = resultSet.getInt("artist_id");
                    }
                }

                // Вставляем данные о песне в базу данных
                String sql = "INSERT INTO songs (title, artist_id, urlMusic, urlVocal, genre, duration, urlPhoto, urlLyric, counter) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, songName);
                    statement.setInt(2, artistId);
                    statement.setString(3, urlMusic);
                    statement.setString(4, urlVocal);
                    statement.setString(5, genre);
                    statement.setString(6, duration);
                    statement.setString(7, urlPhoto);
                    statement.setString(8, urlLyric);
                    statement.setInt(9, 0);
                    int rowsInserted = statement.executeUpdate();
                    if (rowsInserted > 0) {
                        System.out.println("Запись успешно добавлена в базу данных.");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Ошибка добавления");
            }
        }



    public void top5(TilePane pane) {
        setup.replacePlaylistSongsWithTopSongs(7, 10); // Предполагаем, что это метод обновляет песни в плейлисте на основе их популярности
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Запускаем соединение с базой данных
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx-tynda", "root", "admin");
            // SQL запрос выбирает песни из определенного плейлиста (id = 7) и сортирует их по популярности (counter)
            String sql = "SELECT s.song_id, s.title, a.name AS artist, s.urlPhoto, ps.order_number\n" +
                    "FROM Songs s \n" +
                    "JOIN Artists a ON s.artist_id = a.artist_id\n" +
                    "JOIN Playlist_Songs ps ON s.song_id = ps.song_id\n" +
                    "WHERE ps.playlist_id = 7 ORDER BY s.counter DESC LIMIT 10";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            pane.getChildren().clear(); // Очищаем TilePane от предыдущих элементов

            while (resultSet.next()) {
                String songName = resultSet.getString("title");
                String artistName = resultSet.getString("artist");
                String urlPhoto = resultSet.getString("urlPhoto");
                int songId = resultSet.getInt("song_id");

                int orderNumber = resultSet.getInt("order_number");


                HBox songBox = new HBox(10);
                songBox.setAlignment(Pos.CENTER_LEFT);
                songBox.setPadding(new Insets(5, 10, 5, 10));

                ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream(urlPhoto)));
                imageView.setFitHeight(70);
                imageView.setFitWidth(70);

                Label orderLabel = new Label(String.valueOf(orderNumber)); // Создаем Label для order_number
                orderLabel.setStyle("-fx-text-fill: white; -fx-font-size: 22;");


                Label nameLabel = new Label(songName);
                nameLabel.setStyle("-fx-text-fill: white; -fx-font-size: 22;");

                Label artistLabel = new Label(artistName);
                artistLabel.setStyle("-fx-text-fill: gray; -fx-font-size: 19;");

                VBox textVBox = new VBox(nameLabel, artistLabel);
                textVBox.setAlignment(Pos.CENTER_LEFT);

                songBox.getChildren().addAll(orderLabel,imageView, textVBox);

                VBox container = new VBox(songBox);
                Line separator = new Line(0, 0, 790, 0);
                separator.setStrokeWidth(0.5);
                separator.setStroke(Color.GRAY);
                VBox.setMargin(separator, new Insets(10, 0, 2, 0));
                container.getChildren().add(separator);

                Button songButton = new Button();
                songButton.setGraphic(container);
                songButton.setOnMouseEntered(e -> songBox.setStyle("-fx-background-color: rgba(204, 204, 204, 0.5);"));
                songButton.setOnMouseExited(e -> songBox.setStyle("-fx-background-color: transparent;"));
                songButton.setStyle("-fx-background-color: transparent;");
                songButton.setOnAction(event -> playSongPl(songId, 7));  // Adjust playSongPl method to handle songName or songId

                pane.getChildren().add(songButton);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    }



