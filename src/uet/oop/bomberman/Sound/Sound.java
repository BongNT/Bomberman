package uet.oop.bomberman.Sound;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

public class Sound {
    static Media media1 = new Media(new File("res/sound/menu.mp3").toURI().toString());
    static Media media2 = new Media(new File("res/sound/game.mp3").toURI().toString());
    static Media media3 = new Media(new File("res/sound/set_bomb.wav").toURI().toString());
    static Media media4 = new Media(new File("res/sound/bomb_explode.mp3").toURI().toString());
    static Media media5 = new Media(new File("res/sound/eat_item.wav").toURI().toString());
    static Media media6 = new Media(new File("res/sound/next_level.mp3").toURI().toString());
    static Media media7 = new Media(new File("res/sound/life_lost.mp3").toURI().toString());
    static Media media8 = new Media(new File("res/sound/lose.mp3").toURI().toString());
    static Media media9 = new Media(new File("res/sound/win.mp3").toURI().toString());
    static Media media10 = new Media(new File("res/sound/bomber_vs_enemy.mp3").toURI().toString());

    public static MediaPlayer menuSound = new MediaPlayer(media1);
    public static MediaPlayer gameSound = new MediaPlayer(media2);
    public static MediaPlayer setBombSound = new MediaPlayer(media3);
    public static MediaPlayer bombExplodeSound = new MediaPlayer(media4);
    public static MediaPlayer eatItemSound = new MediaPlayer(media5);
    public static MediaPlayer levelUpSound = new MediaPlayer(media6);
    public static MediaPlayer BomberDie = new MediaPlayer(media7);
    public static MediaPlayer loseSound = new MediaPlayer(media8);
    public static MediaPlayer winSound = new MediaPlayer(media9);
    public static MediaPlayer bomberVsEnemySound = new MediaPlayer(media10);

    public static void playMedia(MediaPlayer mediaPlayer) {
        mediaPlayer.play();
        mediaPlayer.seek(mediaPlayer.getStartTime());
        mediaPlayer.setVolume(0.5);
    }
    public static void playMedia(MediaPlayer mediaPlayer, double start, double end) {
        mediaPlayer.play();
        mediaPlayer.seek(mediaPlayer.getStartTime());
        mediaPlayer.setVolume(0.5);
       // mediaPlayer.
    }
}
