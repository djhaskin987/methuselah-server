package io.github.djhaskin987.methuselah.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception used when throwing a 404.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Serialization UID.
     */
    private static final long serialVersionUID = -7507169493619106112L;

    /**
     * Standard 404 constructor with mesage.
     *
     * @param message
     *                    Message used as part of the exception.
     */
    public ResourceNotFoundException(final String message) {
        super(message);
    }

    /**
     * Standard 404 constructor with mesage.
     *
     * @param message
     *                    Message used as part of the exception.
     * @param cause
     *                    An exception thrown that is encapsulated by this one.
     *                    I think.
     */
    public ResourceNotFoundException(final String message,
            final Throwable cause) {
        super(message, cause);
    }
}
