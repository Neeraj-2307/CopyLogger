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

import java.awt.*;
import java.awt.datatransfer.*;
import java.io.IOException;


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


        //TODO: Create a welcome home stage, which the user can anyway close as trayicon will always be listening
        // Build the JavaFX UI (the main window)
        Stage dummyStage = new Stage();

//        dummyStage.initStyle(StageStyle.UTILITY);   // Prevents taskbar icon
//        dummyStage.setOpacity(0);                   // Fully transparent
//        dummyStage.setWidth(0);
//        dummyStage.setHeight(0);

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

    public static void main(String[] args) throws IOException, UnsupportedFlavorException {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        final String[] lastClipboardText = {""};
        Runnable clipboardMonitor = () -> {
            while (true) {
                try {
                    if (clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor)) {
                        String data = (String) clipboard.getData(DataFlavor.stringFlavor);
                        if (data != null && !data.equals(lastClipboardText[0])) {
                            lastClipboardText[0] = data;
                            System.out.println("Clipboard changed: " + data);
                        }
                    }
                    Thread.sleep(500); // check every 500ms
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        System.out.println("Clipboard watcher started...");
        new Thread(clipboardMonitor).start();
        launch(args);
    }
}