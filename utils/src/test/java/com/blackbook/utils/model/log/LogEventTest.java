package com.blackbook.utils.model.log;

import org.testng.annotations.Test;

import java.time.LocalDateTime;

import static org.testng.Assert.assertEquals;

public class LogEventTest {

    @Test
    public void shouldBuildLogEventObject() throws Exception {
        // Given
        LogEvent event = LogEvent.builder()
                .bookStoreId(12L)
                .startTime(LocalDateTime.of(2017, 9, 10, 13, 30))
                .finishTime(LocalDateTime.of(2017, 9, 10, 14, 45))
                .result(10)
                .build();
        // Then
        assertEquals(event.getBookStoreId().toString(), String.valueOf(12));
        assertEquals(event.getStartTime(), LocalDateTime.of(2017, 9, 10, 13, 30));
        assertEquals(event.getFinishTime(), LocalDateTime.of(2017, 9, 10, 14, 45));
        assertEquals(event.getResult(), 10);
    }
}