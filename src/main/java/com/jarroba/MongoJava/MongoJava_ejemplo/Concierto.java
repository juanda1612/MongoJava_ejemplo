package com.jarroba.MongoJava.MongoJava_ejemplo;

import org.bson.Document;

public class Concierto {
    private String nombre;
    private String lugar;
    private String fecha;
    private String hora;

    public Concierto(String nombre, String lugar, String fecha, String hora) {
        this.nombre = nombre;
        this.lugar = lugar;
        this.fecha = fecha;
        this.hora = hora;
    }

    // Constructor para transformar un objeto Document de MongoDB a un objeto Java
    public Concierto(Document document) {
        this.nombre = document.getString("nombre");
        this.lugar = document.getString("lugar");
        this.fecha = document.getString("fecha");
        this.hora = document.getString("hora");

    }

    // Métod0 para transformar un objeto Java a un Document de MongoDB
    public Document toDocument() {
        return new Document("nombre", this.nombre)
                .append("lugar", this.lugar)
                .append("fecha", this.fecha)
                .append("hora", this.hora);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    @Override
    public String toString() {
        return "Concierto{" +
                "nombre='" + nombre + '\'' +
                ", lugar='" + lugar + '\'' +
                ", fecha='" + fecha + '\'' +
                ", hora='" + hora + '\'' +
                '}';
    }
}
