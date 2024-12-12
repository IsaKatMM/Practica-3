package com.practica2.rest.controller.dao.services;

import java.util.List;

import com.practica2.rest.controller.dao.GeneradorDao;
import com.practica2.rest.controller.tda.list.LinkedList;
import com.practica2.rest.models.Generador;
public class GeneradorServices {
    private GeneradorDao obj;

    public GeneradorServices() {
        obj = new GeneradorDao();
    }

    public LinkedList<Generador> listAll() {
        return obj.getListAll();
    }
    public Generador getGenerador() {
        return obj.getGenerador();
    }

    public void setGenerador(Generador generador) {
        obj.setGenerador(generador);
    }

    public Boolean save(Generador generador) throws Exception {
        obj.setGenerador(generador);
        return obj.save();  // Pasar el generador al DAO para persistirlo
    }

    public Boolean update(Generador generador) throws Exception {
        obj.setGenerador(generador);
        return obj.update();  // Pasar el generador al DAO para actualizarlo
    }

    public Generador get(Integer id) throws Exception {
        return obj.get(id);  // Obtener un generador por ID
    }

    public LinkedList<Generador> getGeneratorsByFamily(Integer familyId) {
        LinkedList<Generador> result = new LinkedList<Generador>(); // Usando tu clase personalizada LinkedList
        // Lógica para llenar la lista result
        return result;
    }
      
    
}

