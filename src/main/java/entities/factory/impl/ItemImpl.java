package entities.factory.impl;

import com.mercadopago.resources.datastructures.preference.Item;
import entities.factory.ItemFactory;

public class ItemImpl implements ItemFactory {
    @Override
    public Item newItem() {
        return new Item();
    }
}
