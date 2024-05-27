package com.Alura.Challenge_LibrosAlura.Model;

import com.fasterxml.jackson.annotation.*;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(
        @JsonAlias("title") String Titulo,
        @JsonAlias("authors") List<DatosAutor> Autor,
        @JsonAlias("languages") List<String> Idioma,
        @JsonAlias("download_count") String NumeroDescargas
) {
}
