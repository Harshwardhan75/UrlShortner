package com.urlshortner.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(name = "urls")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class URL {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer urlId;

    private String originalUrl;

    private String shortenUrl;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime expiredAt;

    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonIgnore
    private User user;

    @Column(nullable = false)
    private Integer userAccessed = 0;
}
