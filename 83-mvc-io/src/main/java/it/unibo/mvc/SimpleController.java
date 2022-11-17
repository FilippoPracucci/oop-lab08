package it.unibo.mvc;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * 
 *
 */
public final class SimpleController implements Controller {

    private final List<String> list = new LinkedList<>();
    private String nexString;

    @Override
    public void setNextString(final String s) {
        this.nexString = Objects.requireNonNull(nexString, "Null value is not acceptable.");
    }

    @Override
    public String getNextString() {
        return this.nexString;
    }

    @Override
    public List<String> getHistory() {
        return this.list;
    }

    @Override
    public void printCurrentString() {
        if (this.nexString == null) {
            throw new IllegalStateException("Current string is unset.");
        }
        this.list.add(this.nexString);
        System.out.println(this.nexString); //NOPMD: println allowed because is an exercise
    }

}
