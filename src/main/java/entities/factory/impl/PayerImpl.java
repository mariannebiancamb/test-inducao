package entities.factory.impl;

import com.mercadopago.resources.datastructures.payment.Payer;
import entities.factory.PayerFactory;

public class PayerImpl implements PayerFactory {

    @Override
    public Payer newPayer() {
        return new Payer();
    }
}
