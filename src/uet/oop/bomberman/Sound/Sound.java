package uet.oop.bomberman.Sound;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class Sound {
    public final static Media media1 = new Media(new File("res/sound/menu.mp3").toURI().toString());
    public final static Media media2 = new Media(new File("res/sound/game.mp3").toURI().toString());
    public final static Media media3 = new Media(new File("res/sound/set_bomb.wav").toURI().toString());
    public final static Media media4 = new Media(new File("res/sound/bomb_explode.mp3").toURI().toString());
    public final static Media media5 = new Media(new File("res/sound/eat_item.wav").toURI().toString());
    public final static Media media6 = new Media(new File("res/sound/next_level.mp3").toURI().toString());
    public final static Media media7 = new Media(new File("res/sound/life_lost.mp3").toURI().toString());
    public final static Media media8 = new Media(new File("res/sound/lose.mp3").toURI().toString());
    public final static Media media9 = new Media(new File("res/sound/win.mp3").toURI().toString());
    public final static Media media10 = new Media(new File("res/sound/bomber_vs_enemy.mp3").toURI().toString());

    public final static MediaPlayer menuSound = new MediaPlayer(media1);
    public final static MediaPlayer gameSound = new MediaPlayer(media2);
    public final static MediaPlayer setBombSound = new MediaPlayer(media3);
    public final static MediaPlayer bombExplodeSound = new MediaPlayer(media4);
    public final static MediaPlayer eatItemSound = new MediaPlayer(media5);
    public final static MediaPlayer levelUpSound = new MediaPlayer(media6);
    public final static MediaPlayer BomberDie = new MediaPlayer(media7);
    public final static MediaPlayer loseSound = new MediaPlayer(media8);
    public final static MediaPlayer winSound = new MediaPlayer(media9);
    public final static MediaPlayer bomberDie = new MediaPlayer(media10);

    public static void playMedia(MediaPlayer mediaPlayer) {
        mediaPlayer.play();
        mediaPlayer.seek(mediaPlayer.getStartTime());
        mediaPlayer.setVolume(0.5);
    }

    public static void playLoopMedia(MediaPlayer mediaPlayer) {
        mediaPlayer.play();
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.seek(mediaPlayer.getStartTime());
        mediaPlayer.setVolume(0.3);
    }
}
