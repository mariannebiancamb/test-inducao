package entities;

import java.util.Map;

public class Cart {
    private Map<Product, Integer> itemsCart;

    public Cart(Map<Product, Integer> itemsCart) {
        this.itemsCart = itemsCart;
    }

    public Map<Product, Integer> getItemsCart() {
        return itemsCart;
    }

    public void addItemsCart(Product product, Integer quantity) {
        itemsCart.put(product, quantity);
    }

    @Override
    public String toString() {
        return "Cart{" + itemsCart + '}';
    }
}
