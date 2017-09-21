package com.blackbook.gandalfscraper.webconnector;

import org.junit.Test;

/**
 * @author "Patrycja Zaremba"
 */
public class JsoupWebConnectorTest {

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenNotExistingUrl() {
        //given
        JsoupWebConnector webConnector = new JsoupWebConnector();
        String notExistingUrl = "http://not_url";
        //when
        webConnector.connect(notExistingUrl);
    }
}
