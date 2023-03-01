package com.example.photoapp;

public class Imagen {
    private int id;
    private String nombre;
    private String ruta;
    private String fecha;

    public Imagen() {}

    public Imagen(int id, String nombre, String ruta, String fecha) {
        this.id = id;
        this.nombre = nombre;
        this.ruta = ruta;
        this.fecha = fecha;
    }

    // Métodos get y set para los atributos de la imagen

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
