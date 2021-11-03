package com.example.tomasz1452.model;

import com.example.tomasz1452.event.TaskEvent;

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
    @NotBlank(message = "Task's description must not be empty")
    private String description;
    private boolean done;
    private LocalDateTime deadline;
    @Embedded
//    @AttributeOverrides({
//            @AttributeOverride(column = @Column(name = "updatedOn"), name = "updatedOn")
//    })   // W lekcji 73 mamy opis tego dziwadła.
    private Audit audit = new Audit();
    @ManyToOne
    @JoinColumn(name="task_group_id")
    private TaskGroup group;




    Task(){}

    public Task(String description, LocalDateTime deadline){
        this(description, deadline, null);
    }

    /**
     * Ten konstruktor tworzymy na potrzeby klas GroupTaskReadModel
     * oraz GroupTaskWriteModel. Nie zawiera w parametrach zmiennej
     * done, ponieważ zakładamy że nowo utworzone zadanie nie może
     * być od razu zrobionw.
     *
     * @param description
     * @param deadline
     */
    public Task(String description, LocalDateTime deadline, TaskGroup group) {
        this.description = description;
        this.deadline = deadline;
        if(group != null){
            this.group = group;
        }
    }

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

    public TaskEvent toggle(){
        this.done = !this.done;
        return TaskEvent.changed(this);
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    TaskGroup getGroup() {
        return group;
    }

    void setGroup(TaskGroup group) {
        this.group = group;
    }

    public void updateFrom(final Task source){
        description = source.description;
        done = source.done;
        deadline = source.deadline;
        group = source.group;
    }


}
