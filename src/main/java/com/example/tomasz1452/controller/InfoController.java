package com.example.tomasz1452.controller;

import com.example.tomasz1452.model.TaskConfigurationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Zarządzmy tutaj danymi zawartymi w pliku application.yaml.
 * Nie wiem tylko puki co skąd spring wie z jakiego pliku czytać
 * te dane.
 *
 * Adnotacje springowe pozwalają springowi zarzadząć tym wszystkim
 * @RestController tworzy beana czyli komponent springowy.
 * @GetMapping wysyła rządanie.
 * @Value(${}) pobiera dane z podanego w nawiasie adresu.
 *
 * Słabością tego formatu i tego rozwiązania jest to że jest
 * on bardzo podatny na literówki i temu podobne błędy.
 * Błąd zrobiony na przykład w spring.datasource.url byłby
 * wiodoczny dopiero w logach po wystartowaniu aplikacji.
 * I czasem trudno byłoby odnaleść taki błąd.
 * Dlatego w następnej lekcji zapobiegliśmy powstawaniu takiego
 * błędu poprzez zasttosowanie rozwiązania z autowired.
 * Po czym tego wszytkiego i tak sie pozbywamy bo to
 * był tylko przykład.
 */
@RestController
class InfoController {
//    @Value("${spring.datasource.url}")
//    private String url;

    private DataSourceProperties dataSource;
    private TaskConfigurationProperties myProp;

    InfoController(final DataSourceProperties dataSource,
                   final TaskConfigurationProperties myProp) {
        this.dataSource = dataSource;
        this.myProp = myProp;
    }

    @GetMapping("/info/url")
    String url(){
        return dataSource.getUrl();
    }

    @GetMapping("/info/prop")
    boolean myProp(){
        return myProp.getTemplate().isAllowMultipleTasks();
    }
}
