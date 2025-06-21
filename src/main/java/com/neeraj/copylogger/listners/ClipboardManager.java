package com.neeraj.copylogger.listners;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.util.LinkedList;

//Singleton class responsible for handling all the clipboard changes
public class ClipboardManager {

    private final LinkedList<String> copiedTexts;

    public static volatile ClipboardManager clipBoardManager;

    private ClipboardManager() {
        this.copiedTexts = new LinkedList<>();
    }

    public static ClipboardManager getClipboardManager() {
        ClipboardManager result = clipBoardManager;
        if(result == null) {
            synchronized (ClipboardManager.class) {
                result = clipBoardManager;
                if(result == null) {
                    result = clipBoardManager = new ClipboardManager();
                }
            }
        }
        return result;
    }

    //Method responsible for handling all the clipboard texts management
    public void initialise() {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        Runnable clipboardMonitor = () -> {
            while (true) {
                try {
                    String lastClipboardText;
                    if(!copiedTexts.isEmpty()) {
                        lastClipboardText = copiedTexts.getLast();
                    } else {
                        lastClipboardText = "";
                    }
                    if (clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor)) {
                        String data = (String) clipboard.getData(DataFlavor.stringFlavor);
                        if (data != null && !data.equals(lastClipboardText)) {
                            addText(data);
                        }
                    }
                    Thread.sleep(500); // check every 500ms
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(clipboardMonitor).start();
    }
    private void addText(String newText) {
        copiedTexts.addLast(newText);
        if(copiedTexts.size() == 40) {
            copiedTexts.removeFirst();
        }
    }

    public LinkedList<String> getAllCopiedTexts() {
        return copiedTexts;
    }
}
