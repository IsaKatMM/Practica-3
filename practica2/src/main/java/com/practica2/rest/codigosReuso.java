package com.practica2.rest;

public class codigosReuso {
    
}

    /*
     * public void quickSort(Integer type, String atributo) {
     * if (!listAll().isEmpty()) { //verifica si la lista no esta vacia
     * Familia[] array = listAll.toArray(); //convierte la lista a array
     * quicksort(array, 0, array.length - 1, type, atributo); //esta llamndo al
     * metodo de prden quicksort
     * listAll.toList(array); // convierte el array ordenado a lista enlazada
     * }
     * }
     * 
     * // Método recursivo de quicksort
     * private void quicksort(Familia[] array, int low, int high, Integer type,
     * String atributo) {
     * if (low < high) { //verifica si el indice es valido
     * int pi = partition(array, low, high, type, atributo); //obtine el indice de
     * particion
     * quicksort(array, low, pi - 1, type, atributo); //ordena de izquierda
     * quicksort(array, pi + 1, high, type, atributo); //ordena de derecha
     * }
     * }
     * 
     * // Método de partición
     * private int partition(Familia[] array, int low, int high, Integer type,
     * String atributo) {
     * Familia pivot = array[high]; //obtiene el pivote del ultimo elemento
     * int i = (low - 1); //elemento menor
     * 
     * for (int j = low; j < high; j++) { //recorre el array desde el inicio hasta
     * el final low(bajo) hasta high(alto)
     * if (compare(array[j], pivot, type, atributo)) { //compara los elementos con
     * el pivote
     * i++;
     * swap(array, i, j);
     * }
     * }
     * swap(array, i + 1, high); // pone el pivote en su lugar
     * return i + 1; //retorna el indice d
     * }
     * 
     * // Método de comparación basado en el atributo y tipo de orden
     * private boolean compare(Familia a, Familia b, Integer type, String atributo)
     * {
     * try {
     * Method methodA = a.getClass().getMethod("get" + capitalize(atributo));
     * Method methodB = b.getClass().getMethod("get" + capitalize(atributo));
     * Comparable valueA = (Comparable) methodA.invoke(a);
     * Comparable valueB = (Comparable) methodB.invoke(b);
     * return type.equals(LinkedList.ASC) ? valueA.compareTo(valueB) < 0 :
     * valueA.compareTo(valueB) > 0;
     * } catch (Exception e) {
     * throw new RuntimeException(e);
     * }
     * }
     * 
     * // Método para intercambiar elementos en el array
     * private void swap(Familia[] array, int i, int j) {
     * Familia temp = array[i];
     * array[i] = array[j];
     * array[j] = temp;
     * }
     * 
     * // Método para capitalizar la primera letra del atributo
     * private String capitalize(String str) {
     * if (str == null || str.isEmpty()) {
     * return str;
     * }
     * return str.substring(0, 1).toUpperCase() + str.substring(1);
     * }
     */

      // public void quickSort(Integer type, String atributo) {
    // QuickSort<Familia> quickSort = new QuickSort<>();
    // try {
    // System.out.println("Ordenando por: " + atributo + ", Tipo: " + type);
    // if (type.equals(LinkedList.ASC)) {
    // listAll = quickSort.sortModelsAscendent(listAll, capitalize(atributo));
    // } else {
    // listAll = quickSort.sortModelsDescendent(listAll, capitalize(atributo));
    // }
    // } catch (Exception e) {
    // e.printStackTrace();
    // throw new RuntimeException("Error durante QuickSort", e);
    // }
    // }

    /*public void quickSort(Integer type, String atributo) {
        QuickSort<Familia> quickSort = new QuickSort<>();
        try {
            System.out.println("Ordenando por: " + atributo + ", Tipo: " + type);
            if (type.equals(LinkedList.ASC)) {
                listAll = quickSort.sortModelsAscendent(listAll, atributo);
            } else {
                listAll = quickSort.sortModelsDescendent(listAll, atributo);
            }
            System.out.println("Lista ordenada: " + listAll.toString());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error durante QuickSort: " + e.getMessage(), e);
        }
    }*/