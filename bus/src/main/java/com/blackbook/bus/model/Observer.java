package com.blackbook.bus.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Siarhei Shauchenka
 * @since 23.08.17
 */
@Data
@Entity
@Table(name = "observers")
public class Observer {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "url")
    private String url;

}
