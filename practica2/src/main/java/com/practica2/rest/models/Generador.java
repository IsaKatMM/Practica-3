package com.practica2.rest.models;

public class Generador {
    private Integer id;
    private Float coste;
    private Float potencia;//cuantto produce
    private Float consumoHora;
    private String tipoUso;
    private Integer familyId;

    public Generador(){
    }


public Generador (Integer id, Float coste, Float potencia, Float consumoHora, String tipoUso, Integer familyId){
    this.id= id;
    this.coste= coste;
    this.potencia= potencia;
    this.consumoHora= consumoHora;
    this.tipoUso= tipoUso;
    this.familyId= familyId;
}
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getCoste() {
        return this.coste;
    }

    public void setCoste(Float coste) {
        this.coste = coste;
    }

    public Float getPotencia() {
        return this.potencia;
    }

    public void setPotencia(Float potencia) {
        this.potencia = potencia;
    }

    public Float getConsumoHora() {
        return this.consumoHora;
    }

    public void setConsumoHora(Float consumoHora) {
        this.consumoHora = consumoHora;
    }

    public String getTipoUso() {
        return this.tipoUso;
    }

    public void setTipoUso(String tipoUso) {
        this.tipoUso = tipoUso;
    }

    public Integer getFamilyId() {
        return familyId;
    }

    public void setFamilyId(Integer familyId) {
        this.familyId = familyId;
    }


    
}
