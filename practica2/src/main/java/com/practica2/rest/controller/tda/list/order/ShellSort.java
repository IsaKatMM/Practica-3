package com.practica2.rest.controller.tda.list.order;
import java.lang.reflect.Method;
import com.practica2.rest.controller.tda.list.LinkedList;

public class ShellSort<E> {

 
    public LinkedList<E> sortPrimitiveAscendent(LinkedList<E> linkedList) {
        E[] array = linkedList.toArray();
        int n = array.length;
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                E temp = array[i];
                int j = i;
                while (j >= gap && compare(array[j - gap], temp) > 0) {
                    array[j] = array[j - gap];
                    j -= gap;
                }
                array[j] = temp;
            }
        }
        linkedList.toList(array);
        return linkedList;
    }

    public LinkedList<E> sortPrimitiveDescendent(LinkedList<E> linkedList) {
        E[] array = linkedList.toArray();
        int n = array.length;
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                E temp = array[i];
                int j = i;
                while (j >= gap && compare(array[j - gap], temp) < 0) {
                    array[j] = array[j - gap];
                    j -= gap;
                }
                array[j] = temp;
            }
        }
        linkedList.toList(array);
        return linkedList;
    }

  
    public LinkedList<E> sortModelsAscendent(LinkedList<E> linkedList, String attribute) throws Exception {
        E[] array = linkedList.toArray();
        int n = array.length;
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                E temp = array[i];
                int j = i;
                while (j >= gap && compareByAttribute(array[j - gap], temp, attribute) > 0) {
                    array[j] = array[j - gap];
                    j -= gap;
                }
                array[j] = temp;
            }
        }
        linkedList.toList(array);
        return linkedList;
    }

  
    public LinkedList<E> sortModelsDescendent(LinkedList<E> linkedList, String attribute) throws Exception {
        E[] array = linkedList.toArray();
        int n = array.length;
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                E temp = array[i];
                int j = i;
                while (j >= gap && compareByAttribute(array[j - gap], temp, attribute) < 0) {
                    array[j] = array[j - gap];
                    j -= gap;
                }
                array[j] = temp;
            }
        }
        linkedList.toList(array);
        return linkedList;
    }

    
    @SuppressWarnings("unchecked")
    private int compare(E a, E b) {
        if (a instanceof Comparable && b instanceof Comparable) {
            return ((Comparable<E>) a).compareTo(b);
        }
        throw new IllegalArgumentException("Los elementos no son comparables.");
    }

 
    private int compareByAttribute(E a, E b, String attribute) throws Exception {
        Object valueA = getAttributeValue(a, attribute);
        Object valueB = getAttributeValue(b, attribute);

        if (valueA instanceof Comparable && valueB instanceof Comparable) {
            return ((Comparable<Object>) valueA).compareTo(valueB);
        }
        throw new IllegalArgumentException("Los atributos no son comparables.");
    }

   
    private Object getAttributeValue(E obj, String attribute) throws Exception {
        Method method = null;
        attribute = "get" + attribute.substring(0, 1).toUpperCase() + attribute.substring(1);
        for (Method m : obj.getClass().getMethods()) {
            if (m.getName().equals(attribute)) {
                method = m;
                break;
            }
        }
        if (method == null) {
            throw new NoSuchMethodException("No se encontró el atributo " + attribute + " en la clase " + obj.getClass().getName());
        }
        return method.invoke(obj);
    }
}


