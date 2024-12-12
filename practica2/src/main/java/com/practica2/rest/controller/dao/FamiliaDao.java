package com.practica2.rest.controller.dao;

import com.practica2.rest.controller.dao.implement.AdapterDao;
import com.practica2.rest.controller.tda.list.LinkedList;
import com.practica2.rest.models.Familia;
import com.practica2.rest.controller.tda.list.order.QuickSort;
import com.practica2.rest.controller.tda.list.order.MergeSort;
import com.practica2.rest.controller.tda.list.order.ShellSort;
import com.practica2.rest.controller.tda.list.search.Binary;
import com.practica2.rest.controller.tda.list.search.BinarySecuencial;
import com.practica2.rest.controller.tda.list.search.LinearBinarySearch;
import java.lang.reflect.Method;

public class FamiliaDao extends AdapterDao<Familia> {
    private Familia familia;
    private LinkedList<Familia> listAll;

    public FamiliaDao() {
        super(Familia.class);
    }

    public Familia getFamilia() {
        if (familia == null) {
            familia = new Familia();
        }
        return this.familia;
    }

    public void setFamilia(Familia familia) {
        this.familia = familia;
    }

    public LinkedList<Familia> getListAll() {
        if (listAll == null || listAll.isEmpty()) {
            this.listAll = super.listAll(); 
            if (listAll == null || listAll.isEmpty()) {
                throw new RuntimeException("La lista está vacía o es nula.");
            }
        }
        //System.out.println("Lista de familias: " + listAll); // Línea de depuración
        return listAll;
    }

    public Boolean save() throws Exception {
        Integer id = getListAll().getSize() + 1;
        familia.setId(id);
        this.persist(this.familia);
        return true;
    }

    public Boolean update() throws Exception {
        this.merge(getFamilia(), getFamilia().getId() - 1);
        this.listAll = listAll();
        return true;
    }

    public LinkedList<Familia> order(Integer type_order, String atributo) {
        LinkedList<Familia> listita = getListAll();
      
        if (!listita.isEmpty()) {
            Familia[] lista = (Familia[]) listita.toArray();
            
            listita.reset();
            for (int i = 1; i < lista.length; i++) {
                Familia aux = lista[i];
               
                int j = i - 1;
               
                while (j >= 0 && verify(lista[j], aux, type_order, atributo)) {
                    lista[j + 1] = lista[j--];
                    
                }
                lista[j + 1] = aux;
                
            }
            listita.toList(lista);
        }
        return listita;
    }

    public Boolean verify(Familia a, Familia b, Integer type_order, String atributo) {
        if (type_order == 1) {
            if (atributo.equalsIgnoreCase("apellidos")) {
                return a.getApellidos().compareTo(b.getApellidos()) > 0;
            } else if (atributo.equalsIgnoreCase("direccion")) {
                return a.getDireccion().compareTo(b.getDireccion()) > 0;
            } else if (atributo.equalsIgnoreCase("id")) {
                return a.getId() > b.getId(); // se lo pone asi por ser numerico
            }
        } else {
            if (atributo.equalsIgnoreCase("apellidos")) {
                return a.getApellidos().compareTo(b.getApellidos()) < 0;
            } else if (atributo.equalsIgnoreCase("direccion")) {
                return a.getDireccion().compareTo(b.getDireccion()) < 0;
            } else if (atributo.equalsIgnoreCase("id")) {
                return a.getId() < b.getId(); // se lo pone asi por ser numerico
            }
        }
        return false;
    }

    public LinkedList pruebaOrden(String atributo, Integer type, String metodo) throws Exception {
        LinkedList listaFamilia = getListAll();
        if (metodo.equals("quick")) {
            QuickSort<Familia> quickSort = new QuickSort<>();
            if (type == 0) {
                listaFamilia = quickSort.sortModelsAscendent(listaFamilia, atributo);
            } else if (type == 1) {
                listaFamilia = quickSort.sortModelsDescendent(listaFamilia, atributo);
            }
        } else if (metodo.equals("merge")) {
            MergeSort<Familia> mergeSort = new MergeSort<>();
            if (type == 0) {
                listaFamilia = mergeSort.sortModelsAscendent(listaFamilia, atributo);
            } else if (type == 1) {
                listaFamilia = mergeSort.sortModelsDescendent(listaFamilia, atributo);
            }
        } else if (metodo.equals("shell")) {
            ShellSort<Familia> shellSort = new ShellSort<>();
            if (type == 0) {
                listaFamilia = shellSort.sortModelsAscendent(listaFamilia, atributo);
            } else if (type == 1) {
                listaFamilia = shellSort.sortModelsDescendent(listaFamilia, atributo);
            }
        }
        return listaFamilia;
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }


    public Familia buscarFamilia(String atributo, String valor) {
        MergeSort<Familia> mergeSort = new MergeSort<>();
        try {
            listAll = getListAll(); 
            listAll = mergeSort.sortModelsAscendent(listAll, atributo);
        } catch (Exception e) {
            e.printStackTrace();
            return null; 
        }
        Binary binarySearch = new Binary(); 
        try {
            Object resultado = binarySearch.searchModels(listAll, valor, atributo, 0, listAll.getSize() - 1, "binary");
            if (resultado != null && resultado instanceof Familia) {
                return (Familia) resultado;
            } else {
                return null; 
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null; 
        }
    }

    public LinkedList<Familia> searchByBinarySecuencial(String atributo, String valor) throws Exception {
        LinkedList<Familia> list = getListAll();
        BinarySecuencial binarySecuencial = new BinarySecuencial();
        return binarySecuencial.binarySecuencialSearch(list, valor, atributo);
    }


}
