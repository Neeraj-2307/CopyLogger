package com.neeraj.copylogger;


import com.dustinredmond.fxtrayicon.FXTrayIcon;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


//import java.awt.*;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.awt.image.BufferedImage;


public class HelloApplication extends Application {
//    private TrayIcon trayIcon;
    private Stage mainStage;

    @Override
    public void start(Stage stage) {
        this.mainStage = stage;

        // Build the JavaFX UI (the main window)
        Stage dummyStage = new Stage();
        dummyStage.initStyle(StageStyle.UTILITY);   // Prevents taskbar icon
        dummyStage.setOpacity(0);                   // Fully transparent
        dummyStage.setWidth(0);
        dummyStage.setHeight(0);

        // Pass in the app's main stage, and path to the icon image
        FXTrayIcon icon = new FXTrayIcon(dummyStage);
        // Create JavaFX ContextMenu
        MenuItem showItem = new MenuItem("Show Copied Text");
        MenuItem exitItem = new MenuItem("Exit");

        showItem.setOnAction(e -> Platform.runLater(() -> {
            System.out.println("Show Item is called");
        }));

        exitItem.setOnAction(e -> {
            Platform.exit();
            System.exit(0);
        });
        icon.addMenuItem(showItem);
        icon.addSeparator();
        icon.addMenuItem(exitItem);
        icon.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}