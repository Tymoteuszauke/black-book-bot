package view.response;

/**
 * @author "Patrycja Zaremba"
 */

public class SimpleResponse {
    private int code;
    private String message;

    public SimpleResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
