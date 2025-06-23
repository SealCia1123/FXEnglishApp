package com.sealcia.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertUtils {
    private static AlertUtils instance;
    private Alert alert;

    private AlertUtils() {
        alert = new Alert(AlertType.INFORMATION);
    }

    public static AlertUtils getInstance() {
        if (instance == null) {
            instance = new AlertUtils();
        }
        return instance;
    }
    public void showMessage(String title, String msg) {
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
        alert.close();
    }
}
