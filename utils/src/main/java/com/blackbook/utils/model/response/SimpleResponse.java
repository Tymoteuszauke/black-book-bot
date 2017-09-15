package com.blackbook.utils.model.response;

import lombok.Builder;
import lombok.Getter;
/**
 * @author "Patrycja Zaremba"
 */
@Getter
@Builder
public class SimpleResponse {
    private final int code;
    private final String message;
}
