package view.response;

import lombok.Getter;

/**
 * @author "Patrycja Zaremba"
 */
@Getter
public class SimpleResponse {
    private int code;
    private String message;

    public SimpleResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
