package com.sealcia.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertUtils {
    private static final AlertUtils instance = new AlertUtils();

    private AlertUtils() {}

    public static AlertUtils getInstance() {
        return instance;
    }

    public Alert getAlert(AlertType type, String content) {
        Alert alert = new Alert(type);
        alert.setContentText(content);
        return alert;
    }
}
