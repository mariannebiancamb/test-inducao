package service

import com.mercadopago.MercadoPago
import com.mercadopago.resources.Preference
import com.mercadopago.resources.datastructures.preference.Item
import controller.request.OrderRequest
import controller.response.PreferenceResponse
import org.mockito.Mockito
import entities.Category
import entities.Order
import entities.Product
import spock.lang.Specification

class OrderServiceTest extends Specification {

    OrderService orderServiceTest
    Product product
    Product product1
    Product product2
    OrderRequest orderRequest

    def setup() {
        MercadoPago.SDK.setAccessToken("TEST-8051729513123545-080214-44d1ded32c0c88b7dbd7a800c5078f6c-800777909");
        orderServiceTest = new OrderService()
        product = new Product.Builder().withID(1L).withName("PS4").withPrice(2000.00).withCategory(Category.GAMER).build()
        product1 = new Product.Builder().withID(2L).withName("TV").withPrice(600.00).withCategory(Category.TECH).build()
        product2 = new Product.Builder().withID(3L).withName("Notebook").withPrice(3000.00).withCategory(Category.HOMEOFFICE).build()
        orderRequest = new OrderRequest([new Order(1L, 1), new Order(2L, 1)])
    }

    def "should show items"() {
        given:
            List<Product> listExpect = [product, product1, product2]
        when:
            List<Product> listResult = orderServiceTest.findAll();
        then:
            listExpect == listResult
    }

    def "should view tens in cart"() {
        given:
             Map<Product, Integer> cart = new HashMap<>();
             cart.put(product, 1);
             cart.put(product1, 1);
            orderServiceTest.addInCart(orderRequest)
        when:
            def result = orderServiceTest.viewCart();
        then:
            result == cart
    }

    def "should add item in the cart"() {
        when:
            String result = orderServiceTest.addInCart(orderRequest);
        then:
            result == "Products add in the cart."
    }

    def "should not add item in the cart"() {
        when:
            orderServiceTest.addInCart(new OrderRequest(null))
        then:
            thrown(RuntimeException)
    }

    def "should make a preference"() {
        given:
            Preference preference = new Preference()
            Preference preferenceMock = Mockito.mock(Preference)
            Item item = new Item()
            Item itemMock = Mockito.mock(Item)

            item.setTitle(product.getName())
                .setId(product.getId().toString())
                .setQuantity(1)
                .setCategoryId(product.getCategory().toString())
                .setUnitPrice(product.getPrice());
            preference.appendItem(item);

            Mockito.when(itemMock.setTitle(any() as String)).thenReturn(item)
            Mockito.when(preferenceMock.appendItem(item)).thenReturn(preference)
            Mockito.when(preferenceMock.save()).thenReturn(preference)

            orderServiceTest.addInCart(new OrderRequest([new Order(1L, 1)]));
        when:
            PreferenceResponse preferenceResponseResult = orderServiceTest.preferenceOrder()
        then:
            preferenceResponseResult != null
    }

    def "should not make a preference"() {
        when:
            orderServiceTest.preferenceOrder()
        then:
            thrown(RuntimeException)
    }

}
