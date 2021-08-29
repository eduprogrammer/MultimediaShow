/*
 * Copyright 2021. Eduardo Programador
 * www.eduardoprogramador.com
 *
 * */

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.util.ArrayList;

public class ImageScreen extends LayoutTasks implements EventHandler {

    private MenuBar menuBar;
    private Label labelFooter, labelImg;
    private Stage stage;
    private Button btnChoose, btnBack, btnForward;
    private CommonTasks commonTasks;
    private TextField fieldChoose;
    private Image image;
    private ImageView imageView;
    private ArrayList<String> photos;
    private int cur;

    public ImageScreen(MenuBar menuBar, Label labelFooter, Stage stage) {

        this.menuBar = menuBar;
        this.labelFooter = labelFooter;
        this.stage = stage;

        createImgLayout();
        commonTasks = CommonTasks.getInstance();
    }


    private void createImgLayout() {

        Label labelTitle = new Label("Multimedia Show");
        setInTitle(labelTitle);
        Label labelSubtitle = new Label("Imagem");
        setInSubtitle(labelSubtitle);

        Label labelChoose = new Label("Escolher Imagem:");
        fieldChoose = new TextField();
        btnChoose = new Button("Carregar");
        HBox hBoxOne = new HBox();
        hBoxOne.getChildren().addAll(labelChoose,fieldChoose,btnChoose);
        setDefaultHbox(hBoxOne);

        imageView = new ImageView();
        Image image = new Image("https://eduardoprogramador.com/img/ico.png");
        imageView.setImage(image);
        imageView.setFitHeight(350);
        imageView.setFitWidth(800);

        labelImg = new Label("Nome da Imagem:");

        btnBack = new Button("Anterior");
        btnForward = new Button("Próxima");
        HBox hboxTwo = new HBox();
        hboxTwo.getChildren().addAll(btnBack,btnForward);
        setDefaultHbox(hboxTwo);

        VBox root = new VBox();
        root.getChildren().addAll(menuBar,labelTitle,labelSubtitle,hBoxOne,imageView,labelImg,hboxTwo,labelFooter);
        root.setSpacing(5);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root,1000,550);
        stage.setScene(scene);
        stage.hide();
        stage.show();
        stage.setMaximized(true);

        btnChoose.setOnAction(this::handle);
        btnBack.setOnAction(this::handle);
        btnForward.setOnAction(this::handle);

    }

    @Override
    public void handle(Event event) {
        if(event.getTarget().equals(btnChoose)) {
            photos = commonTasks.getListOfFiles("Selecionar Imagens",stage,"Imagens",new String[]{"*.jpg","*.jpeg","*.png","*.gif","*.bmp"});
            if(photos != null) {
                fieldChoose.setText(photos.toString());
                labelImg.setText("Nome da Imagem: " + photos.get(0));

                image = readImg(photos.get(0));
                if(image != null) {
                    imageView.setImage(image);
                } else {
                    displayDialog(Alert.AlertType.ERROR,"Falha de Leitura","Erro de Leitura","Um erro ocorreu no processamento da imagem");
                }

            }
        } else if(event.getTarget().equals(btnBack)) {
            if(cur > 0) {
                cur--;
                labelImg.setText("Nome da Imagem: " + photos.get(cur));
                image = readImg(photos.get(cur));
                if(image != null) {
                    imageView.setImage(image);
                } else {
                    displayDialog(Alert.AlertType.ERROR,"Falha de Leitura","Erro de Leitura","Um erro ocorreu no processamento da imagem");
                }

            }
        } else if(event.getTarget().equals(btnForward)) {
            if(cur < photos.size() - 1) {
                cur++;
                labelImg.setText("Nome da Imagem: " + photos.get(cur));
                image = readImg(photos.get(cur));
                if(image != null) {
                    imageView.setImage(image);
                } else {
                    displayDialog(Alert.AlertType.ERROR,"Falha de Leitura","Erro de Leitura","Um erro ocorreu no processamento da imagem");
                }
            }
        }
    }

    private Image readImg(String path) {
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            Image image = new Image(fileInputStream);
            fileInputStream.close();
            return image;
        } catch (Exception ex) {
            return null;
        }
    }
}
