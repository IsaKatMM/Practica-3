package com.practica2.rest.controller.tda.list.search;

import java.lang.reflect.Method;
import com.practica2.rest.controller.tda.list.LinkedList;
import com.practica2.rest.models.Familia;

public class Binary {

    public int binaryPrimitive(Object[] array, Object data, int low, int high) {
        while (low <= high) {
            int mid = (low + high) / 2;
            if (array[mid].equals(data)) {
                return mid;
            } else if (((Comparable<Object>) array[mid]).compareTo(data) < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    public int binaryString(String[] array, String data, int low, int high) {
        while (low <= high) {
            int mid = (low + high) / 2;
            if (array[mid].toLowerCase().endsWith(data.toLowerCase())) {
                return mid;
            } else if (array[mid].toLowerCase().compareTo(data.toLowerCase()) < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    public Familia searchModels(LinkedList<Familia> list, String element, String attribute, int low, int high, String method) throws Exception {
        if ("binary".equals(method)) {
            while (low <= high) {
                int mid = (low + high) / 2;
                Familia model = list.get(mid);
                Object midValue = getAttributeValue(model, attribute);

                if (midValue.toString().equalsIgnoreCase(element)) {
                    return model;
                } else if (((Comparable<Object>) midValue).compareTo(element) < 0) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
            return null;
        } else if ("sequential".equals(method)) {
            for (int i = 0; i < list.getSize(); i++) {
                Familia model = list.get(i);
                Object modelValue = getAttributeValue(model, attribute);
                if (modelValue.toString().equalsIgnoreCase(element)) {
                    return model;
                }
            }
            return null;
        } else {
            throw new Exception("Método de búsqueda no válido. Debe ser 'binary' o 'sequential'.");
        }
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
