/*
 * Copyright 2021. Eduardo Programador
 * www.eduardoprogramador.com
 *
 * */

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CommonTasks {

    private CommonTasks() {

    }

    public static CommonTasks getInstance() {
        return new CommonTasks();
    }

    public ArrayList<String> getListOfFiles(String title, Stage stage,String typeOfFiles,String[] extensions) {
        ArrayList<String> res = new ArrayList<>();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        List<String> ext = new ArrayList<>();
        for (int i = 0; i < extensions.length; i++) {
            ext.add(extensions[i]);
        }

        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter(typeOfFiles,ext);
        fileChooser.getExtensionFilters().add(filter);
        List<File> files = fileChooser.showOpenMultipleDialog(stage);

        if(files != null) {

            for (int i = 0; i < files.size() ; i++) {

                String path = files.get(i).getPath();
                res.add(path);
            }

            return res;
        } else {
            return null;
        }


    }

}
