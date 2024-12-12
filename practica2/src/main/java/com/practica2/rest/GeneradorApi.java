package com.practica2.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import com.practica2.rest.models.Familia;
import com.practica2.rest.models.Generador;
import com.practica2.rest.controller.tda.list.LinkedList;
import com.practica2.rest.controller.dao.GeneradorDao;

@Path("generator")
public class GeneradorApi {

    // Obtener todos los generadores
    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllGenerators() {
        HashMap<String, Object> map = new HashMap<>();
        GeneradorServices ps = new GeneradorServices();
        try {
            map.put("msg", "Ok");
            map.put("data", ps.listAll().toArray());
            if (ps.listAll().isEmpty()) {
                map.put("data", new Object[] {});
            }
        } catch (Exception e) {
            map.put("msg", "Error");
            map.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }
        return Response.ok(map).build();
    }

    // Obtener un generador por ID
    @Path("/get/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFamily(@PathParam("id") Integer id) {
        HashMap<String, Object> map = new HashMap<>();
        GeneradorServices ps = new GeneradorServices();
        try {
            Generador generador = ps.get(id);
            if (generador == null || generador.getId() == null) {
                map.put("msg", "Error");
                map.put("data", "No existe el generador con ese identificador");
                return Response.status(Status.BAD_REQUEST).entity(map).build();
            }
            map.put("msg", "Ok");
            map.put("data", generador);
        } catch (Exception e) {
            map.put("msg", "Error");
            map.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }
        return Response.ok(map).build();
    }

    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(HashMap<String, Object> map) {
        HashMap<String, Object> res = new HashMap<>();
        try {
            // Validación simple: Verificar que se proporcionen los datos requeridos
            if (!map.containsKey("coste") || !map.containsKey("potencia") ||
                    !map.containsKey("consumoHora") || !map.containsKey("tipoUso")) {
                res.put("msg", "Error");
                res.put("data", "Faltan datos obligatorios.");
                return Response.status(Status.BAD_REQUEST).entity(res).build();
            }

            // Crear un nuevo objeto Generador con los datos recibidos
            Generador generador = new Generador();
            generador.setCoste(Float.parseFloat(map.get("coste").toString()));
            generador.setPotencia(Float.parseFloat(map.get("potencia").toString()));
            generador.setConsumoHora(Float.parseFloat(map.get("consumoHora").toString()));
            generador.setTipoUso(map.get("tipoUso").toString());

            // Llamar al servicio para guardar el generador
            GeneradorServices ps = new GeneradorServices();
            ps.save(generador); // Guardar el generador

            res.put("msg", "OK");
            res.put("data", "Generador registrado correctamente");
            return Response.ok(res).build();

        } catch (Exception e) {
            e.printStackTrace();
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @Path("/update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(HashMap<String, Object> map) {
        HashMap<String, Object> res = new HashMap<>();
        try {
            // Validación: Verificar que se proporcionen los datos requeridos
            if (!map.containsKey("id") || !map.containsKey("coste") ||
                    !map.containsKey("potencia") || !map.containsKey("consumoHora") || !map.containsKey("tipoUso")) {
                res.put("msg", "Error");
                res.put("data", "Faltan datos obligatorios.");
                return Response.status(Status.BAD_REQUEST).entity(res).build();
            }

            // Obtener el ID del generador y los datos para actualizar
            Integer id = Integer.parseInt(map.get("id").toString());
            GeneradorServices ps = new GeneradorServices();
            Generador generador = ps.get(id); // Obtener el generador por ID

            if (generador == null) {
                res.put("msg", "Error");
                res.put("data", "Generador no encontrado.");
                return Response.status(Status.BAD_REQUEST).entity(res).build();
            }

            // Actualizar los campos del generador con los datos recibidos
            generador.setCoste(Float.parseFloat(map.get("coste").toString()));
            generador.setPotencia(Float.parseFloat(map.get("potencia").toString()));
            generador.setConsumoHora(Float.parseFloat(map.get("consumoHora").toString()));
            generador.setTipoUso(map.get("tipoUso").toString());

            // Llamar al servicio para actualizar el generador
            ps.update(generador); // Actualizar el generador

            res.put("msg", "OK");
            res.put("data", "Generador actualizado correctamente");
            return Response.ok(res).build();

        } catch (Exception e) {
            e.printStackTrace();
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @Path("/family/{familyId}/generators")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addGeneratorsToFamily(@PathParam("familyId") Integer familyId, List<Generador> generadoresList) {
        HashMap<String, Object> res = new HashMap<>();
        GeneradorDao generadorDao = new GeneradorDao();
        FamiliaServices familiaServices = new FamiliaServices();

        try {
            // Verificar si la familia existe
            Familia familia = familiaServices.get(familyId);
            if (familia == null) {
                res.put("msg", "Error");
                res.put("data", "No existe una familia con ese ID.");
                return Response.status(Status.BAD_REQUEST).entity(res).build();
            }

            // Agregar los generadores a la familia
            for (Generador generador : generadoresList) {
                generador.setFamilyId(familyId); // Asociamos el generador con la familia
                generadorDao.addGenerador(generador); // Agregamos el generador al DAO
            }

            // Guardar los cambios (si es necesario, o al menos podemos confirmar la
            // operación)
            generadorDao.save();

            res.put("msg", "OK");
            res.put("data", "Generadores agregados correctamente a la familia.");
            return Response.ok(res).build();

        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

}