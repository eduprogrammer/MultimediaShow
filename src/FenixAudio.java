/*
 * Copyright 2021. Eduardo Programador
 * www.eduardoprogramador.com
 *
 * */

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import java.io.File;
import java.util.ArrayList;

public class FenixAudio {

    private MediaPlayer mediaPlayer;
    private ArrayList<String> playList;
    private Media media;
    private int currentTrack;
    private double inSeconds;
    private String totalDuration;


    private FenixAudio(ArrayList<String> audios) {
        this.playList = audios;
        media = new Media(new File(audios.get(0)).toURI().toString());
        mediaPlayer = new MediaPlayer(media);

    }

    public static FenixAudio getInstance(ArrayList<String> audios) {
        return new FenixAudio(audios);
    }


    public String getStatus() {
        return mediaPlayer.getStatus().name();
    }

    public String getAudioName() {
        return playList.get(currentTrack);
    }

    public String getAudioNames() {
        return playList.toString();
    }

    public void start() {
        mediaPlayer.play();
    }


    public void goForward() {
        currentTrack += 1;
        if(playList.size() <= currentTrack) {
            currentTrack -= 1;
        } else {
            String path = playList.get(currentTrack);
            media = new Media(new File(path).toURI().toString());
            mediaPlayer.stop();
            mediaPlayer.dispose();
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
        }
    }

    public void goBack() {
        if(currentTrack != 0) {

            currentTrack -= 1;
            String path = playList.get(currentTrack);
            media = new Media(new File(path).toURI().toString());
            mediaPlayer.stop();
            mediaPlayer.dispose();
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
        }
    }

    public void calculateDuration(Runnable function) {
        mediaPlayer.setOnReady(function);
    }

    public double getTotalDuration() {
        return mediaPlayer.getTotalDuration().toSeconds();
    }


    public void setTrack(double value) {
        mediaPlayer.seek(new Duration(value * 1000));
    }

    public void setVolume(double volume) {
        mediaPlayer.setVolume(volume);
    }

    public MediaPlayer getPlayer() {
        return mediaPlayer;
    }

}
