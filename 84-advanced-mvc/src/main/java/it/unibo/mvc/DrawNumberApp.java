package it.unibo.mvc;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 */
public final class DrawNumberApp implements DrawNumberViewObserver {

    private static final String SEPARATOR = System.getProperty("file.separator");
    private static final String MIN_STRING = "min";
    private static final String MAX_STRING = "max";
    private static final String ATTEMPTS_STRING = "attempts";

    private final DrawNumber model;
    private final List<DrawNumberView> views;

    /**
     * @param views
     *            the views to attach
    */
    public DrawNumberApp(final DrawNumberView... views) {
        /*
         * Side-effect proof
         */
        this.views = Arrays.asList(Arrays.copyOf(views, views.length));
        for (final DrawNumberView view: views) {
            view.setObserver(this);
            view.start();
        }
        final Configuration.Builder builder = new Configuration.Builder();
        final Path path = Paths.get("src" + SEPARATOR + "main" + SEPARATOR + "resources" + SEPARATOR + "config.yml").toAbsolutePath();
        try (BufferedReader reader = new BufferedReader(new FileReader(path.toString()))) {
            String[] line = reader.readLine().split(": ");
            while (line != null) {
                if (line[0].contains(MIN_STRING)) {
                    builder.setMin(Integer.parseInt(line[1]));
                } else if (line[0].contains(MAX_STRING)) {
                    builder.setMax(Integer.parseInt(line[1]));
                } else if (line[0].contains(ATTEMPTS_STRING)) {
                    builder.setAttempts(Integer.parseInt(line[1]));
                }
                final String s = reader.readLine();
                if (s != null) {
                    line = s.split(": ");
                } else {
                    line = null;
                }
            }
            reader.close();
        } catch (IOException | NumberFormatException e) {
            displayError(e.getMessage());
        }
        final Configuration configuration = builder.build();
        if (configuration.isConsistent()) {
            this.model = new DrawNumberImpl(configuration);
        } else {
            displayError("Configuration is not consistent: "
                    + "min: " + configuration.getMin() + ", "
                    + "max: " + configuration.getMax() + ", "
                    + "attempts: " + configuration.getAttempts() + ". So using default settings."
            );
            this.model = new DrawNumberImpl(new Configuration.Builder().build());
        }
    }

    @Override
    public void newAttempt(final int n) {
        try {
            final DrawResult result = model.attempt(n);
            for (final DrawNumberView view: views) {
                view.result(result);
            }
        } catch (IllegalArgumentException e) {
            for (final DrawNumberView view: views) {
                view.numberIncorrect();
            }
        }
    }

    @Override
    public void resetGame() {
        this.model.reset();
    }

    @Override
    public void quit() {
        /*
         * A bit harsh. A good application should configure the graphics to exit by
         * natural termination when closing is hit. To do things more cleanly, attention
         * should be paid to alive threads, as the application would continue to persist
         * until the last thread terminates.
         */
        System.exit(0);
    }

    private void displayError(final String errorMsg) {
        for (final DrawNumberView view : views) {
            view.displayError(errorMsg);
        }
    }

    /**
     * @param args
     *            ignored
     * @throws FileNotFoundException 
     */
    public static void main(final String... args) throws FileNotFoundException {
        new DrawNumberApp(new DrawNumberViewImpl(),
                new DrawNumberViewImpl(),
                new PrintStreamView(System.out),
                new PrintStreamView(System.getProperty("user.home") + SEPARATOR + "output.txt")
        );
    }

}
