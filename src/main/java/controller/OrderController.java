package controller;

// SDK do Mercado Pago
import com.mercadopago.MercadoPago;

import com.google.gson.Gson;
import com.mercadopago.exceptions.MPException;
import controller.request.OrderRequest;
import service.OrderService;
import static spark.Spark.*;

public class OrderController {

    private static final OrderService orderService = new OrderService();

    public static void main(String[] args) throws MPException {

        MercadoPago.SDK.setAccessToken("TEST-8051729513123545-080214-44d1ded32c0c88b7dbd7a800c5078f6c-800777909");

        get("/", (request, response) -> "Bem-vindo(a) a MyTech.");

        //Lista todos os produtos para o consumidor
        get("/list", (request, response) -> {
            return "Products: " + orderService.findAll();
        });

        //recebe a order e gera a preferencia
        post("/order", (request, response) -> {
            response.type("application/json"); //after
            OrderRequest orderRequest = new Gson().fromJson(request.body(), OrderRequest.class);
            return new Gson().toJson(orderService.preferenceOrder(orderRequest));
        });


    }
}
