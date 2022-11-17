package it.unibo.mvc;

import java.util.List;

/**
 *
 */
public interface Controller {

    /**
     * 
     * @param s is the next string to print
     */
    void setNextString(String s);

    /**
     * 
     * @return the next string to print
     */
    String getNextString();

    /**
     * 
     * @return the history of the printed strings as list of strings
     */
    List<String> getHistory();

    /**
     * 
     */
    void printCurrentString();
}
