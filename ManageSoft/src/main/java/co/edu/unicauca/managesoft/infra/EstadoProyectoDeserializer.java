/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.managesoft.infra;

import co.edu.unicauca.managesoft.entities.EstadoAceptado;
import co.edu.unicauca.managesoft.entities.EstadoRecibido;
import co.edu.unicauca.managesoft.entities.*;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;

/**
 *
 * @author jcben
 */
public class EstadoProyectoDeserializer implements JsonDeserializer<IEstadoProyecto> {

    @Override
    public IEstadoProyecto deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String estado = json.getAsString();

        switch (estado) {
            case "RECIBIDO":
                return new EstadoRecibido();
            case "ACEPTADO":
                return new EstadoAceptado();
            case "RECHAZADO":
                return new EstadoRechazado();
            case "EN_EJECUCION":
                return new EstadoEnEjecucion();
            case "CERRADO":
                return new EstadoCerrado();
            default:
                throw new JsonParseException("Estado desconocido: " + estado);
        }
    }
}
