package com.Alura.Challenge_LibrosAlura.principal;

import com.Alura.Challenge_LibrosAlura.Model.Autor;
import com.Alura.Challenge_LibrosAlura.Model.DatosAutor;
import com.Alura.Challenge_LibrosAlura.Model.DatosLibro;
import com.Alura.Challenge_LibrosAlura.Model.Libro;
import com.Alura.Challenge_LibrosAlura.repository.AutorRepository;
import com.Alura.Challenge_LibrosAlura.repository.IdiomaRepository;
import com.Alura.Challenge_LibrosAlura.repository.LibroRepository;
import com.Alura.Challenge_LibrosAlura.service.ConsumoAPI;
import com.Alura.Challenge_LibrosAlura.service.ConvierteDatos;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.hibernate.type.descriptor.sql.internal.Scale6IntervalSecondDdlType;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private LibroRepository repositorio;
    private AutorRepository repositorio2;
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private final String URL_BASE2 = "https://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();
//    private List<DatosLibro> datosLibros;
    private List<DatosLibro> datosLibros = new ArrayList<>();
    private  List<DatosAutor> datosAutores = new ArrayList<>();
    private List<Autor> autorEncontrado2;
    private List<Libro> libros;
    Optional<Autor> autorEncontrado;
    Optional<Libro> libroEncontrado;

    public Principal(LibroRepository repository, AutorRepository repository2) {
        this.repositorio = repository;
        this.repositorio2 = repository2;
    }

    //    public Principal(LibroRepository repository){
