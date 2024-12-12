package com.practica2.rest.controller.tda.list.search;

import java.lang.reflect.Method;
import com.practica2.rest.controller.tda.list.LinkedList;
import com.practica2.rest.controller.tda.list.order.MergeSort;


public class BinarySecuencial {

    public <E> LinkedList<E> binarySecuencialSearch(LinkedList<E> list, String data, String attribute)
            throws Exception {
        MergeSort<E> mergeSort = new MergeSort<>();
        LinkedList<E> sortedList = mergeSort.sortModelsAscendent(list, attribute);

        LinkedList<E> resultList = new LinkedList<>();
        int left = 0;
        int right = sortedList.getSize() - 1;

        while (left <= right) {
            int mid = (left + right) / 2;

            E midElement = sortedList.get(mid);
            String midValue = (String) getAttributeValue(midElement, attribute);
            int comparison = midValue.toLowerCase().compareTo(data.toLowerCase());

            if (comparison == 0) {
                for (int i = mid; i < sortedList.getSize(); i++) {
                    E element = sortedList.get(i);
                    String elementValue = (String) getAttributeValue(element, attribute);
                    if (elementValue.toLowerCase().equals(data.toLowerCase())) {
                        resultList.add(element);
                    } else {
                        break;
                    }
                }
                for (int i = mid - 1; i >= 0; i--) {
                    E element = sortedList.get(i);
                    String elementValue = (String) getAttributeValue(element, attribute);
                    if (elementValue.toLowerCase().equals(data.toLowerCase())) {
                        resultList.add(element);
                    } else {
                        break;
                    }
                }
                break;
            } else if (comparison < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return resultList;
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