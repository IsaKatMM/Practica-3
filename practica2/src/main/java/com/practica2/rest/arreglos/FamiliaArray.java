package com.practica2.rest.arreglos;
import com.practica2.rest.controller.tda.list.LinkedList;
import com.practica2.rest.controller.tda.list.Exception.ListEmptyException;
import com.practica2.rest.models.Familia;

public class FamiliaArray {
    private LinkedList<Familia> familiaList;

    // Constructor
    public FamiliaArray() {
        this.familiaList = new LinkedList<>();
    }

    // Método para agregar una familia
    public void addFamilia(Familia familia) {
        familiaList.add(familia);
    }

    // Método para obtener una familia
    public Familia getFamilia(int index) throws ListEmptyException, IndexOutOfBoundsException {
        return familiaList.get(index);
    }

    // Método para actualizar una familia
    //public void updateFamilia(Familia familia, int index) throws ListEmptyException, IndexOutOfBoundsException {
      //  familiaList.update(familia, index);
    //}

    // Método para convertir la lista a un arreglo
    public Familia[] toArray() {
        return familiaList.toArray();
    }

    // Método para mostrar todos las familias
    public void mostrarFamilia() {
        System.out.println(familiaList.toString());
    }
}
