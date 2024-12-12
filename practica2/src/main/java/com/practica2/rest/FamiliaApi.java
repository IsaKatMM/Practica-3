package com.practica2.rest;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.practica2.rest.controller.dao.services.FamiliaServices;
import com.practica2.rest.controller.dao.services.GeneradorServices;
import com.practica2.rest.controller.tda.list.LinkedList;
import com.practica2.rest.models.Generador;
import com.practica2.rest.models.Familia;

@Path("family")
public class FamiliaApi {
    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllFamilys() {
        HashMap map = new HashMap<>();
        FamiliaServices ps = new FamiliaServices();
        map.put("msg", "Ok");
        map.put("data", ps.listAll().toArray());
        if (ps.listAll().isEmpty()) {
            map.put("data", new Object[] {});
        }
        return Response.ok(map).build();
    }

    @Path("/get/{id}") // actualizar
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFamily(@PathParam("id") Integer id) {
        HashMap map = new HashMap<>();
        FamiliaServices ps = new FamiliaServices();
        try {
            ps.setFamilia(ps.get(id));
        } catch (Exception e) {
            // TODO: handle exception
        }
        map.put("msg", "Ok");
        map.put("data", ps.getFamilia());
        if (ps.getFamilia().getId() == null) {
            map.put("data", "No existe la familia con ese identificador");
            return Response.status(Status.BAD_REQUEST).entity(map).build();
        }

        return Response.ok(map).build();
    }

    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(HashMap<String, Object> map) {
        HashMap<String, Object> res = new HashMap<>();
        Gson g = new Gson();
        try {
            // Validaciones básicas para los datos principales de la familia
            if (!map.containsKey("apellidos") || map.get("apellidos") == null) {
                res.put("msg", "Error");
                res.put("data", "El campo 'apellidos' es obligatorio");
                return Response.status(Status.BAD_REQUEST).entity(res).build();
            }

            // Procesar datos de la familia
            FamiliaServices ps = new FamiliaServices();
            ps.getFamilia().setApellidos(map.get("apellidos").toString());
            ps.getFamilia().setDireccion(map.get("direccion").toString());
            ps.getFamilia().setSector(map.get("sector").toString());
            ps.getFamilia().setCodigoHogar(map.get("codigoHogar").toString());

            // Verificar si la familia desea adquirir un generador
            boolean adquirirGenerador = map.containsKey("adquirirGenerador")
                    && Boolean.parseBoolean(map.get("adquirirGenerador").toString());
            ps.getFamilia().setAdquirirGenerador(adquirirGenerador);

            // Si desea adquirir generador, procesar los datos del generador
            if (adquirirGenerador) {
                if (!map.containsKey("generador") || map.get("generador") == null) {
                    res.put("msg", "Error");
                    res.put("data", "El objeto 'generador' es obligatorio cuando 'adquirirGenerador' es true");
                    return Response.status(Status.BAD_REQUEST).entity(res).build();
                }

                Map<String, Object> generadorMap = (Map<String, Object>) map.get("generador");
                if (!generadorMap.containsKey("coste") || generadorMap.get("coste") == null ||
                        !generadorMap.containsKey("potencia") || generadorMap.get("potencia") == null ||
                        !generadorMap.containsKey("consumoHora") || generadorMap.get("consumoHora") == null ||
                        !generadorMap.containsKey("tipoUso") || generadorMap.get("tipoUso") == null) {
                    res.put("msg", "Error");
                    res.put("data", "Faltan campos obligatorios en el objeto 'generador'");
                    return Response.status(Status.BAD_REQUEST).entity(res).build();
                }

                // Crear o actualizar el generador asociado a la familia
                Generador generador = ps.getFamilia().getGenerador();
                if (generador == null) {
                    generador = new Generador();
                    ps.getFamilia().setGenerador(generador);
                }

                // Asignar los datos del generador
                generador.setCoste(Float.parseFloat(generadorMap.get("coste").toString()));
                generador.setPotencia(Float.parseFloat(generadorMap.get("potencia").toString()));
                generador.setConsumoHora(Float.parseFloat(generadorMap.get("consumoHora").toString()));
                generador.setTipoUso(generadorMap.get("tipoUso").toString());
            } else {
                // Si no adquiere generador, asegurarse de que el generador esté a null
                ps.getFamilia().setGenerador(null);
            }

            
            ps.save();

            res.put("msg", "OK");
            res.put("data", ps.getFamilia()); // Aquí devolvemos toda la familia, que incluye el generador si lo tiene
            return Response.ok(res).build();

        } catch (Exception e) {
            System.out.println("Error en save: " + e.toString());
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @Path("/update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(HashMap map) {
        HashMap res = new HashMap<>();
        try {
            FamiliaServices ps = new FamiliaServices();
            ps.setFamilia(ps.get(Integer.parseInt(map.get("id").toString())));
            ps.getFamilia().setApellidos(map.get("apellidos").toString());
            ps.getFamilia().setDireccion(map.get("direccion").toString());
            ps.getFamilia().setSector(map.get("sector").toString());
            ps.getFamilia().setCodigoHogar(map.get("codigoHogar").toString());
            ps.getFamilia().setAdquirirGenerador(Boolean.parseBoolean(map.get("adquirirGenerador").toString()));
            // ps.getFamilia().setGenerador(map.get("generador").toString());
            Generador generador = ps.getFamilia().getGenerador();
            if (generador == null) {
                generador = new Generador(); 
                ps.getFamilia().setGenerador(generador); 
            }

            GeneradorServices gs = new GeneradorServices();
            gs.setGenerador(gs.get(Integer.parseInt(map.get("id").toString())));
            gs.getGenerador().setCoste(Float.parseFloat(map.get("coste").toString()));
            gs.getGenerador().setPotencia(Float.parseFloat(map.get("potencia").toString()));
            gs.getGenerador().setConsumoHora(Float.parseFloat(map.get("consumoHora").toString()));
            gs.getGenerador().setTipoUso(map.get("tipoUso").toString());
            // ps.getPersona().setIdentificacion(map.get("identificacion").toString());

            ps.update();
            res.put("msf", "OK");
            res.put("msg", "Familia registrada correctamente");
            return Response.ok(res).build();

        } catch (Exception e) {
            System.out.println("Error en sav data " + e.toString());
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////
    // PRACTICA# 3
    // API ORDER
    @Path("/orderFamily/{attribute}/{type}/{metodo}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response orderFamily(@PathParam("attribute") String attribute, @PathParam("type") Integer type, @PathParam("metodo") String metodo) {
        //HashMap<String, Object> map = new HashMap<>();
        HashMap map = new HashMap<>();
        FamiliaServices ps = new FamiliaServices();
        LinkedList<Familia> lista = new LinkedList<>();
        try {
            if ("quick".equals(metodo)) {
                lista = ps.pruebaOrden(attribute, type, "quick");
            } else if ("merge".equals(metodo)) {
                lista = ps.pruebaOrden(attribute, type, "merge");
            } else if ("shell".equals(metodo)) {
                lista = ps.pruebaOrden(attribute, type, "shell");
            } else {
                map.put("msg", "Error");
                map.put("data", "Método de ordenamiento inválido. Debe ser 'quick', 'merge' o 'shell'.");
                return Response.status(Status.BAD_REQUEST).entity(map).build();
            }
            map.put("msg", "Ok");
            map.put("data", lista.toArray());
            return Response.ok(map).build();
        } catch (Exception e) {
            e.printStackTrace(); // Imprimir el stack trace completo en los logs del servidor
            map.put("msg", "Error");
            map.put("data", "Error al ordenar la lista: " + e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // API SEARCH
    @Path("/searchFamily/{attribute}/{value}/{method}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchFamily(@PathParam("attribute") String attribute, @PathParam("value") String value, @PathParam("method") String method) {
        HashMap<String, Object> map = new HashMap<>();
        FamiliaServices ps = new FamiliaServices();
        Object resultado = null;
        try {
            switch (method.toLowerCase()) {
                case "binary":
                    resultado = ps.buscarFamiliaBinaria(attribute, value);
                    break;
                case "linealbinary":
                    resultado = ps.buscarFamiliaBinariaSecuencial(attribute, value);
                    break;
                default:
                    map.put("msg", "Error");
                    map.put("data", "Método de búsqueda inválido. Debe ser 'binary' o 'linealBinary'.");
                    return Response.status(Status.BAD_REQUEST).entity(map).build();
            }
            map.put("msg", "Ok");
            map.put("data", resultado);
            return Response.ok(map).build();
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", "Error");
            map.put("data", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }
    }
}