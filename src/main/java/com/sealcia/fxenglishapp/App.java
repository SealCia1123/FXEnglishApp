package com.sealcia.fxenglishapp;

import com.sealcia.utils.JdbcUtils;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("manage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("English App");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        JdbcUtils.getInstance().close();
    }

    public static void main(String[] args) {
        launch();
    }
}
