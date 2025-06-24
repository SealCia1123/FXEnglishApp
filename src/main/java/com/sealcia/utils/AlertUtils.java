package com.sealcia.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertUtils {
    public static Alert getAlert(AlertType type, String content) {
        Alert alert = new Alert(type);
        alert.setContentText(content);
        return alert;
    }
}
