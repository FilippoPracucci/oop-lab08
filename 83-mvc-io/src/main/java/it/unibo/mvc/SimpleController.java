package it.unibo.mvc;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * 
 *
 */
public final class SimpleController implements Controller {

    private final List<String> list = new LinkedList<>();
    private String nextString;

    @Override
    public void setNextString(final String s) {
        this.nextString = Objects.requireNonNull(s, "Null value is not acceptable.");
    }

    @Override
    public String getNextString() {
        return this.nextString;
    }

    @Override
    public List<String> getHistory() {
        return Collections.unmodifiableList(this.list);
    }

    @Override
    public void printCurrentString() {
        if (this.nextString == null) {
            throw new IllegalStateException("Current string is unset.");
        }
        this.list.add(this.nextString);
        System.out.println(this.nextString); //NOPMD: println allowed because is an exercise
    }

}
