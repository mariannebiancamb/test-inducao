package service;

import com.mercadopago.MercadoPago;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Preference;
import com.mercadopago.resources.datastructures.preference.Item;
import controller.request.OrderRequest;
import entities.Product;

import java.util.ArrayList;
import java.util.List;

public class OrderService {

    public List<Product> findAll() {
        List<Product> list = new ArrayList<>();

        Product product = new Product.Builder().withID(1L).withName("PS4").withPrice(2000.00).build();
        Product product1 = new Product.Builder().withID(2L).withName("TV").withPrice(600.00).build();
        Product product2 = new Product.Builder().withID(3L).withName("Notebook").withPrice(3000.00).build();

        list.add(product);
        list.add(product1);
        list.add(product2);

        return list;
    }

    private Product findById(Long id){
        List<Product> list = findAll();
        return list.stream().filter(x -> x.getId().equals(id)).findFirst().orElseThrow(() -> new RuntimeException("ID invalido."));
    }

    public Preference preferenceOrder(OrderRequest orderRequest) throws MPException {
        Product product = findById(orderRequest.getIdProduct());
        // Cria um objeto de preferência
        Preference preference = new Preference();

        // Cria um item na preferência
        Item item = new Item();
        item.setTitle(product.getName())
                .setQuantity(orderRequest.getQuantity())
                .setUnitPrice(product.getPrice());
        preference.appendItem(item);
        return preference.save();

        //E AGORA?
    }


}
