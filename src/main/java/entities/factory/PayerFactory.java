package entities.factory;

import com.mercadopago.resources.datastructures.payment.Payer;

public interface PayerFactory {
    Payer newPayer();
}
