package com.Alura.Challenge_LibrosAlura.repository;

import com.Alura.Challenge_LibrosAlura.Model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    Autor findByNombreContainsIgnoreCase(String nombre);

    @Query("select autor from Autor autor where (autor.fechaNacimiento <= :fecha) and (autor.fechaMuerte >= :fecha or autor.fechaMuerte is null)")
    List<Autor> BuscarAutorVivo(String fecha);

    Optional<Autor> findByNombreContainingIgnoreCase(String nombre);
}
