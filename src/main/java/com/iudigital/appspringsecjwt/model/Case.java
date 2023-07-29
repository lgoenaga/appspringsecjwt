package com.iudigital.appspringsecjwt.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Cascade;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "cases")
public class Case implements Serializable{

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    @Column(nullable = false)
    private LocalDate dateCase;
    @Column(nullable = false)
    private Float latitude;
    @Column(nullable = false)
    private Float longitude;
    @Column(nullable = false)
    private Float altitude;

    private Boolean isVisibility;

    private String urlMap;
    private String rmiUrl;

    private LocalDate createdAt;
    private LocalDate updatedAt;


    @ManyToOne(fetch = FetchType.EAGER)
    //@Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @ManyToOne(fetch = FetchType.EAGER)
   // @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JoinColumn(name = "crime_id", nullable = false)
    private Crime crime;

}
