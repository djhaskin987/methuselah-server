package io.github.djhaskin987.methuselah.server.payload;

/**
 * POJO that represents the request body used for making a new series. Basically
 * this houses all the same items as Series except "lastAssigned".
 */
public final class NewSeriesRequest extends NamedContainerRequest {
    /**
     * Corresponds to the member of the Series class of the same name.
     */
    private Long startSequenceNumber;
    /**
     * Corresponds to the member of the Series class of the same name.
     */
    private String name;
    /**
     * Corresponds to the member of the Series class of the same name.
     */
    private String description;

    public Long getStartSequenceNumber() {
        return startSequenceNumber;
    }

    public void setStartSequenceNumber(final Long startSequenceNumber) {
        this.startSequenceNumber = startSequenceNumber;
    }
}
