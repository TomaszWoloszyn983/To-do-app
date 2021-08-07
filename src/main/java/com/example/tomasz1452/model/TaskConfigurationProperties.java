package com.example.tomasz1452.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("task")
public class TaskConfigurationProperties {

//    private boolean allowMultipleTasksFromTemplate;
//
//    public boolean isAllowMultipleTasksFromTemplate() {
//        return allowMultipleTasksFromTemplate;
//    }
//
//    public void setAllowMultipleTasksFromTemplate(boolean allowMultipleTasksFromTemplate) {
//        this.allowMultipleTasksFromTemplate = allowMultipleTasksFromTemplate;
//    }

    private Template template;

    public Template getTemplate() {
        return template;
    }

    void setTemplate(Template template) {
        this.template = template;
    }

    public static class Template{
        private boolean allowMultipleTasks;

        public boolean isAllowMultipleTasks(){
            return allowMultipleTasks;
        }
        public void setAllowMultipleTasks(final boolean allowMultipleTasks){
            this.allowMultipleTasks = allowMultipleTasks;
        }
        /*
        Utworzenie tej klasy było zadaniem samodzielnym.
        Trzeba zajrzec do pliku application.properties i obczaić
        task.template.allowMultipleTasks: false następnie
        doposować do tego kod aby to wyrażenie działało.
        Zadanie było dość ciekawe więc podaję opis.

        Przedewszystkim nie wiedziłem gdzie znaleść pierwszy człon:
        task. Okazało sie że ta klasa ma adnotację springową task.

        Dalej trzeba było wewnątrz tej klasy utworzyć nową statyczną
        klasę Template, która będzie miała za zadanie zarządzac
        pozwoleniem na dodawanie danego zadania kilka razy.

        I to właściwie tyle.
         */
    }
}
