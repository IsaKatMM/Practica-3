package com.practica2.rest.controller.tda.list.search;

import com.practica2.rest.controller.tda.list.LinkedList;
import com.practica2.rest.models.Familia;
import java.lang.reflect.Method;

public class LinearBinarySearch {

    public int linearBinarySearch(LinkedList<Familia> list, Familia data, String attribute) throws Exception {
        
        if (list == null || list.getSize() == 0) {
            return -1; // Lista vacía
        }

        Familia[] array = (Familia[]) list.toArray();

        int low = 0;
        int high = array.length - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            Familia midElement = array[mid];
            Object midValue = getAttributeValue(midElement, attribute);
            Object dataValue = getAttributeValue(data, attribute);

            if (midValue.equals(dataValue)) {
                return mid; 
            }

            if (((Comparable<Object>) midValue).compareTo(dataValue) < 0) {
                low = mid + 1; 
            } else {
                high = mid - 1;
            }
        }

        return linearSearch(array, data, attribute, low, high);
    }

    
    private int linearSearch(Familia[] array, Familia data, String attribute, int low, int high) throws Exception {
        Object dataValue = getAttributeValue(data, attribute);

        for (int i = low; i <= high; i++) {
            Object arrayValue = getAttributeValue(array[i], attribute);
            if (arrayValue.equals(dataValue)) {
                return i; 
            }
        }
        return -1; 
    }

    private Object getAttributeValue(Object model, String attribute) throws Exception {
        Method method = model.getClass().getMethod("get" + capitalize(attribute));
        return method.invoke(model);
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
