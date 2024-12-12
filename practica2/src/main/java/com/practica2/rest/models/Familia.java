package com.practica2.rest.models;

public class Familia {
    private Integer id;
    private String apellidos;
    private String direccion;
    private String sector;
    private String codigoHogar;
    private Boolean adquirirGenerador;
    private Generador generador;

public Familia(){
    }


public Familia(Integer id, String apellidos, String direccion, String sector, String codigoHogar, Boolean adquirirGenerador, Generador generador){
    this.id= id;
    this.apellidos= apellidos;
    this.direccion= direccion; 
    this.sector= sector;
    this.codigoHogar= codigoHogar;
    this.adquirirGenerador= adquirirGenerador;
    this.generador= generador;
}
public Familia(Familia otraFamilia) {
    this.id = otraFamilia.getId();
    this.apellidos = otraFamilia.getApellidos();
    this.direccion = otraFamilia.getDireccion();
    this.sector = otraFamilia.getSector();
    this.codigoHogar = otraFamilia.getCodigoHogar();
    this.adquirirGenerador = otraFamilia.isAdquirirGenerador();
    this.generador = otraFamilia.getGenerador();
}
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApellidos() {
        return this.apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getSector() {
        return this.sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getCodigoHogar() {
        return this.codigoHogar;
    }

    public void setCodigoHogar(String codigoHogar) {
        this.codigoHogar = codigoHogar;
    }

    public Boolean isAdquirirGenerador() {
        return this.adquirirGenerador;
    }

    public Boolean getAdquirirGenerador() {
        return this.adquirirGenerador;
    }

    public void setAdquirirGenerador(Boolean adquirirGenerador) {
        this.adquirirGenerador = adquirirGenerador;
    }

    public Generador getGenerador() {
        return this.generador;
    }

    public void setGenerador(Generador generador) {
        this.generador = generador;
    }
}
