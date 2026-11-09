package com.bookluu.test;

import com.bookluu.dao.LibroDao;
import com.bookluu.dao.LibroDAOImpli;
import com.bookluu.model.Libro;
import java.util.List;

public class MainTest {
    public static void main(String[] args) {
        System.out.println("=== PRUEBA DE CONEXIÓN Y LECTURA DAO (BOOKLUU) ===");
        LibroDao libroDao = new LibroDAOImpli();

        List<Libro> libros = libroDao.obtenerTodosConPropietario();
        if (libros.isEmpty()) {
            System.out.println("⚠️ No se encontraron libros o falló la conexión con MySQL.");
        } else {
            System.out.println("✅ Conexión exitosa. Se recuperaron " + libros.size() + " libros:");
            for (Libro l : libros) {
                System.out.println("- [" + l.getIdLibro() + "] " + l.getTitulo() + " (" + l.getAutor() + ") - Propietario: " + l.getPropietario() + " | Estado: " + l.getEstado());
            }
        }
    }
}
