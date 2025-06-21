package com.neeraj.copylogger.Stages;


import com.dustinredmond.fxtrayicon.FXTrayIcon;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.util.LinkedList;

//Class responsible for handling and managing all the stages / UI related stuff
public class StageManager {

    private Stage mainStage;
    private Stage popupStage;
    private boolean ctrlPressed = false;
    private boolean shiftPressed = false;
    private LinkedList <String> copiedTexts;
    private Label stringLabel;
    private int currPage;

    public StageManager(Stage mainStage, LinkedList <String> copiedTexts) {
        this.mainStage = mainStage;
        this.copiedTexts = copiedTexts;
    }

    public void setMainStage() {
        StackPane root = new StackPane(new Label("Welcome to copylogger, you may close this window"));
        Scene scene = new Scene(root, 400, 300);
        mainStage.setScene(scene);
        mainStage.show();
    }

    public void setTrayIcons() {

        FXTrayIcon icon = new FXTrayIcon(mainStage);
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

    public void setupPopupStage() {
        stringLabel = new Label("Ctrl + Shift + V Pressed!");
        popupStage = new Stage();
        StackPane popupRoot = new StackPane(stringLabel);
        Scene popupScene = new Scene(popupRoot, 300, 150);
        popupStage.setScene(popupScene);
        popupStage.setTitle("Shortcut Triggered");
        currPage = copiedTexts.size()-1;
    }

    public void setupGlobalKeyListener() {
        setupPopupStage();
        try {
            GlobalScreen.registerNativeHook();
        } catch (Exception e) {
            e.printStackTrace();
        }

        GlobalScreen.addNativeKeyListener(new NativeKeyListener() {
            @Override
            public void nativeKeyPressed(NativeKeyEvent e) {
                if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL) ctrlPressed = true;
                if (e.getKeyCode() == NativeKeyEvent.VC_SHIFT) shiftPressed = true;
                if (ctrlPressed && shiftPressed && e.getKeyCode() == NativeKeyEvent.VC_LEFT) {
                    if(currPage > 0) {
                        currPage--;
                        System.out.println("curr = " + currPage);
                        Platform.runLater(() -> {
                            stringLabel.setText(copiedTexts.get(currPage));
                            if (!popupStage.isShowing()) {
                                popupStage.show();
                            } else {
                                popupStage.toFront();
                            }
                        });
                    }
                }
                if(ctrlPressed && shiftPressed && e.getKeyCode() == NativeKeyEvent.VC_RIGHT) {
                    if(currPage < copiedTexts.size()-1) {
                        currPage++;
                        Platform.runLater(() -> {
                            stringLabel.setText(copiedTexts.get(currPage));
                            if (!popupStage.isShowing()) {
                                popupStage.show();
                            } else {
                                popupStage.toFront();
                            }
                        });
                    }
                }
                if (e.getKeyCode() == NativeKeyEvent.VC_V && ctrlPressed && shiftPressed && !copiedTexts.isEmpty()) {
                    Platform.runLater(() -> {
                        stringLabel.setText(copiedTexts.get(currPage));
                        if (!popupStage.isShowing()) {
                            popupStage.show();
                        } else {
                            popupStage.toFront();
                        }
                    });
                }
            }

            @Override
            public void nativeKeyReleased(NativeKeyEvent e) {
                if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL) ctrlPressed = false;
                if (e.getKeyCode() == NativeKeyEvent.VC_SHIFT) shiftPressed = false;
                // If any of the required keys are released, close the popup
                if (!ctrlPressed || !shiftPressed) {
                    Platform.runLater(() -> {
                        if (popupStage.isShowing()) {
                            popupStage.close();
                        }
                    });
                    currPage = copiedTexts.size()-1;
                }
            }
        });
    }
}
