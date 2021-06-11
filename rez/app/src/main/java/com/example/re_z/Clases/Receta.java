package com.example.re_z.Clases;

public class Receta {
    private String id;
    private String imagen;
    private String titulo;
    private String dificultad;
    private String porciones;
    private String duracion;
    private String ingredientes;
    private String preparacion;
    private String email;

    public Receta(String id , String imagen, String titulo, String dificultad, String porciones, String duracion,
                  String ingredientes, String preparacion, String email) {
        this.id = id;
        this.imagen = imagen;
        this.titulo = titulo;
        this.dificultad = dificultad;
        this.porciones = porciones;
        this.duracion = duracion;
        this.ingredientes = ingredientes;
        this.preparacion = preparacion;
        this.email = email;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getId(){
        return id;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
    public String getImagen(){
        return imagen;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getTitulo(){
        return titulo;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }
    public String getDificultad(){
        return dificultad;
    }

    public void setPorciones(String porciones) {
        this.porciones = porciones;
    }
    public String getPorciones(){
        return porciones;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }
    public String getDuracion(){
        return duracion;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }
    public String getIngredientes(){
        return ingredientes;
    }

    public void setPreparacion(String preparacion) {
        this.preparacion = preparacion;
    }
    public String getPreparacion(){
        return preparacion;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}