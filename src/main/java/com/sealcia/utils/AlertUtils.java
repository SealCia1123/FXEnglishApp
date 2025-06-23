package com.sealcia.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertUtils {
    private static AlertUtils instance;
    private static Alert alert;

    private AlertUtils() {
        alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("INFORMATION");
    }

    public static AlertUtils getInstance() {
        if (instance == null) {
            instance = new AlertUtils();
        }
        return instance;
    }

    public void showMessage(String msg) {
        alert.setContentText(msg);
        alert.show();
    }
}
