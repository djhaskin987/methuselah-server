package io.github.djhaskin987.methuselah.server.payload;

/**
 * POJO describing the response for uploading content.
 */
public final class UploadContentResponse {
    /**
     * Content address expressed as a sha256 hex string of the checksum of the file.
     */
    private String contentAddress;

    /**
     * Message that describes whether or not upload was successful, or why it wasn't.
     */
    private String status;

    /**
     * Construct the upload content response in the usual way.
     *
     * @param contentAddress
     *            The content address, a sha256 hex string.
     * @param status
     *            a status line for this response.
     */
    public UploadContentResponse(final String contentAddress, final String status) {

        this.contentAddress = contentAddress;
        this.status = status;
    }

    public String getContentAddress() {
        return contentAddress;
    }

    public void setContentAddress(final String newContentAddress) {
        this.contentAddress = newContentAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }
}
