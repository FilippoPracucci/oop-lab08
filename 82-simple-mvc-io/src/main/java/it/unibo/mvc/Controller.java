package it.unibo.mvc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Serializable;

/**
 * Application controller. Performs the I/O.
 */
public class Controller implements Serializable {

    private static final long serialVersionUID = 1L;

    private File currentFile;
    private String currentPath;
    /**
     * Creates a new Controller.
     */
    public Controller() {
        this.currentPath = System.getProperty("user.home")
        + System.getProperty("file.separator") + "output.txt";
    }
    /**
     * @param file
     * set the file as the current one.
     */
    public final void setCurrentFile(final File file) {
        this.currentFile = file;
        this.currentPath = file.getPath();
    }
    /**
     * @return the current file.
     */
    public final File getCurrentFile() {
        return this.currentFile;
    }
    /**
     * @return the current path.
     */
    public final String getCurrentPath() {
        return this.currentPath;
    }
    /**
     * @param s that's a string.
     * write on a file the string.
     * @throws IOException
     */
    public final void writeOnFile(final String s) throws IOException {
        try (
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(getCurrentPath()), "UTF-8"))
        ) {
            writer.write(s);
            writer.newLine();
        }
    }
}
