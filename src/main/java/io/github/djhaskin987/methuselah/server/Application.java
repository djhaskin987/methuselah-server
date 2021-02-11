package io.github.djhaskin987.methuselah.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import io.github.djhaskin987.methuselah.server.model.FileStorageProperties;

/**
 * Main application class of the methuselah server.
 */
// These comments to suppress the need for a final class here, it can't be final
// because Spring doesn't want it to be.
// CHECKSTYLE:OFF
@SpringBootApplication
@EnableConfigurationProperties(FileStorageProperties.class)
public class Application {
    // CHECKSTYLE:ON

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
