package io.github.djhaskin987.methuselah.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class of the methuselah server.
 */
@SpringBootApplication
public final class Application {
    /**
     * Private constructor to avoid linter errors.
     */
    private Application() {
    }

    /**
     * Main method for the spring boot application.
     *
     * @param args
     *                 CLI arguments.
     */
    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
