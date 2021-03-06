package com.example.tomasz1452.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * W tej klasie definiujemy tabelę table z naszymi todosami,
 * nazwanymi tutaj tasks. Tabela będzie zawierała następujące
 * kolumny: Id, description oraz done. Przy czym przy Id musieliśmy
 * dodać specjalną Springową adnotację.
 *
 * Adnotacja @GeneratedValue generuje automatycznie IDka dla
 * każdego nowego taska.
 * NotBlank sprawdza czy zmienna nie jest nullem, albo inną niewłaściwą
 * wartością. Tylko trzeba dociągnąc zalezność spring-boot-starter-validation
 */

@Entity
//@Inheritance(InheritanceType.TABLE_PER_CLASS)
@Table(name = "tasks")
public class Task{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotBlank(message = "Tasks description must not be empty")
    private String description;
    private boolean done;
    private LocalDateTime deadline;
    @Embedded
//    @AttributeOverrides({
//            @AttributeOverride(column = @Column(name = "updatedOn"), name = "updatedOn")
//    })   // W lekcji 73 mamy opis tego dziwadła.
    private Audit audit = new Audit();



    Task(){}


    public int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public void updateFrom(final Task source){
        description = source.description;
        done = source.done;
        deadline = source.deadline;
    }


}
