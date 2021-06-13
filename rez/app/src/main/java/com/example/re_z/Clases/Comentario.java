package com.example.re_z.Clases;

public class Comentario {
    private String id;
    private String imagen;
    private String usuario;
    private String fecha;
    private String comentario;
    private String receta;

    public Comentario(String id, String imagen, String usuario, String fecha, String comentario, String receta) {
        this.id = id;
        this.imagen = imagen;
        this.usuario = usuario;
        this.fecha = fecha;
        this.comentario = comentario;
        this.receta = receta;
    }

    public String getReceta() {
        return receta;
    }

    public void setReceta(String receta) {
        this.receta = receta;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

}
