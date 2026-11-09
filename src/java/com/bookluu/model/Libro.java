package com.bookluu.model;

public class Libro {
    private int idLibro;
    private String titulo;
    private String autor;
    private String genero;
    private String formato;    // "Físico" o "Digital"
    private String estado;     // "Pendiente", "En lectura", "Terminado"
    private int paginas;
    private double calificacion;
    private boolean prestado;
    private int idUsuario;
    private String propietario; // Atributo para almacenar el resultado del JOIN con usuarios

    public Libro() {}

    public Libro(int idLibro, String titulo, String autor, String genero, String formato,
                 String estado, int paginas, double calificacion, boolean prestado, int idUsuario) {
        this.idLibro = idLibro;
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.formato = formato;
        this.estado = estado;
        this.paginas = paginas;
        this.calificacion = calificacion;
        this.prestado = prestado;
        this.idUsuario = idUsuario;
    }

    // Getters y Setters
    public int getIdLibro() { return idLibro; }
    public void setIdLibro(int idLibro) { this.idLibro = idLibro; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public String getFormato() { return formato; }
    public void setFormato(String formato) { this.formato = formato; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public int getPaginas() { return paginas; }
    public void setPaginas(int paginas) { this.paginas = paginas; }

    public double getCalificacion() { return calificacion; }
    public void setCalificacion(double calificacion) { this.calificacion = calificacion; }

    public boolean isPrestado() { return prestado; }
    public void setPrestado(boolean prestado) { this.prestado = prestado; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getPropietario() { return propietario; }
    public void setPropietario(String propietario) { this.propietario = propietario; }
}