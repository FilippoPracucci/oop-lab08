package it.unibo.mvc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

/**
 * Application controller. Performs the I/O.
 */
public class Controller implements Serializable {

    private File currentFile;

    public Controller() {
        this.currentFile = System.getProperty("user.home") + 
            System.getProperty("file.separator") + "output.txt";
    }

    public void setCurrentFile(final File file) {
        this.currentFile = file;
    }

    public File getCurrentFile() {
        return this.currentFile;
    }

    public String getCurrentPath() {
        return getCurrentFile().getAbsolutePath();
    }

    public void writeOnFile(final String s) throws IOException {
        try (final BufferedWriter writer = new BufferedWriter(new FileWriter(getCurrentPath()))) {
            writer.write(s);
            writer.newLine();
        }
    }
}
