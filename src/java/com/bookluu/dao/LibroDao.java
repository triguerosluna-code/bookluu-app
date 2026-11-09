package com.bookluu.dao;

import com.bookluu.model.Libro;
import java.util.List;

public interface LibroDao {
    List<Libro> obtenerTodosConPropietario();
    List<Libro> obtenerPorUsuario(int idUsuario);
    Libro obtenerPorId(int idLibro);
    boolean insertar(Libro libro);
    boolean actualizar(Libro libro);
    boolean eliminar(int idLibro);
}
