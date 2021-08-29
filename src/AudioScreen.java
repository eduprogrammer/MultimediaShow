/*
* Copyright 2021. Eduardo Programador
* www.eduardoprogramador.com
*
* */

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.ArrayList;

public class AudioScreen extends LayoutTasks implements EventHandler {

    private MenuBar menuBar;
    private Label labelFooter, labelStatus, labelAudio, labelTrack;
    private Stage stage;
    private Button btnSearch;
    private CommonTasks commonTasks;
    private FenixAudio fenixAudio;
    private TextField fieldChoose;
    private Button btnPlay, btnBack, btnForward;
    private Slider sliderCurrent, sliderVolume;
    private int i, minCur, secCur, minTotal, secTotal;
    private boolean sliderControl;
    private long now,after;


    public AudioScreen(MenuBar menuBar, Label labelFooter, Stage stage) {
        this.menuBar = menuBar;
        this.labelFooter = labelFooter;
        this.stage = stage;
        commonTasks = CommonTasks.getInstance();

        createAudioLayout();
    }


    private void createAudioLayout() {

        Label labelTitle = new Label("Multimedia Show");
        setInTitle(labelTitle);
        Label labelSubtitle = new Label("Áudio");
        setInSubtitle(labelSubtitle);
        Label labelChoose = new Label("Escolher Músicas:");
        fieldChoose = new TextField();
        btnSearch = new Button("Buscar");
        HBox hBoxOne = new HBox();
        setDefaultHbox(hBoxOne);
        hBoxOne.getChildren().addAll(labelChoose,fieldChoose,btnSearch);

        labelStatus = new Label("Status: xxxx");
        labelAudio = new Label("Áudio: xxxx");

        btnBack = new Button("Voltar");
        btnPlay = new Button("Tocar");
        btnForward = new Button("Avançar");
        HBox hBoxTwo = new HBox();
        setDefaultHbox(hBoxTwo);
        hBoxTwo.getChildren().addAll(btnBack,btnPlay,btnForward);

        sliderCurrent = new Slider();
        labelTrack = new Label("Faixa:");
        sliderCurrent.backgroundProperty().set(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        HBox hBoxThree = new HBox();
        hBoxThree.getChildren().addAll(labelTrack,sliderCurrent);
        setDefaultHbox(hBoxThree);

        Label labelVolume = new Label("Volume:");
        sliderVolume = new Slider();
        HBox hBoxFour = new HBox();
        setDefaultHbox(hBoxFour);
        hBoxFour.getChildren().addAll(labelVolume,sliderVolume);

        VBox root = new VBox();
        setDefaultVbox(root);
        root.getChildren().addAll(menuBar,labelTitle,labelSubtitle,labelStatus,labelAudio,hBoxOne,hBoxTwo,hBoxThree,hBoxFour,labelFooter);

        Scene scene = new Scene(root,1000,550);
        stage.setScene(scene);
        stage.hide();
        stage.show();
        stage.setMaximized(true);


        btnSearch.setOnAction(this::handle);
        btnPlay.setOnAction(this::handle);

        sliderCurrent.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                sliderControl = true;

                double changed = (double) newValue;
                sliderCurrent.setValue(changed);
                fenixAudio.setTrack(changed);

                after = System.currentTimeMillis();
                if((after - now) == 1000) {
                    now += 1000;

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {

                            if(secCur > 58) {
                                minCur += 1;
                                secCur = 0;
                                sliderCurrent.setValue((minCur * 60) + secCur);


                            } else {
                                secCur += 1;
                                sliderCurrent.setValue((minCur * 60) + secCur);
                            }
                        }
                    });
                }


            }
        });

        sliderVolume.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                double vol = (double) newValue;

                fenixAudio.setVolume(vol);
                sliderVolume.setValue(vol);
            }
        });

        btnForward.setOnAction(this::handle);
        btnBack.setOnAction(this::handle);

    }

    @Override
    public void handle(Event event) {
        if(event.getTarget().equals(btnSearch)) {

            ArrayList<String> files = commonTasks.getListOfFiles("Selecionar Áudio",stage,"Áudio",new String[]{"*.mp3"});
            if(files != null) {

                fenixAudio = FenixAudio.getInstance(files);

                labelStatus.setText("Status: " + fenixAudio.getStatus());
                labelAudio.setText("Áudio: " + fenixAudio.getAudioName());
                fieldChoose.setText(fenixAudio.getAudioNames());
                fenixAudio.calculateDuration(new Runnable() {
                    @Override
                    public void run() {
                        double inSeconds = fenixAudio.getTotalDuration();

                        int filtered = (int) inSeconds; //219s
                        minTotal = filtered / 60; // 3min
                        int exceed = minTotal * 60; //180s
                        secTotal = filtered - exceed;

                        sliderCurrent.setMax((minTotal * 60) + secTotal);

                        sliderVolume.setMax(1.0);

                    }
                });


            }
        } else if(event.getTarget().equals(btnPlay)) {
            fenixAudio.start();
            labelStatus.setText("Status: " + fenixAudio.getStatus());

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        now = System.currentTimeMillis();

                        for(;;) {

                            if(!sliderControl) {
                                after = System.currentTimeMillis();
                                if((after - now) == 1000) {
                                    now += 1000;

                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {

                                            if(secCur > 58) {
                                                minCur += 1;
                                                secCur = 0;
                                                sliderCurrent.setValue((minCur * 60) + secCur);
                                            } else {
                                                secCur += 1;
                                                sliderCurrent.setValue((minCur * 60) + secCur);
                                            }
                                        }
                                    });
                                }
                            }


                        }

                    }
                }).start();




        } else if(event.getTarget().equals(btnBack)) {
            fenixAudio.goBack();

        } else if(event.getTarget().equals(btnForward)) {
            fenixAudio.goForward();
        }
    }


}
