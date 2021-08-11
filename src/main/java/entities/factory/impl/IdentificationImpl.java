package entities.factory.impl;

import com.mercadopago.resources.datastructures.payment.Identification;
import entities.factory.IdentificationFactory;

public class IdentificationImpl implements IdentificationFactory {
    @Override
    public Identification newIdentification() {
        return new Identification();
    }
}
