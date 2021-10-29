package com.example.tomasz1452.controller;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
Retention oznacza długość działania tej adnotacji zanim zostanie
zatrzymana i usunięta przez kompilator. W naszym przypadku dodaliśmy
Runtime czyli że będzie ona działała przez cały czas działania aplikacji.
Dodatkowo zapis RetentionPolicy.RUNTIME można zapisać też :
value = RetentionPolicy.RUNTIME. dlatego każda oadnotacja która będzie
poniżej naszej IllegalExceptionProcessing @Retention poprzez value będzie
mogła uzyskać dostęp to wartości RetentionPolicy.RUNTIME.

Co tak naprawdę robi Target tego nie ogarniam na razie.
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface IllegalExceptionProcessing {

}
