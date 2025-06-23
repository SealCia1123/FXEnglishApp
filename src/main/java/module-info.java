module com.sealcia.fxenglishapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;
    requires lombok;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires javafx.swing;
    opens com.sealcia.fxenglishapp to javafx.fxml;

    exports com.sealcia.pojo;
    exports com.sealcia.services;
    exports com.sealcia.utils;
    exports com.sealcia.fxenglishapp;
}
