package com.blackbook.persistencebot.model;

import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author Siarhei Shauchenka at 11.09.17
 */
@Entity
@Data
@Table(name = "logs")
public class LogEventModel {

    @Id
    @GeneratedValue
    private long id;

    @Getter
    @JoinColumn(name = "bookstore_id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Bookstore bookStore;

    @Getter
    @Column(name = "start_time")
    private Timestamp startTime;

    @Getter
    @Column(name = "finish_time")
    private Timestamp finishTime;

    @Getter
    @Column(name = "result")
    private int result;
}
