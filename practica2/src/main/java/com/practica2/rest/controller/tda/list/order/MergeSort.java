package com.practica2.rest.controller.tda.list.order;
import java.lang.reflect.Method;
import com.practica2.rest.controller.tda.list.LinkedList;

public class MergeSort<E> {

    private Object[] buffer;


    public LinkedList<E> sortPrimitiveAscendent(LinkedList<E> linkedList) {
        E[] array = linkedList.toArray();
        buffer = new Object[array.length];
        mergeSort(array, 0, array.length - 1, null);
        linkedList.toList(array);
        return linkedList;
    }

    public LinkedList<E> sortPrimitiveDescendent(LinkedList<E> linkedList) {
        E[] array = linkedList.toArray();
        buffer = new Object[array.length];
        mergeSort(array, 0, array.length - 1, null);
        reverseArray(array);
        linkedList.toList(array);
        return linkedList;
    }


    public LinkedList<E> sortModelsAscendent(LinkedList<E> linkedList, String attribute) throws Exception {
        E[] array = linkedList.toArray();
        buffer = new Object[array.length];
        mergeSort(array, 0, array.length - 1, attribute);
        linkedList.toList(array);
        return linkedList;
    }


    public LinkedList<E> sortModelsDescendent(LinkedList<E> linkedList, String attribute) throws Exception {
        E[] array = linkedList.toArray();
        buffer = new Object[array.length];
        mergeSort(array, 0, array.length - 1, attribute);
        reverseArray(array);
        linkedList.toList(array);
        return linkedList;
    }

  
    private void mergeSort(E[] array, int start, int end, String attribute) {
        if (start < end) {
            int mid = (start + end) / 2;
            mergeSort(array, start, mid, attribute);
            mergeSort(array, mid + 1, end, attribute);
            mergeTwoArrays(array, start, mid, end, attribute);
        }
    }

  
    private void mergeTwoArrays(E[] array, int start, int mid, int end, String attribute) {
        int left = start, right = mid + 1, i = start;

        while (left <= mid && right <= end) {
            try {
                boolean condition;
                if (attribute != null) {
                    condition = compareByAttribute(array[left], array[right], attribute) <= 0;
                } else {
                    condition = compare(array[left], array[right]) <= 0;
                }

                if (condition) {
                    buffer[i] = array[left];
                    left++;
                } else {
                    buffer[i] = array[right];
                    right++;
                }
                i++;
            } catch (Exception e) {
                throw new RuntimeException("Error al comparar elementos: " + e.getMessage());
            }
        }

        while (left <= mid) {
            buffer[i] = array[left];
            left++;
            i++;
        }

        while (right <= end) {
            buffer[i] = array[right];
            right++;
            i++;
        }

        for (int j = start; j <= end; j++) {
            array[j] = (E) buffer[j];
        }
    }

 
    private void reverseArray(E[] array) {
        int left = 0, right = array.length - 1;
        while (left < right) {
            E temp = array[left];
            array[left] = array[right];
            array[right] = temp;
            left++;
            right--;
        }
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

