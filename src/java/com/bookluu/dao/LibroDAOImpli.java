package com.bookluu.dao;

import com.bookluu.config.DatabaseConfig;
import com.bookluu.model.Libro;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibroDAOImpli implements LibroDao {

    @Override
    public List<Libro> obtenerTodosConPropietario() {
        List<Libro> lista = new ArrayList<>();
        String sql = "SELECT l.*, u.nombre AS propietario FROM libros l INNER JOIN usuarios u ON l.id_usuario = u.id_usuario";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Libro libro = mapearLibro(rs);
                libro.setPropietario(rs.getString("propietario"));
                lista.add(libro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<Libro> obtenerPorUsuario(int idUsuario) {
        List<Libro> lista = new ArrayList<>();
        String sql = "SELECT l.*, u.nombre AS propietario FROM libros l INNER JOIN usuarios u ON l.id_usuario = u.id_usuario WHERE l.id_usuario = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Libro libro = mapearLibro(rs);
                    libro.setPropietario(rs.getString("propietario"));
                    lista.add(libro);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public Libro obtenerPorId(int idLibro) {
        String sql = "SELECT l.*, u.nombre AS propietario FROM libros l INNER JOIN usuarios u ON l.id_usuario = u.id_usuario WHERE l.id_libro = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idLibro);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Libro libro = mapearLibro(rs);
                    libro.setPropietario(rs.getString("propietario"));
                    return libro;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean insertar(Libro libro) {
        String sql = "INSERT INTO libros (titulo, autor, genero, formato, estado, paginas, calificacion, prestado, id_usuario) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, libro.getTitulo());
            stmt.setString(2, libro.getAutor());
            stmt.setString(3, libro.getGenero());
            stmt.setString(4, libro.getFormato());
            stmt.setString(5, libro.getEstado());
            stmt.setInt(6, libro.getPaginas());
            stmt.setDouble(7, libro.getCalificacion());
            stmt.setBoolean(8, libro.isPrestado());
            stmt.setInt(9, libro.getIdUsuario());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean actualizar(Libro libro) {
        String sql = "UPDATE libros SET titulo=?, autor=?, genero=?, formato=?, estado=?, paginas=?, calificacion=?, prestado=? WHERE id_libro=?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, libro.getTitulo());
            stmt.setString(2, libro.getAutor());
            stmt.setString(3, libro.getGenero());
            stmt.setString(4, libro.getFormato());
            stmt.setString(5, libro.getEstado());
            stmt.setInt(6, libro.getPaginas());
            stmt.setDouble(7, libro.getCalificacion());
            stmt.setBoolean(8, libro.isPrestado());
            stmt.setInt(9, libro.getIdLibro());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminar(int idLibro) {
        String sql = "DELETE FROM libros WHERE id_libro = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idLibro);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Libro mapearLibro(ResultSet rs) throws SQLException {
        Libro libro = new Libro();
        libro.setIdLibro(rs.getInt("id_libro"));
        libro.setTitulo(rs.getString("titulo"));
        libro.setAutor(rs.getString("autor"));
        libro.setGenero(rs.getString("genero"));
        libro.setFormato(rs.getString("formato"));
        libro.setEstado(rs.getString("estado"));
        libro.setPaginas(rs.getInt("paginas"));
        libro.setCalificacion(rs.getDouble("calificacion"));
        libro.setPrestado(rs.getBoolean("prestado"));
        libro.setIdUsuario(rs.getInt("id_usuario"));
        return libro;
    }
}
