package ar.edu.utn.dds.k3003.app;

import ar.edu.utn.dds.k3003.clients.HeladerasProxy;

import ar.edu.utn.dds.k3003.controller.ViandaController;
import ar.edu.utn.dds.k3003.facades.dtos.Constants;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.javalin.Javalin;
import io.javalin.json.JavalinJackson;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;



public class WebApp {

    public static void main(String[] args) {
        var env = System.getenv();

        var objectMapper = createObjectMapper();
        var fachada = new Fachada();

        fachada.setHeladerasProxy(new HeladerasProxy(objectMapper));

        Integer port = Integer.parseInt(System.getProperty("port", "8080"));

        var app = Javalin.create().start(port);

        var viandaController = new ViandaController(fachada);

        //agregar nueva vianda
        app.post("/viandas/", viandaController::agregar);
        //Obtener vianda de un colaborador,mes y año
        app.get("/viandas/search/findByColaboradorIdAndAnioAndMes",viandaController::buscarPorColaboradorIdAnioMes);

        // buscar vianda QR
        app.get("/viandas/{qr}",viandaController::buscarPorQr);
        // evaluar vencimiento vianda por qr
        app.get("/viandas/{qr}/vencida",viandaController::estaVencida);
        // Modificar heladera de una vianda por QR
        app.patch("/viandas/{qrVianda}",viandaController::modificarHeladeraVianda);
    }


    public static ObjectMapper createObjectMapper() {
        var objectMapper = new ObjectMapper();
        configureObjectMapper(objectMapper);
        return objectMapper;
    }


    public static void configureObjectMapper(ObjectMapper objectMapper) {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        var sdf = new SimpleDateFormat(Constants.DEFAULT_SERIALIZATION_FORMAT, Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        objectMapper.setDateFormat(sdf);
    }

}