package com.example.tomasz1452.model.projection;

import com.example.tomasz1452.model.Task;
import com.example.tomasz1452.model.TaskGroup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Test ten utworzyliśmy dodatkowo gdy instruktor odkrył błąd którego wczesniej nie
 * widział. Chodizło o to że tworzą nowe zadanie można było nie podawać żadnej wartości
 * w tabeli deadline, przez co wartość ta wynosiła domyślnie null.
 * Powodowało to w klasie GroupReadModel w linijce 24 błąd spowodowany użyciem
 * metody compare() na nullu.
 * Praktyka tego typu jest zalecana. Czyli po wykryciu jakiegoś błędu najpierw utworzyć test
 * który będzie taki błąd wykrywał i obsługiwał i dopiero wtedy naprawiać taki błąd.
 */
class GroupReadModelTest {
    @Test
    @DisplayName("should create null deadline for group when no task deadlines")
    void constructor_noDeadlines_createsNullDealine(){

            /*
            Więc test polega mniej więcej na tym że tworzymy mockowy
            obiekt TaskGroup. Jak mniemam nie ma on konstruktora
            parametrowego, dlatego jego wartości ustawiamy za pomoca
            setterów.
            Podstawiamy TaskGrupę jako argument do nowego obiektu
            GroupReadModel.
            I oczekujemy że otrzymamy wynik zawierający właściwość deadline
            o wartości null. Ale że początkowo w GroupReadModel wyskakiwał
            nam nullPointerException to nie otrzymywaliśmy tego wyniku ponieważ
            test nie przechodził.
             */

//        given
        var source = new TaskGroup();
        source.setDescription("foo");
        source.setTasks(Set.of(new Task("bar", null)));

//        when
        var result = new GroupReadModel(source);

//        then
        assertThat(result).hasFieldOrPropertyWithValue("deadline", null);
    }
}
