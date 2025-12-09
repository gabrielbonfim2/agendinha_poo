package org;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // AQUI ESTÁ O SEGREDO: Mude para "agenda-view.fxml"
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/org/agenda-view.fxml"));

        // Ajuste o tamanho para caber a tabela
        Scene scene = new Scene(fxmlLoader.load(), 700, 500);

        stage.setTitle("Minha Agenda - Hibernate");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}