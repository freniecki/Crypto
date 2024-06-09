package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/StageFXML.fxml"));
        primaryStage.setScene(new Scene(loader.load()));
        MyController controller = loader.getController();
        controller.setPrimaryStage(primaryStage);
        primaryStage.setTitle("Schnorr");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
