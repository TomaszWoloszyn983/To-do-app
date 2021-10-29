package com.example.tomasz1452.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/*
AOP czyli programowanie zorientowane aspektowo.
Polega na tym że możemy używać , lub możemy też tworzyc własne
adnotacje Springowe które będą się odnosić tylko do metod oznaczonych
wybranymi przez nas odnotacjami.

Dodaliśmy adnotację IllegalExceptionProcessing do TaskGroupController
czyli oznacza to że od teraz ta adnotacja będzie działała po wywołaniu
tej klasy/

W klasie ProjectController do metody cretaGroup dodaliśmy adnotacje Timed
dzięki której po pod adresem http://localhost:8080/status/metrics/ mamy
różne statystyki dotyczące działanie nasze aplikacji.
P.S. w aplication-properties usunęliśmy możliwość zbierania staystyk
javowych (chyba tak to jakoś było) abyśmy mogli zbierać tylko te Springowe.
 */

@RestControllerAdvice(annotations = IllegalExceptionProcessing.class)
class IllegalExceptionControllerAdvice {

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<?> handleIllegalArgument(IllegalArgumentException e){
        return ResponseEntity.notFound().build();
    }
    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<String> handleIllegalArgument(IllegalStateException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
