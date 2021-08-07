package com.example.tomasz1452;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import javax.validation.Validator;

@SpringBootApplication
public class TodoAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoAppApplication.class, args);


	}

	@Bean
	Validator validator(){
		return new LocalValidatorFactoryBean();
	}

//	@Override
//	public void configureValidatingRepositoryEventListener(
//			final ValidatingRepositoryEventListener validatingListener) {
//		RepositoryRestConfigurer.super.configureValidatingRepositoryEventListener(
//				validatingListener);
//		validatingListener.addValidator("beforeCreate", validator());
//		validatingListener.addValidator("beforeSave", validator());
		/*
		Ustawiliśmy tutaj validację która sprawdza czy rządanie (Request) jest
		prawidłowe. Jesli nie jest to zwraca błąd rządania.
		 */

}
