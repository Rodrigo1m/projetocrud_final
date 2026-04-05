package com.grupotres.projetocrud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.grupotres.projetocrud.view.TelaInicial;

@SpringBootApplication
public class ProjetocrudApplication {

	public static void main(String[] args) {

		// Inicia o Spring e guarda o contexto
		ApplicationContext context = SpringApplication.run(ProjetocrudApplication.class, args);

		// Passa o contexto para a TelaInicial antes de lançar o JavaFX
		TelaInicial.springContext = context;

		// Inicia a interface gráfica
		javafx.application.Application.launch(TelaInicial.class, args);
	}
}
