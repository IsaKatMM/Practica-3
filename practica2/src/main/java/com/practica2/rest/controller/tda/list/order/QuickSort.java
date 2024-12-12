package com.practica2.rest.controller.tda.list.order;
import java.lang.reflect.Method;
import java.util.Random;
import com.practica2.rest.controller.tda.list.LinkedList;
import java.util.Arrays;

public class QuickSort<E> {

    public LinkedList<E> sortPrimitiveAscendent(LinkedList<E> linkedList) throws Exception {
        E[] array = linkedList.toArray();
        quickSortPrimitive(array, 0, array.length - 1, LinkedList.ASC);
        linkedList.toList(array);
        return linkedList;
    }

    public LinkedList<E> sortPrimitiveDescendent(LinkedList<E> linkedList) throws Exception {
        E[] array = linkedList.toArray();
        quickSortPrimitive(array, 0, array.length - 1, LinkedList.DESC);
        linkedList.toList(array);
        return linkedList;
    }

    public LinkedList<E> sortModelsAscendent(LinkedList<E> linkedList, String attribute) throws Exception {
        if (linkedList == null || linkedList.isEmpty()) {
            throw new IllegalArgumentException("La lista está vacía o es nula.");
        }
        E[] array = linkedList.toArray();
        System.out.println("Array inicial: " + Arrays.toString(array)); // Línea de depuración
        quickSortModels(array, 0, array.length - 1, attribute, LinkedList.ASC);
        linkedList.toList(array);
        return linkedList;
    }

    public LinkedList<E> sortModelsDescendent(LinkedList<E> linkedList, String attribute) throws Exception {
        if (linkedList == null || linkedList.isEmpty()) {
            throw new IllegalArgumentException("La lista está vacía o es nula.");
        }
        E[] array = linkedList.toArray();
        System.out.println("Array inicial: " + Arrays.toString(array)); // Línea de depuración
        quickSortModels(array, 0, array.length - 1, attribute, LinkedList.DESC);
        linkedList.toList(array);
        return linkedList;
    }

    private void quickSortPrimitive(E[] array, int low, int high, int order) {
        if (low < high) {
            int pivotIndex = partitionPrimitive(array, low, high, order);
            quickSortPrimitive(array, low, pivotIndex - 1, order);
            quickSortPrimitive(array, pivotIndex + 1, high, order);
        }
    }

    /*private int partitionPrimitive(E[] array, int low, int high, int order) {
        E pivot = array[new Random().nextInt(high - low + 1) + low];
        int i = low - 1;
        for (int j = low; j <= high; j++) {
            if (compare(array[j], pivot, order)) {
                i++;
                E temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        return i;
    }*/
    private int partitionPrimitive(E[] array, int low, int high, int order) {
        E pivot = array[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (compare(array[j], pivot, order)) {
                i++;
                E temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        E temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;
        return i + 1;
    }

    private void quickSortModels(E[] array, int low, int high, String attribute, int order) throws Exception {
        if (low < high) {
            System.out.println("Antes de partitionModels"); // Línea de depuración
            int pivotIndex = partitionModels(array, low, high, attribute, order);
            quickSortModels(array, low, pivotIndex - 1, attribute, order);
            quickSortModels(array, pivotIndex + 1, high, attribute, order);
        }
    }

    private int partitionModels(E[] array, int low, int high, String attribute, int order) throws Exception {
        E pivot = array[new Random().nextInt(high - low + 1) + low];
        System.out.println("Pivot elegido: " + pivot); // Línea de depuración
        Object pivotValue = existAttribute(pivot, attribute);
        System.out.println("Pivot value: " + pivotValue); // Línea de depuración
        int i = low - 1;
        for (int j = low; j <= high; j++) {
            Object value = existAttribute(array[j], attribute);
            System.out.println("Comparando " + value + " con " + pivotValue); // Línea de depuración
            if (compare(value, pivotValue, order)) {
                i++;
                E temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        return i;
    }

    private Object existAttribute(E obj, String attribute) throws Exception {
        Method method = null;
        attribute = "get" + attribute.substring(0, 1).toUpperCase() + attribute.substring(1);
        System.out.println("Buscando método: " + attribute + " en la clase " + obj.getClass().getName()); // Línea de depuración
        for (Method m : obj.getClass().getMethods()) {
            if (m.getName().equals(attribute)) {
                method = m;
                break;
            }
        }
        if (method == null) {
            throw new NoSuchMethodException("Attribute " + attribute + " not found in class " + obj.getClass().getName());
        }
        return method.invoke(obj);
    }

    /*private boolean compare(Object a, Object b, int order) {
        if (a == null || b == null) {
            System.out.println("Uno de los valores es nulo: a = " + a + ", b = " + b); // Línea de depuración
            throw new IllegalArgumentException("No se pueden comparar valores nulos.");
        }
        if (a instanceof Comparable && b instanceof Comparable) {
            Comparable<Object> compA = (Comparable<Object>) a;
           // System.out.println("Comparando " + a + " con " + b + " en orden " + (order == LinkedList.ASC ? "ascendente" : "descendente")); // Línea de depuración
            if (order == LinkedList.ASC) {
                return compA.compareTo(b) < 0;
            } else {
                return compA.compareTo(b) > 0;
            }
        }
        throw new IllegalArgumentException("Los atributos no son comparables.");
    }*/
    private boolean compare(Object a, Object b, int order) {
        if (a == null || b == null) {
            throw new IllegalArgumentException("No se pueden comparar valores nulos.");
        }
        if (a instanceof Comparable && b instanceof Comparable) {
            Comparable<Object> compA = (Comparable<Object>) a;
            if (order == LinkedList.ASC) {
                return compA.compareTo(b) <= 0; 
            } else {
                return compA.compareTo(b) >= 0; 
            }
        }
        throw new IllegalArgumentException("Los atributos no son comparables.");
    }
}
