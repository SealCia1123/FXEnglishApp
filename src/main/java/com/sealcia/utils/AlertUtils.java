package com.sealcia.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertUtils {
    private AlertUtils() {}

    public static void showAlertMessage(AlertType type, String content) {
        Alert alert = new Alert(type, content);
        alert.showAndWait();
    }

    public static Alert getAlert(AlertType type, String content) {
        return new Alert(type, content);
    }
}
