package entities;

import java.util.Map;

public class Cart {
    private final Map<Product, Integer> itemsCart;
    private Double totalPrice;

    public Cart(Map<Product, Integer> itemsCart, Double totalPrice) {
        this.itemsCart = itemsCart;
        this.totalPrice = totalPrice;
    }

    public Double getTotalPrice() {
        for (Map.Entry<Product, Integer> entry : itemsCart.entrySet()) {
            totalPrice = totalPrice + entry.getKey().getPrice();
        }
        return totalPrice;
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
