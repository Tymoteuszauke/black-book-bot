package view.log;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * @author Siarhei Shauchenka at 11.09.17
 */
@Builder
@Getter
public class LogEvent {

    private Long bookStoreId;
    private LocalDateTime finishTime;
    private LocalDateTime startTime;
    private int result;
}
