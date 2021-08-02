package controller;

// SDK do Mercado Pago
import com.mercadopago.MercadoPago;
import com.mercadopago.exceptions.MPException;
import service.OrderService;
import sun.text.resources.cldr.ti.FormatData_ti_ER;

import static spark.Spark.*;

public class OrderController {


    public static void main(String[] args) throws MPException {
        MercadoPago.SDK.setAccessToken("PROD_ACCESS_TOKEN");

        get("/hello", (request, response) -> {
            return OrderService.hello();
        });

        //CRUD
        //lista os produtos
        //adiciona rprodutos no carrinho
        //ver carrinho
        //pagamento com MP
    }
}
