package service;

import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Preference;
import com.mercadopago.resources.datastructures.preference.Item;
import controller.request.OrderRequest;
import controller.response.PreferenceResponse;
import entities.Category;
import entities.Order;
import entities.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderService {

    private final Map<Product, Integer> cart = new HashMap<>();

    public List<Product> findAll() {
        List<Product> list = new ArrayList<>();

        Product product = new Product.Builder().withID(1L).withName("PS4").withPrice(2000.00).withCategory(Category.GAMER).build();
        Product product1 = new Product.Builder().withID(2L).withName("TV").withPrice(600.00).withCategory(Category.TECH).build();
        Product product2 = new Product.Builder().withID(3L).withName("Notebook").withPrice(3000.00).withCategory(Category.HOMEOFFICE).build();

        list.add(product);
        list.add(product1);
        list.add(product2);

        return list;
    }

    private Product findById(Long id){
        List<Product> list = findAll();
        return list.stream().filter(x -> x.getId().equals(id)).findFirst().orElseThrow(() -> new RuntimeException("ID not found."));
    }

    public PreferenceResponse preferenceOrder() throws MPException {
        // if(cart.isEmpty()) { throw new RuntimeException("Cart is empty"); }

        Preference preference = new Preference();

        for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
            Item item = new Item();
            item.setTitle(entry.getKey().getName())
                    .setId(entry.getKey().getId().toString())
                    .setQuantity(entry.getValue())
                    .setCategoryId(entry.getKey().getCategory().toString())
                    .setUnitPrice(entry.getKey().getPrice());
            preference.appendItem(item);
        }
        preference.save();

        return new PreferenceResponse.Builder()
                .withItems(preference.getItems())
                .withId(preference.getId())
                .withInitPoint(preference.getInitPoint())
                .withPayer(preference.getPayer())
                .withSandBoxInitPoint(preference.getSandboxInitPoint())
                .build();
    }

    public String addInCart(OrderRequest orderRequest) {
        for (Order or : orderRequest.getOrderList()) {
            // if(or.getQuantity() < 0) throw new RuntimeException("Quantity should be positive number.");
            Integer quant = 0;
            Product product = findById(or.getIdProduct());
            if(cart.containsKey(product)) {
                quant = cart.get(product);
                cart.put(product, or.getQuantity() + quant);
            } else cart.put(product, or.getQuantity());

        }
        return "Products add in the cart.";
    }

    public Map<Product, Integer> viewCart() {
        return cart;
    }

}
