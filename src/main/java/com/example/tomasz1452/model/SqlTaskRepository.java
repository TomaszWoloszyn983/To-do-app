package com.example.tomasz1452.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Ta klasa ma służyć do komunikacji pomiędzy aplikacją
 * a baza danych. Po prostu metody z adnotacją RestResource będą mapowane na
 * rządania sql'owe. Taką funkcję pełni JpaRepo, inne klasy mogą tłumacznyć na inne
 * typu.
 *
 * Dodając parametry path oraz collectionResourceRel do RepositoryRestResource
 * spowodawaliśmy podmianę domyślnych wartości tych zmiennych. Oznacza to że
 * teraz scieżka URL do naszej klasy bedzie wyglądała localohost:8080/todos
 * zamiast domyślnego localohost:8080/tasks. Domyślna wartość była wygenerowana
 * przez Javę/springa na podstawie klasy Task. Podobnie podmieniając collectionResourceRel
 * sprawiliśmy że nasza kolekcja zadań (Task) będzie oznaczona w systemach naszej aplikacji
 * jako todos. Ostatecznie jednak powróciliśmy do domyślnych wartości.
 *
 * Usunęliśmy większość napisanego dotychczas kodu, aby oprościc oraz przedewszystkim
 * chyba opóźnić impementację różnych metod i interfaców, które jak rozumiem chcemy, aby
 * były implementowane z jak najpóźniejdszym etapie uruchamiania się aplikacji
 *
 * P.S. RestController pozwala zdefiniować dowolne operacje i ma pierwszeństwo
 * przed RepositoryRestResource. Dlatego, gdy ma jakiś adres, który koliduje
 * z adresem z repozytorium, to repo przestaje działać jak kontroler.
 *
 * !!!
 * Do rozszerzeń dodaliśmy TaskRepository dzięki temu SqlTaskRepository będzie
 * rozpoznawany jako taki sam typ Bean jak TaskRepository.
 * Ponieważ rozszerzamy interface TaskRepository uzyskujemy dostęp do jego
 * metod wobec tego wykasowaliśmy wszystkie metody które mieliśmy w tej klasie.
 * Głowny powód tego był taki że ta klasa jest publiczna a TaskRepo ma ograniczony
 * dozstęp a my nie chcemy udostępniać tych klas publicznie.
 */
//@RepositoryRestResource(path = "todos", collectionResourceRel = "todos")

@Repository
public interface SqlTaskRepository extends TaskRepository, JpaRepository<Task, Integer>{
    @Override
    @Query(nativeQuery = true, value = "select count (*) > 0 from tasks where id=:id")
    boolean existsById(@Param("id")Integer id);

    /*
    Nadpisanie dwóch poniższych metod delete() powoduje że poprzez
    wywoływanie żądań delete w postmanie nie będziemy mogli usuwać
    elementów z naszej listy zadań.
    */

    /*
    @Override
    @RestResource(exported = false)
    void delete(Task task);

    @Override
    @RestResource(exported = false)
    void deleteById(Integer integer);
*/
    /**
     * Ta metoda zwraca tablicę zadań(task) oznaczoneych jako done.
     * Path oznaczo człon adresu ULR a rel to referencja.
     * teraz w Postmanie możemy wpisać adres:
     *      localhost:8080/tasks/search/done
     * i otrzymamy listę zadań wykonanych.
     * P.S. search jest to nazwa opcji któa nam się pojawia po
     * napisaniu tej metody w Postamanie.
     * @return
     */
//    @RestResource(path = "done", rel = "done")
//    List<Task> findByDoneIsTrue();

//        @RestResource(path = "done", rel = "done")
//        List<Task> findByDone(@Param("state") boolean done);
//          przenieślismy metode do TaskRepository
    /*
    Tutaj mamy altetnatywną wersję powyższej metody.
    Ta jest bardziej elastyczna, gdyż w parametrze podajemy stan
    w jakim zwracane elementy mają sie znajdowac, tutaj podane many done
    ale równie dobrze poprzez wpisanie:
        localhost:8080/tasks/search/done?state=false
    możemy wyszukać zadania niewykonane.
     */
}
