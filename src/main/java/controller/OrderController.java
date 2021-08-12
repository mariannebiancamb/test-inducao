package controller;

// SDK do Mercado Pago
import com.mercadopago.MercadoPago;

import com.google.gson.Gson;
import com.mercadopago.exceptions.MPException;
import controller.request.OrderRequest;
import controller.request.PaymentRequest;
import entities.Messege;
import service.OrderService;


import static spark.Spark.*;

public class OrderController {

    private static final OrderService orderService = new OrderService();

    public static void main(String[] args) throws MPException {

        MercadoPago.SDK.setAccessToken(System.getenv("APP_HOME"));

        before("/*", (request, response) -> response.type("application/json"));

        get("/list", (request, response) -> {
            return "Products: " + orderService.findAll();
        });

        post("/order", (request, response) -> {
                Messege messege = orderService.addInCart(new Gson().fromJson(request.body(), OrderRequest.class));
                return new Gson().toJson(messege);
            }
        );

        get("/cart", (request, response) -> orderService.viewCart());

        //parte 1
        post("/preference", (request, response) ->
                new Gson().toJson(orderService.preferenceOrder())
        );

        post("/payment", ((request, response) ->
                new Gson().toJson(orderService.paymentOrder(new Gson().fromJson(request.body(), PaymentRequest.class)))
        ));

        exception(RuntimeException.class, ((exception, request, response) -> {
            response.status(500);
            response.body(new Gson().toJson(new Messege(exception.getMessage())));
        }));
    }


}
