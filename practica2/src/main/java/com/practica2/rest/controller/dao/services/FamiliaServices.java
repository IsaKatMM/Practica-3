package com.practica2.rest.controller.dao.services;

import com.practica2.rest.controller.dao.FamiliaDao;
import com.practica2.rest.controller.tda.list.LinkedList;
import com.practica2.rest.models.Familia;

public class FamiliaServices {
    private FamiliaDao obj;
    private FamiliaDao familiaDao = new FamiliaDao();

    public FamiliaServices() {
        obj = new FamiliaDao();
    }

    public Boolean save() throws Exception {
        return obj.save();
    }

    public Boolean update() throws Exception {
        return obj.update();
    }

 
    public LinkedList<Familia> listAll() {
        return familiaDao.getListAll();
    }

    
    public Familia getFamilia() {
        return obj.getFamilia();
    }

    
    public void setFamilia(Familia familia) {
        obj.setFamilia(familia);
    }

    public Familia get(Integer id) throws Exception {
        return obj.get(id);
    }


    // PRACTICA #3
    // metodos de ordenamiento
    public LinkedList pruebaOrden(String atributo, Integer type, String metodo) throws Exception {
        return obj.pruebaOrden(atributo, type, metodo);
    }

    // Métodos de Búsqueda
    public Familia buscarFamiliaBinaria(String atributo, String valor) throws Exception {
        return obj.buscarFamilia(atributo, valor);
    }


    public LinkedList<Familia> buscarFamiliaBinariaSecuencial(String atributo, String valor) throws Exception {
        return obj.searchByBinarySecuencial(atributo, valor);
    }

}
