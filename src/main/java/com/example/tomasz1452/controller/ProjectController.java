package com.example.tomasz1452.controller;

import com.example.tomasz1452.model.Project;
import com.example.tomasz1452.model.ProjectService;
import com.example.tomasz1452.model.ProjectStep;
import com.example.tomasz1452.model.projection.ProjectWriteModel;
import io.micrometer.core.annotation.Timed;
import org.springframework.data.jpa.repository.Query;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/projects")
class ProjectController {
    private final ProjectService service;

    ProjectController(ProjectService service) {
        this.service = service;
    }

    @GetMapping
    String showProjects(Model model){
//        var projectToEdit = new ProjectWriteModel();
//        projectToEdit.setDescription("test");
        model.addAttribute("project", new ProjectWriteModel());
        return "projects";
    }

    /**
     * Metoda ta dodaje krok(step) do naszego szablonu w html'u.
     * Adnotacja ModelAttribute precyzuje że pobiwerany parametr będzie odnosił się
     * do atrybutu "project" zdefiniowanego chyba w metodzie powyżej.
     * Następnie z podanego jako agrument WriteModelu pobieramy wszystkie istniejące już
     * w nim stepy oraz dodajemy następny, nowy step.
     * Mniemam że nie zawiera on żadnych wartości gdyż te wartości będą pobierane
     * uzupełnionych przez użytkownika diagramów na wyświetlonej stronie.
     *
     * @param current
     * @return
     */
    @PostMapping(params = "addStep")
    String addProjectStep(@ModelAttribute("project") ProjectWriteModel current){
        current.getSteps().add(new ProjectStep());
        return "projects";
    }

    /**
     * Podobnie jak w metodzie addProjectStep tak tutaj tutaj dodajemy do
     * wyświetlanego szablonu Projekt zawierający swoje kroki.
     *
     * Dodajemy też wiadomość message która będzie wyświetlana w nagłówku panelu
     * po dodaniu Projeku. Wiadomość tą przekazujemy do pliku html, do działu main
     * w templatce <h1></h1>
     *
     * Dodoaliśmy walidację.
     * Poprzeż dodanie nowego parametru do konstruktora. Kolejność parametrów nie jest chyba
     * przypadkowa, podobnie jak co jest oczywiste, użycie tego patametru w metodzie.
     * Teraz program przed wykonaniem swojej właściwej operacji sprawdza czy dodawanie
     * nowego projektu wywołuje jakieś błędy (w tym przypadku są to np. niewypełnione pola)
     * i reaguje wyświetleniem komunikatu, który sobie dostroiliśmy w pliku project za pomoca css'a
     *
      * @param current
     * @param model
     * @return
     */
    @PostMapping
    String addProject(
            @ModelAttribute("project") @Valid ProjectWriteModel current,
            BindingResult bindingResult,
            Model model){

        if(bindingResult.hasErrors()){
            return "projects";
        }

        service.save(current);
        model.addAttribute("project", new ProjectWriteModel());
        model.addAttribute("projects", getProject());
        model.addAttribute("message", "Dodano projekt!");
        return "projects";
    }

    @ModelAttribute("projects")
    List<Project> getProject(){
        return service.readAll();
    }

    /**
     * Nie wiem co właściwie ma robić ta metoda, ale poki co wszystko działa w niej
     * poprawnie.
     *
     * Miałem problrem podczas pisanie tej metody wpowodowany literówką
     * w pliku html, ale już naprawiony.
     *
     * @param current
     * @param model
     * @param id
     * @param deadline
     * @return
     */
    @Timed(value = "project.create.group", histogram = true, percentiles = {0.5, 0.95, 0.99})
    @PostMapping("/{id}")
    String createGroup(
            @ModelAttribute("project") ProjectWriteModel current,
            Model model,
            @PathVariable int id,
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")LocalDateTime deadline){
        try{
            service.createGroup(deadline, id);
            model.addAttribute("message", "Dodano grupę!");
        }catch (IllegalStateException | IllegalArgumentException e){
            model.addAttribute("message", "Błąd podczas tworzenia grupy!");
        }

        return "projects";
    }
}
