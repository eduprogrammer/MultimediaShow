/*
 * Copyright 2021. Eduardo Programador
 * www.eduardoprogramador.com
 *
 * */

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ContactScreen extends LayoutTasks implements Builder, EventHandler {

    private MenuBar menuBar;
    private Label labelFooter;
    private Stage stage;
    private TextField fieldName, fieldSurname, fieldEmail, fieldPhone;
    private Button btnSend;
    private TextArea textArea;


    public ContactScreen(MenuBar menuBar, Label labelFooter, Stage stage) {
        this.menuBar = menuBar;
        this.labelFooter = labelFooter;
        this.stage = stage;

        setUI();

    }


    @Override
    public void setUI() {

        Label labelTitle = new Label("Contate o Desenvolvedor");
        setInTitle(labelTitle);
        Label labelSubtitle = new Label("Preencha o formulário com seus dados para receber uma resposta do Programador");
        setInSubtitle(labelSubtitle);

        Label labelName = new Label("Nome:");
        fieldName = new TextField();
        Label labelSurname = new Label("Sobrenome:");
        fieldSurname = new TextField();
        HBox hBoxOne = new HBox();
        hBoxOne.getChildren().addAll(labelName,fieldName,labelSurname,fieldSurname);
        setDefaultHbox(hBoxOne);

        Label labelEmail = new Label("E-mail:");
        fieldEmail = new TextField();
        Label labelPhone = new Label("Telefone:");
        fieldPhone = new TextField();
        HBox hBoxTwo = new HBox();
        hBoxTwo.getChildren().addAll(labelEmail,fieldEmail,labelPhone,fieldPhone);
        setDefaultHbox(hBoxTwo);

        Label labelMsg = new Label("Mensagem:");
        textArea = new TextArea();

        btnSend = new Button("Enviar");
        btnSend.setOnAction(this::handle);

        VBox root = new VBox();
        setDefaultVbox(root);
        root.getChildren().addAll(menuBar,labelTitle,labelSubtitle,hBoxOne,hBoxTwo,labelMsg,textArea,btnSend,labelFooter);

        Scene scene = new Scene(root,1000,550);
        stage.setScene(scene);
        stage.hide();
        stage.show();
        stage.setMaximized(true);
    }

    @Override
    public void handle(Event event) {

        if(event.getTarget().equals(btnSend)) {

            String name = fieldName.getText();
            String surname = fieldName.getText();
            String email = fieldEmail.getText();
            String phone = fieldPhone.getText();
            String msg = textArea.getText();

            if(!submit(name,surname,email,phone,msg)) {
                new LayoutTasks().displayDialog(Alert.AlertType.ERROR,"Erro de Formulário","Erro de Formulário","Um erro ocorreu ao enviar os dados do formulário. Tente novamente!");
            } else {
                new LayoutTasks().displayDialog(Alert.AlertType.INFORMATION,"Enviado com Sucesso","Dados Enviados com Sucesso","Aguarde o contato do desenvolvedor para a resposta desejada");
            }
        }
    }

    private boolean submit(String name, String surname, String email,String phone, String msg) {
        Proposal proposal = new Proposal();
        return proposal.send(name,surname,email,phone,msg);
    }
}
