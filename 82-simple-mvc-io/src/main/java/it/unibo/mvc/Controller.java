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
    private String currentPath;

    public Controller() {
        this.currentPath = System.getProperty("user.home") + 
            System.getProperty("file.separator") + "output.txt";
    }

    public void setCurrentFile(final File file) {
        this.currentFile = file;
        this.currentPath = file.getPath();
    }

    public File getCurrentFile() {
        return this.currentFile;
    }

    public String getCurrentPath() {
        return this.currentPath;
    }

    public void writeOnFile(final String s) throws IOException {
        try (final BufferedWriter writer = new BufferedWriter(new FileWriter(getCurrentPath()))) {
            writer.write(s);
            writer.newLine();
        } catch (IOException e) {
            throw new IOException("IOException: " + e);
        }
    }
}
