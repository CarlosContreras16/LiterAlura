package com.Alura.Challenge_LibrosAlura.repository;

import com.Alura.Challenge_LibrosAlura.Model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro,Long> {
    @Query("select libro.idioma as idioma, count(*) as count from Libro libro group by libro.idioma")
    List<IdiomaRepository> BuscarIdiomas();

    List<Libro> findByIdiomaEquals(String codigo);

    List<Libro> findTop10ByOrderByNumeroDescargasDesc();

}
