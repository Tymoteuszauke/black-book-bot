package com.blackbook.utils.model.response;

import lombok.Builder;
import lombok.Getter;

/**
 * @author "Patrycja Zaremba"
 */
@Getter
@Builder
public  class SimpleResponse<T> {
    protected final int code;
    protected final T body;
}
