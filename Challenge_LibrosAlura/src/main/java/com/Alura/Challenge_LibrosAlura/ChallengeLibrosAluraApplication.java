package com.Alura.Challenge_LibrosAlura;

import com.Alura.Challenge_LibrosAlura.principal.Principal;
import com.Alura.Challenge_LibrosAlura.repository.AutorRepository;
import com.Alura.Challenge_LibrosAlura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ChallengeLibrosAluraApplication implements CommandLineRunner {

	@Autowired
	private LibroRepository repository;
	@Autowired
	private AutorRepository repository2;
	public static void main(String[] args) {
		SpringApplication.run(ChallengeLibrosAluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repository, repository2);
		principal.MostrarMenu();
	}

//	@Override
//	public void run(String... args) throws Exception {
////
}
