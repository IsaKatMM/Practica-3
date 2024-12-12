package com.practica2.rest;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.practica2.rest.controller.tda.list.LinkedList;
import com.practica2.rest.controller.tda.list.order.QuickSort;

public class rendimientoOrder {
    public static void main(String[] args) {
        LinkedList<Integer> list = new LinkedList<>();

        for (Integer i = 0; i < 25000; i++) {
            list.add(new Random().nextInt(1000000));
        }
        list.add(23); 

        QuickSort<Integer> quickSort = new QuickSort<>();

        try {
            long inicioTiempo = System.nanoTime();
            quickSort.sortPrimitiveAscendent(list);
            long finTiempo = System.nanoTime();

            long tiempoEjecucion = TimeUnit.MILLISECONDS.convert(finTiempo - inicioTiempo, TimeUnit.NANOSECONDS);
            System.out.println("Tiempo de ejecución del ordenamiento: " + tiempoEjecucion + " ns");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}