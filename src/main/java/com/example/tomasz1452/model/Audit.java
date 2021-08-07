package com.example.tomasz1452.model;

import javax.persistence.Embeddable;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

//@MappedSuperclass
@Embeddable
class Audit {
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

    @PrePersist
    void prePersist(){
        createdOn = LocalDateTime.now();
    }
    @PreUpdate
    void preMerge(){
        updatedOn = LocalDateTime.now();
    }
    /*
    prepersist i preupdate mają taka szczególną funkcję że są aktywowane
    tuż przed wykonaniem commita do bazy danych.
     */
}
