package com.urlshortner.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(name = "analytics")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Analytics {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer analyticsId;
    private LocalDateTime accessTime = LocalDateTime.now();
    private String device;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "urlId")
//    @JsonIgnore
    private Integer urlId;
}
