package io.github.djhaskin987.methuselah.server.payload;

/**
 * POJO that represents the request body used for updating a named container (a
 * class with a name and a description).
 */
public class NamedContainerRequest {
    /**
     * Corresponds to the member of the Series class of the same name.
     */
    private String name;
    /**
     * Corresponds to the member of the Series class of the same name.
     */
    private String description;

    public final String getName() {
        return name;
    }

    public final void setName(final String name) {
        this.name = name;
    }

    public final String getDescription() {
        return description;
    }

    public final void setDescription(final String description) {
        this.description = description;
    }

}
