# LiterAlura
Desafio de Alura cursos, que consume una API que contiene datos de libros y se almacenan en una base de datos de PostgreSQL

![image](https://github.com/CarlosContreras16/LiterAlura/assets/134731423/cd687552-c362-4961-b31d-70c36dfc26d6)

<h3>Descripción del proyecto</h3> 

En este proyecto se crea un sistema que consume los datos de una API [Gutendex](https://gutendex.com/) del curso de Alura Latam

## :hammer_and_wrench:Funcionalidades

`Opciones`
 1. **Buscar libros por título**: Busca el libro en la API y guarda la información (Nombre del libro, nombre del autor, fecha de nacimiento y fallecimiento del autor y cantidas de descargas del libro en la API) en la base de datos.                  
 2. **Listar libros registrados**: Lista los libros que se han guardado en la base de datos.
 3. **Listar autores registrados**: Lista los nombres de los autores registrados en la base de datos.
 4. **Listar autores vivos en un determinado año**: Busca los autores vivos en una fecha especifica. 
 5. **Listar libros por idioma**: Lista los libros por el idioma en el que se han escrito. 
 ----Extras-----
 6. **Top 10 libros**: Lista los 10 libros más descargados 
 7. **Buscar autores por nombre**: Busca un autor guardado en la base de datos y lista los libros que ha escrito.
 8. **Top 10 libros en la API**: Lista los libros más descargados en la API.
 9. **Top 5 libros en la DB**: Lista el top 5 de los libros con mas cantidad de descargas en la base de datos.
 10. **Autores en derecho público**: Lista los 10 autores que se encuentran en derecho público en la API.
- **Salir** : Sale del sistema. 

## :computer:Tecnologias Utilizadas

- Java JDK: versión: 17 en adelante
- Maven: versión 4 en adelante
- Spring: versión 3.2.3 - https://start.spring.io/
- Postgres: versión 16 en adelante

  `Dependencias`
- Spring Data JPA
- PostgreSQL Driver
 