//
//    }
    public void MostrarMenu(){
        var opcion = -1;
        while (opcion!=0){

            var menu= """
                    ----------------------
                    Bienvenido, elija la opción que desea consultar:                  
                    1- Buscar libros por título                  
                    2- Listar libros registrados
                    3- Listar autores registrados
                    4- Listar autores vivos en un determinado año
                    5- Listar libros por idioma
                    ----Extras-----
                    6- Top 10 libros
                    7- Buscar autores por nombre
                    8- Top 10 libros en la API
                    9- Top 5 libros en la DB
                    10- Autores en derecho público 
                    0- Salir
                    -----------------------
                    """;

            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion){
                case 1:
                    BuscarLibrosTitulo();
                    break;
                case 2:
                    MostrasLibros();
                    break;
                case 3:
                    MostrarAutores();
                    break;
                case 4:
                    AutoresPorAño();
                    break;
                case 5:
                    LibrosPorIdioma();
                    break;
                case 6:
                    Top10Libros();
                    break;
                case 7 :
                    BuscarPorNombreAutor();
                    break;
                case 8:
                    Top10LibrosAPI();
                    break;
                case 9:
                    Top5LibrosEnlaBD();
                    break;
                case 10:
                    AutoresEnDerechoPublico();
                    break;
                case 0:
                    System.out.println("Saliendo del Sistema...");
                    break;
                default:
                    System.out.println("Opción no valida");
            }
        }
    }

    //hace una busqueda en la API
    private DatosLibro getDatosLibro(){
        System.out.println("Ingrese el nombre del libro que desea buscar: ");
        var nombrelibro= teclado.nextLine();
        System.out.println("Buscando el libro, espere unos segundos...");
        var json = consumoAPI.obtenerDatos(URL_BASE+nombrelibro.replace(" ", "%20"));
        System.out.println(json);
        //almacenamos en la variable los datos que se traen desde la API
        DatosLibro datos = conversor.obtenerDatos(json, DatosLibro.class);
        //solo traemos un autor y es el primero que encuentra
        autorEncontrado = datos.Autor().stream().map(Autor::new).findFirst();
        return datos;
    }
    private void BuscarLibrosTitulo(){

//        DatosAutor datosAutor = DatosAutor(datos);
//        Autor nuevoAutor =
        //si encontro un autor hay que validarlo
        try {
            Autor autor = new Autor();
            DatosLibro datos = getDatosLibro();
            if (!datos.toString().isEmpty())
            {
                //busca el autor si ya existe en la base de datos
                autor = repositorio2.findByNombreContainsIgnoreCase(autorEncontrado.get().getNombre());

                //si no existe lo guarda en la base de datos
                if (autor ==null){
                    Autor nuevoAutor = autorEncontrado.get();
                    autor = repositorio2.save(nuevoAutor);
                }
                try {
                    Libro libro = new Libro(datos);
                    //retornamos el valor del autor para guardarlo en la tabla libros
                    libro.setAutor(autor);
                    //se agrega los datos a la clase libros
                    datosLibros.add(datos);
                    //se guarda los datos en la tabla libros
                    repositorio.save(libro);
                    System.out.println(libro);
                }catch (DataIntegrityViolationException e){
                    System.out.println("El libro ya existe en la base de datos");
                }
            }
            else {
                System.out.println("Este libro no existe, intente con otro...");
            }
        }catch (Exception e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }
    //opcion 2
    private void MostrasLibros() {
    List<Libro> libros = repositorio.findAll();
    libros.stream().forEach(System.out::println);
    }
    //opcion 3
    private void MostrarAutores() {
        List<Autor> autores = repositorio2.findAll();
        autores.stream().forEach(System.out::println);
    }
    //opcion 4
    private void AutoresPorAño(){
        System.out.println("Ingrese el año de un autor o autores vivos que desea buscar: (ejemplo 1998)");
        int year;
        String AñoBuscado;
        try {
            year = teclado.nextInt();
            AñoBuscado = String.valueOf(year);
            List<Autor> autoresVivos = repositorio2.BuscarAutorVivo(AñoBuscado);
            if (autoresVivos.isEmpty()){
                System.out.println("No se encontraron Autores en ese determinado año");
            }
            else {
                autoresVivos.stream().forEach(System.out::println);
            }
//            autorEncontrado2 = repositorio2.findAll();
//            List<String> autoresNombres = autorEncontrado2
//                    .stream().filter(a-> (Integer.parseInt(a.getFechaMuerte())>= year) && (Integer.parseInt(a.getFechaNacimiento())<= year))
//                    .map(a-> a.getNombre()).collect(Collectors.toList());
//            autoresNombres.forEach(System.out::println);
        }catch (Exception e){
            System.out.println("Escriba un año valido");
            teclado.nextLine();
        }

    }
    //opcion 5
    private void LibrosPorIdioma(){
        List<IdiomaRepository> idiomas = repositorio.BuscarIdiomas();
        idiomas.stream().forEach(i -> System.out.println(
                """
                        Codigo: %s, Cantidad de Libros: %d""".formatted(i.getIdioma(), i.getCount())
                ));
        System.out.println("Ingresa el codigo de idioma para listar los libros: (ejemplo = es)");

        try {
            String codigo = teclado.nextLine();

            for (IdiomaRepository idioma : idiomas)
            {
                if (idioma.getIdioma().equals(codigo))
                {
                    repositorio.findByIdiomaEquals(codigo).stream().forEach(System.out::println);
                    return;
                }else if (codigo.length() > 2)
                {
                    throw  new InputMismatchException("Los cosigos contienen 2 caracteres: Ejemplo(es)");
                }
            }
            System.out.println("Codigo Invalido");
        }catch (InputMismatchException e){
            System.out.println(e.getMessage());
        }
    }
    //opcion 6
    private void Top10Libros(){
        System.out.println("Los TOP 10 libros mas descargados son: ");
        List<Libro> topLibro = repositorio.findTop10ByOrderByNumeroDescargasDesc();
        topLibro.stream().forEach(System.out::println);
    }
    //opcion 7
    private void BuscarPorNombreAutor(){
        System.out.println("Ingrese el nombre del autor que desea buscar:");
        var nombreAutor = teclado.nextLine();
        autorEncontrado = repositorio2.findByNombreContainingIgnoreCase(nombreAutor);
        if (autorEncontrado.isPresent()){
            System.out.println(autorEncontrado.get());
        }else {
            System.out.println("Autor no encontrado");
        }
    }
    private void Top10LibrosAPI(){
        Optional<Autor> autorEncontrado;
        System.out.println("Buscando los libros en la web, espere unos segundos...");

        try {
            String json = consumoAPI.obtenerDatos(URL_BASE2 + "?sort");

            List<DatosLibro> librosDatos = conversor.obtenerDatosArray(json, DatosLibro.class);
             autorEncontrado = librosDatos.get(0).Autor().stream().map(Autor::new).findFirst();
//            DatosAutor autorDatos = conversor.obtenerDatosArray(json, DatosAutor.class);

            List<Libro> libros = new ArrayList<>();
            for (int i = 0; i < librosDatos.size(); i++) {
                Autor autor;
                autor = autorEncontrado.get();
                Libro libro = new Libro(librosDatos.get(i));
                libro.setAutor(autor);
                libros.add(libro);
            }
            libros.sort(Comparator.comparingDouble(Libro::getNumeroDescargas).reversed());
            List<Libro> Top10 = libros.subList(0,Math.min(10, libros.size()));

            for (int i = 0; i < Top10.size(); i++) {
                System.out.println((i+1) +"- " + Top10.get(i));
            }

        }catch (NullPointerException e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
    }

    private void AutoresEnDerechoPublico()
    {
        System.out.println("Buscando el autor, espere unos segundos...");
        try {
            String json = consumoAPI.obtenerDatos(URL_BASE2 + "?sort");
            List<DatosLibro> datosAutores = conversor.obtenerDatosArray(json, DatosLibro.class);
            List<DatosAutor>  AutorEncontrado = new ArrayList<>();

            Map<String, Autor> autoresMap = new HashMap<>();

            for (int i = 0; i < datosAutores.size(); i++) {
//                String nombre = datosAutores.get(i).Autor().toString();
//                for (int i = 0; i < datosAutores.size(); i++) {
//                    String encontrado = AutorEncontrado.get(i).nombre();
                    String nombre = datosAutores.get(i).Autor().get(0).nombre();
                    Optional<Autor> encontrado = datosAutores.get(i).Autor().stream().map(Autor::new).findFirst();
                    Autor autor = autoresMap.get(nombre);
                    if(autor == null){
                        autor = encontrado.get();
                        autoresMap.put(nombre,autor);
                    }
                    List<Libro> librosArray = new ArrayList<>();
                    autor.setLibros(librosArray);
                }

//            }
            List<Autor> AutoresOrdenados = autoresMap.values().stream()
                    .filter(a-> Integer.parseInt(a.getFechaMuerte()) < 1954)
                    .collect(Collectors.toList());

            List<Autor> diezAutores = AutoresOrdenados.subList(0, Math.min(10, AutoresOrdenados.size()));
            System.out.println("Los 10 autores en derecho público son: ");
            for (int i = 0; i < diezAutores.size(); i++) {

                System.out.println((i+1) + "- " + diezAutores.get(i).getNombre()
                + ", año de fallecimiento " + diezAutores.get(i).getFechaMuerte());
            }
        }catch (Exception e){
            System.out.println("Error: "+ e.getMessage());
        }
    }
    //opcion 10
    private void Top5LibrosEnlaBD(){
        try{
            List<Libro> libros = repositorio.findAll();
            List<Libro> ordenados = libros.stream().sorted(Comparator.comparingDouble(Libro::getNumeroDescargas)
                    .reversed()).collect(Collectors.toList());
            List<Libro> Top5 = ordenados.subList(0, Math.min(5, ordenados.size()));
            for (int i = 0; i < Top5.size(); i++) {
                System.out.println(i + 1 + "- " + Top5.get(i));
            }
        }catch (NullPointerException e){
            System.out.println(e.getMessage());
             libros = new ArrayList<>();
        }
    }
}
