package com.bookluu.controller;

import com.bookluu.dao.LibroDao;
import com.bookluu.dao.LibroDAOImpli;
import com.bookluu.model.Libro;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    private final LibroDao libroDao;

    public LibroController() {
        this.libroDao = new LibroDAOImpli();
    }

    // GET: Obtener todos los libros con su propietario (JOIN)
    @GetMapping
    public ResponseEntity<List<Libro>> listarLibros() {
        List<Libro> libros = libroDao.obtenerTodosConPropietario();
        return ResponseEntity.ok(libros);
    }

    // GET: Obtener libro por ID con validación (404 si no existe)
    @GetMapping("/{idLibro}")
    public ResponseEntity<?> obtenerLibroPorId(@PathVariable int idLibro) {
        Libro libro = libroDao.obtenerPorId(idLibro);
        if (libro == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error 404: El libro con ID " + idLibro + " no existe en la base de datos.");
        }
        return ResponseEntity.ok(libro);
    }

    // POST: Registrar nuevo libro con validaciones de negocio (400 Bad Request)
    @PostMapping
    public ResponseEntity<?> registrarLibro(@RequestBody Libro libro) {
        try {
            validarLibro(libro);
            boolean creado = libroDao.insertar(libro);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // PUT: Actualizar libro existente (Validación 400 y 404)
    @PutMapping("/{idLibro}")
    public ResponseEntity<?> actualizarLibro(@PathVariable int idLibro, @RequestBody Libro libro) {
        if (idLibro <= 0) {
            return ResponseEntity.badRequest().body("Error 400: El ID del libro proporcionado no es válido.");
        }

        Libro existe = libroDao.obtenerPorId(idLibro);
        if (existe == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error 404: El libro con ID " + idLibro + " no existe.");
        }

        try {
            validarLibro(libro);
            libro.setIdLibro(idLibro);
            boolean actualizado = libroDao.actualizar(libro);
            return ResponseEntity.ok(actualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // DELETE: Eliminar libro por ID (Validación 404)
    @DeleteMapping("/{idLibro}")
    public ResponseEntity<?> eliminarLibro(@PathVariable int idLibro) {
        Libro existe = libroDao.obtenerPorId(idLibro);
        if (existe == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error 404: El libro con ID " + idLibro + " no existe.");
        }

        boolean eliminado = libroDao.eliminar(idLibro);
        return ResponseEntity.ok(eliminado);
    }

    // Lógica de reglas de negocio y validación estricta de datos (Error 400)
    private void validarLibro(Libro libro) {
        if (libro == null) {
            throw new IllegalArgumentException("Error 400: El objeto libro no puede ser nulo.");
        }
        if (libro.getTitulo() == null || libro.getTitulo().trim().isEmpty()) {
            throw new IllegalArgumentException("Error 400: El título del libro no puede estar vacío.");
        }
        if (libro.getAutor() == null || libro.getAutor().trim().isEmpty()) {
            throw new IllegalArgumentException("Error 400: El autor del libro no puede estar vacío.");
        }
        if (libro.getPaginas() <= 0) {
            throw new IllegalArgumentException("Error 400: El número de páginas debe ser mayor a 0.");
        }
        if (libro.getCalificacion() < 0.0 || libro.getCalificacion() > 5.0) {
            throw new IllegalArgumentException("Error 400: La calificación debe estar dentro del rango 0.0 a 5.0.");
        }
    }
}
