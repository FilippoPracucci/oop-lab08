package it.unibo.mvc;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 * Encapsulates the concept of configuration.
 */
public final class Configuration {

    private final int max; 
    private final int min;
    private final int attempts;

    private Configuration(final int max, final int min, final int attempts) {
        this.max = max;
        this.min = min;
        this.attempts = attempts;
    }

    /**
     * @return the maximum value
     */
    public int getMax() {
        return max;
    }

    /**
     * @return the minimum value
     */
    public int getMin() {
        return min;
    }

    /**
     * @return the number of attempts
     */
    public int getAttempts() {
        return attempts;
    }

    /**
     * @return true if the configuration is consistent
     */
    public boolean isConsistent() {
        return attempts > 0 && min < max;
    }

    /**
     * Pattern builder: used here because:
     * 
     * - all the parameters of the Configuration class have a default value, which
     * means that we would like to have all the possible combinations of
     * constructors (one with three parameters, three with two parameters, three
     * with a single parameter), which are way too many and confusing to use
     * 
     * - moreover, it would be impossible to provide all of them, because they are
     * all of the same type, and only a single constructor can exist with a given
     * list of parameter types.
     * 
     * - the Configuration class has three parameters of the same type, and it is
     * unclear to understand, in a call to its contructor, which is which. By using
     * the builder, we emulate the so-called "named arguments".
     * 
     */
    public static class Builder {

        private static final int MIN = 0;
        private static final int MAX = 100;
        private static final int ATTEMPTS = 10;
        private static final String SEPARATOR = System.getProperty("file.separator");
        private static final String PATH = "src" + SEPARATOR + "main" + SEPARATOR
                + "resources" + SEPARATOR +"config.yml";
        private static final String MINIMUM_STRING = "minimum:";
        private static final String MAXIMUM_STRING = "maximum:";
        private static final String ATTEMPTS_STRING = "attempts:";

        private int min = MIN;
        private int max = MAX;
        private int attempts = ATTEMPTS;
        private boolean consumed = false;
        private StringTokenizer tokenizer;

        /**
         * @param min the minimum value
         * @return this builder, for method chaining
         * @throws IOException
         * @throws FileNotFoundException
         */
        public Builder setMin(final int min) throws FileNotFoundException, IOException {
            try {
                this.min = Integer.parseInt(
                    Builder.getPart(this.min, this.tokenizer, MINIMUM_STRING)
                );
            } catch (Exception e) {
                e.printStackTrace(); //NOPMD: allowed for the exercise
            }
            return this;
        }

        /**
         * @param max the maximum value
         * @return this builder, for method chaining
         */
        public Builder setMax(final int max) {
            try {
                this.max = Integer.parseInt(
                    Builder.getPart(this.max, this.tokenizer, MAXIMUM_STRING)
                );
            } catch (Exception e) {
                e.printStackTrace(); //NOPMD: allowed for the exercise
            }
            return this;
        }

        /**
         * @param attempts the attempts count
         * @return this builder, for method chaining
         */
        public Builder setAttempts(final int attempts) {
            try {
                this.attempts = Integer.parseInt(
                    Builder.getPart(this.attempts, this.tokenizer, ATTEMPTS_STRING)
                );
            } catch (Exception e) {
                e.printStackTrace(); //NOPMD: allowed for the exercise
            }
            return this;
        }

        /**
         * @return a configuration
         */
        public final Configuration build() {
            if (consumed) {
                throw new IllegalStateException("The builder can only be used once");
            }
            consumed = true;
            return new Configuration(max, min, attempts);
        }

        private static String getPart(
            final int min, StringTokenizer tokenizer, final String typeSearch
        ) throws IOException {
            String part = Integer.toString(min);
            try (BufferedReader reader = new BufferedReader(new FileReader(PATH))) {
                do {
                    tokenizer = new StringTokenizer(reader.readLine());
                } while (!tokenizer.nextToken().toLowerCase().equals(typeSearch));
                while (tokenizer.hasMoreTokens()) {
                    part = tokenizer.nextToken();
                }
            }
            return part;
        }
    }
}

