package net;

import lombok.Data;

/**
 * @author Siarhei Shauchenka at 06.09.17
 */
@Data
public class SimpleResponseModel {
    private final int code;
    private final String message;

    public SimpleResponseModel(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
