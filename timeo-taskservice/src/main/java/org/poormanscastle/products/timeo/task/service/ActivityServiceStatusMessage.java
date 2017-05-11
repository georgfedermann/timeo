package org.poormanscastle.products.timeo.task.service;

/**
 * Created by georg on 11/05/2017.
 */
public enum ActivityServiceStatusMessage {

    SUCCESS("SUCCESS: "), FAILURE("FAILURE: ");


    private String message;

    private ActivityServiceStatusMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
