package com.Alura.Challenge_LibrosAlura.Model;

import jakarta.persistence.*;

import java.util.OptionalDouble;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;
    @Column(unique = true)
    private String Titulo;
    private String idioma;
    private double numeroDescargas;
    @ManyToOne
    @JoinColumn(name="autor_id", nullable = false )
    private Autor autor;

    public Autor getAutor(Autor autor) {
        return this.autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public double getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(double numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }

    public Libro(){}

    public Libro(DatosLibro datoslibro) {
        this.Titulo = datoslibro.Titulo();
        this.autor = new Autor(datoslibro.Autor().get(0));
        this.idioma = datoslibro.Idioma().get(0);
        this.numeroDescargas = OptionalDouble.of(Double.valueOf(datoslibro.NumeroDescargas())).orElse(0);
    }

    @Override
    public String toString() {
        return """
                --------Libro--------
                Titulo: %s
                Autor: %s
                Idioma: %s
                Número de Descargas: %s
                "---------------------
                """.formatted(this.Titulo, this.autor.getNombre(), this.idioma, this.numeroDescargas);
    }
}
