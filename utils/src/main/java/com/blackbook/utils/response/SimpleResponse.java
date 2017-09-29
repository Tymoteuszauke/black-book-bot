package com.blackbook.utils.response;

import lombok.Getter;

/**
 * @author "Patrycja Zaremba"
 */
@Getter
public class SimpleResponse<T> {
    T response;

    public SimpleResponse() {
    }

    public SimpleResponse(T response) {
        this.response = response;
    }
}
