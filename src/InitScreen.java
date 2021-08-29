/*
 * Copyright 2021. Eduardo Programador
 * www.eduardoprogramador.com
 *
 * */

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.awt.*;
import java.net.URI;

public class InitScreen extends Application implements EventHandler {

    private MenuBar menuBar;
    private Menu menuMode, menuOptions;
    private MenuItem itemAudio, itemImage, itemVideo;
    private MenuItem itemAbout, itemMore, itemContact;
    private VBox root;
    private Label labelTitle, labelSubtitle, labelFooter;
    private ImageView imageView;
    private Image image;
    private Scene scene;
    private LayoutTasks layoutTasks;
    private Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {

        layoutTasks = new LayoutTasks();

        itemAudio = new MenuItem("Áudio");
        itemImage = new MenuItem(("Imagem"));
        itemVideo = new MenuItem("Vídeo");
        menuMode = new Menu("Modo");
        menuMode.getItems().addAll(itemAudio,itemImage,itemVideo);
        menuOptions = new Menu("Opções");
        itemAbout = new MenuItem("Sobre");
        itemMore = new MenuItem("Mais Softwares");
        itemContact = new MenuItem("Contate o Desenvolvedor");
        menuOptions.getItems().addAll(itemAbout,itemMore,itemContact);
        menuBar = new MenuBar();
        menuBar.getMenus().addAll(menuMode,menuOptions);

        labelTitle = new Label("Multimedia Show");
        layoutTasks.setInTitle(labelTitle);
        labelSubtitle = new Label("MP3 Player, Video Player e Visualizador de Imagem");
        layoutTasks.setInSubtitle(labelSubtitle);
        labelFooter = new Label("Copyright 2021. Eduardo Programador");
        layoutTasks.setInFooter(labelFooter);

        root = new VBox();
        image = new Image("https://eduardoprogramador.com/img/ico.png");
        imageView = new ImageView(image);


        root.getChildren().addAll(menuBar,labelTitle,labelSubtitle,imageView,labelFooter);


        layoutTasks.setDefaultVbox(root);

        scene = new Scene(root,1000,550);
        this.stage = primaryStage;

        stage.setScene(scene);
        stage.setTitle("Multimedia Show");
        stage.getIcons().addAll(image);
        stage.setMaximized(true);
        stage.show();


        itemAudio.setOnAction(this::handle);
        itemVideo.setOnAction(this::handle);
        itemImage.setOnAction(this::handle);
        itemAbout.setOnAction(this::handle);
        itemMore.setOnAction(this::handle);
        itemContact.setOnAction(this::handle);
    }

    @Override
    public void handle(Event event) {
        if(event.getTarget().equals(itemAudio)) {
            new AudioScreen(menuBar,labelFooter,stage);
        } else if(event.getTarget().equals(itemImage)) {
            new ImageScreen(menuBar,labelFooter,stage);
        } else if(event.getTarget().equals(itemVideo)) {
            new VideoScreen(menuBar,labelFooter,stage);
        } else if(event.getTarget().equals(itemAbout)) {
            layoutTasks.displayDialog(Alert.AlertType.INFORMATION,"Sobre","Multimedia Show v1.0","Multimedia Show é um software leitor de mídias, funcionando como MP3 Player, visualizador de imagens e leitor de vídeos. O software é escrito inteiramente na linguagem Java e está disponível gratuitamente para uso.\n\nAutor: Eduardo Programador\nContato: consultoria@eduardoprogramador.com");
        } else if(event.getTarget().equals(itemMore)) {
            try {
                Desktop.getDesktop().browse(URI.create("https://eduardoprogramador.com/downloads.html"));
            } catch (Exception ex) {
                layoutTasks.displayDialog(Alert.AlertType.ERROR,"Falha de Execução","Falha de Execução","Não foi possível acessar a página de downloads do desenvolvedor do sistema");
            }
        } else if(event.getTarget().equals(itemContact)) {
            new ContactScreen(menuBar,labelFooter,stage);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }


}
