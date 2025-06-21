package com.neeraj.copylogger;

import com.neeraj.copylogger.Stages.StageManager;
import com.neeraj.copylogger.listners.ClipboardManager;
import javafx.application.Application;
import javafx.stage.Stage;
import java.awt.datatransfer.*;
import java.io.IOException;


public class HelloApplication extends Application {


    @Override
    public void start(Stage stage) {
        final ClipboardManager clipboardManager = ClipboardManager.getClipboardManager();
        clipboardManager.initialise();
        StageManager stageManager = new StageManager(stage, clipboardManager.getAllCopiedTexts());
        stageManager.setMainStage();
        stageManager.setTrayIcons();
        stageManager.setupGlobalKeyListener();
    }

    public static void main(String[] args) throws IOException, UnsupportedFlavorException {
        launch(args);
    }
}