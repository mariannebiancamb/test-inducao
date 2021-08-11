package entities.factory.impl;

import com.mercadopago.resources.Payment;
import entities.factory.PaymentFactory;

public class PaymentImpl implements PaymentFactory {

    @Override
    public Payment newPayment() {
        return new Payment();
    }
}
