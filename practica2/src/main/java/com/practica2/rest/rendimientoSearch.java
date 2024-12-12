package com.practica2.rest;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.practica2.rest.controller.tda.list.LinkedList;
import com.practica2.rest.controller.tda.list.search.Binary;

public class rendimientoSearch {
    public static void main(String[] args) {
        
        LinkedList<Integer> linkedList = new LinkedList<>();
        for (int i = 0; i < 10000; i++) {

            linkedList.add(new Random().nextInt(100000));
        }
        linkedList.add(23); 

        Integer[] array = linkedList.toArray();

        java.util.Arrays.sort(array);

        linkedList = linkedList.toList(array);

        Binary binary = new Binary();
        int elementoBuscar = 23; 

        long inicioTiempo = System.nanoTime();
        int resultado = binary.binaryPrimitive(array, elementoBuscar, 0, array.length - 1);
        long finTiempo = System.nanoTime();

        //long tiempoEjecucion = TimeUnit.MILLISECONDS.convert(finTiempo - inicioTiempo, TimeUnit.NANOSECONDS);
        long tiempoEjecucion = TimeUnit.MICROSECONDS.convert(finTiempo - inicioTiempo, TimeUnit.NANOSECONDS);

        if (resultado != -1) {
            System.out.println("Elemento " + elementoBuscar + " encontrado en la posición: " + resultado);
        } else {
            System.out.println("Elemento " + elementoBuscar + " no encontrado.");
        }
        System.out.println("Tiempo de ejecución: " + tiempoEjecucion + " ms");
    }
}