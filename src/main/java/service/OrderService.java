package service;

import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Payment;
import com.mercadopago.resources.Preference;
import com.mercadopago.resources.datastructures.payment.Identification;
import com.mercadopago.resources.datastructures.payment.Payer;
import com.mercadopago.resources.datastructures.preference.Item;
import controller.request.OrderRequest;
import controller.request.PaymentRequest;
import controller.response.PreferenceResponse;
import entities.*;
import entities.factory.*;
import entities.factory.impl.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderService {

    private final Map<Product, Integer> cart = new HashMap<>();
    private PreferenceFactory preferenceImpl = new PreferenceImpl();
    private ItemFactory itemImpl = new ItemImpl();
    private PaymentFactory paymentImpl = new PaymentImpl();
    private PayerFactory payerImpl = new PayerImpl();
    private IdentificationFactory identificationImpl = new IdentificationImpl();

    public OrderService() { }

    public OrderService(PaymentFactory paymentImpl, PayerFactory payerImpl, IdentificationFactory identificationImpl) {
        this.paymentImpl = paymentImpl;
        this.payerImpl = payerImpl;
        this.identificationImpl = identificationImpl;
        cart.put(new Product.Builder().withID(1L).withName("PS4").withPrice(2000.00).withCategory(Category.GAMER).build(), 1);
    }

    public OrderService(PreferenceFactory preferenceImpl, ItemFactory itemImpl) {
        this.preferenceImpl = preferenceImpl;
        this.itemImpl = itemImpl;
    }

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
        if(cart.isEmpty()) { throw new RuntimeException("Cart is empty"); }

        Preference preference = preferenceImpl.newPreference();

        for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
            Item item = itemImpl.newItem().setTitle(entry.getKey().getName())
                    .setId(entry.getKey().getId().toString())
                    .setQuantity(entry.getValue())
                    .setCategoryId(entry.getKey().getCategory().toString())
                    .setUnitPrice(entry.getKey().getPrice());
            preference.appendItem(item);
        }
        preference = preference.save();

        return convertPreferenceToPreferenceResponse(preference);
    }

    private PreferenceResponse convertPreferenceToPreferenceResponse(Preference preference) {
        return new PreferenceResponse.Builder()
                .withItems(preference.getItems())
                .withId(preference.getId())
                .withInitPoint(preference.getInitPoint())
                .withPayer(preference.getPayer())
                .withSandBoxInitPoint(preference.getSandboxInitPoint())
                .build();
    }

    public Payment paymentOrder(PaymentRequest request) throws MPException {
        if(cart.isEmpty()) { throw new RuntimeException("Cart is empty"); }

        Identification identification = identificationImpl.newIdentification();
        Payer payer = payerImpl.newPayer();
        Payment payment = paymentImpl.newPayment();

        payment.setTransactionAmount(totalPrice())
                .setDescription("Produtos da MyTech")
                .setPaymentMethodId(request.getPaymentMethodId())
                .setPayer(payer.setEmail(request.getEmail())
                        .setFirstName(request.getFirstName())
                        .setLastName(request.getLastName())
                        .setIdentification(identification
                                .setType("CPF")
                                .setNumber(request.getCpf()))
                        .setAddress(request.getAddress())
                );

        payment = payment.save();

        return payment;
    }

    public Messege addInCart(OrderRequest orderRequest) {
        for (Order or : orderRequest.getOrderList()) {
            if(or.getQuantity() <= 0) throw new RuntimeException("Quantity should be positive number.");
            Integer quant = 0;
            Product product = findById(or.getIdProduct());
            if(cart.containsKey(product)) {
                quant = cart.get(product);
                cart.put(product, or.getQuantity() + quant);
            } else cart.put(product, or.getQuantity());

        }
        return new Messege("Products add in the cart.");
    }

    private Float totalPrice() {
        Float totalPrice = 0f;
        for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
            totalPrice += entry.getKey().getPrice();
        }
        return totalPrice;
    }

    public String viewCart() {
        return "PRODUCTS: " + cart.toString() + "\n TOTAL PRICE: R$" + totalPrice();
    }

}
