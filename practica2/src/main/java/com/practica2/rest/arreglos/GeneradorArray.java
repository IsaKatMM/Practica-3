package com.practica2.rest.arreglos;
import com.practica2.rest.controller.tda.list.LinkedList;
import com.practica2.rest.controller.tda.list.Exception.ListEmptyException;
import com.practica2.rest.models.Familia;
import com.practica2.rest.models.Generador;


public class GeneradorArray {
     private LinkedList<Generador> generadorList;

    // Constructor
    public GeneradorArray() {
        this.generadorList = new LinkedList<>();
    }

    // Método para agregar un   generador
    public void addGenerador(Generador generador) {
        generadorList.add(generador);
    }

    // Método para obtener un generador
    public Generador getGenerador(int index) throws ListEmptyException, IndexOutOfBoundsException {
        return generadorList.get(index);
    }

    // Método para actualizar un generador
  //  public void updateGenerador(Generador generador, int index) throws ListEmptyException, IndexOutOfBoundsException {
    //    generadorList.update(generador, index);
    //}

    // Método para convertir la lista a un arreglo
    public Generador[] toArray() {
        return generadorList.toArray();
    }

    // Método para mostrar todos los generadores
    public void mostrarGenerador() {
        System.out.println(generadorList.toString());
    }
}
