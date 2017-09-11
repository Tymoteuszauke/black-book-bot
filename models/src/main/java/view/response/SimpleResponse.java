package view.response;

import lombok.Data;
import lombok.Getter;

/**
 * @author "Patrycja Zaremba"
 */
@Getter
public class SimpleResponse {
    private final int code;
    private final String message;

    public SimpleResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
