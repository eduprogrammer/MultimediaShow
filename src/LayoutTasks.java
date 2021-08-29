/*
 * Copyright 2021. Eduardo Programador
 * www.eduardoprogramador.com
 *
 * */

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class LayoutTasks {



    public LayoutTasks() {

    }


    public void setInTitle(Label label) {
        label.setFont(new Font("arial",40));
    }

    public void setInSubtitle(Label label) {
        label.setFont(new Font("arial",25));
    }

    public void setInFooter(Label label) {
        label.setFont(new Font("arial",20));
    }

    public void setDefaultVbox(VBox vbox) {
        vbox.setPadding(new Insets(10,10,10,10));
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(20);
    }

    public void setDefaultHbox(HBox hbox) {
        hbox.setPadding(new Insets(10,10,10,10));
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(10);
    }

    public void displayDialog(Alert.AlertType alertType, String title,String header,String content) {
        Alert alert = new Alert(alertType,content);
        alert.setHeaderText(header);
        alert.setTitle(title);
        alert.show();
    }
}
