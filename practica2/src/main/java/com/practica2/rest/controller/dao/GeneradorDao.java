package com.practica2.rest.controller.dao;

import com.practica2.rest.controller.tda.list.LinkedList;  // Usamos tu LinkedList personalizada
import com.practica2.rest.models.Generador;
import com.practica2.rest.controller.dao.implement.AdapterDao;

public class GeneradorDao extends AdapterDao<Generador> {
    private Generador generador;
    private LinkedList<Generador> generadores;  // Usamos LinkedList personalizada con tipo Generador
    private LinkedList<Generador> listAll;

    public GeneradorDao() {
        super(Generador.class);
        this.generadores = new LinkedList<>();  // Inicializamos la lista de generadores
    }

    public Generador getGenerador() {
        if (generador == null) {
            generador = new Generador();
        }
        return this.generador;
    }

    public void setGenerador(Generador generador) {
        this.generador = generador;
    }

    public LinkedList<Generador> getListAll() {
        if (listAll == null) {
            this.listAll = new LinkedList<>();  // Inicializamos la lista de todos los generadores
        }
        return listAll;
    }

    public Boolean save() throws Exception {
        Integer id = getListAll().getSize() + 1;  // Usamos getSize() de tu LinkedList
        generador.setId(id);
        this.persist(this.generador);
        return true;
    }

    public Boolean update() throws Exception {
        this.merge(getGenerador(), getGenerador().getId() - 1);
        this.listAll = listAll();  // Reemplazamos listAll con el contenido actualizado
        return true;
    }

    // Método para agregar un generador a la lista
    public void addGenerador(Generador generador) {
        generadores.add(generador);  // Usamos el método add de LinkedList
    }

    // Método para obtener los generadores de una familia (simulación)
    public LinkedList<Generador> getGeneratorsByFamily(Integer familyId) {
        LinkedList<Generador> result = new LinkedList<>();  // Lista de generadores de la familia
        // Iteramos sobre la lista de generadores
        for (int i = 0; i < generadores.getSize(); i++) {
            try {
                Generador generador = generadores.get(i);
                if (generador.getFamilyId().equals(familyId)) {
                    result.add(generador);  // Agregamos el generador a la lista de resultados
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
