package service

import com.mercadopago.resources.Payment
import com.mercadopago.resources.Preference
import com.mercadopago.resources.datastructures.payment.Address
import com.mercadopago.resources.datastructures.payment.Identification
import com.mercadopago.resources.datastructures.payment.Payer
import com.mercadopago.resources.datastructures.preference.Item
import controller.request.OrderRequest
import controller.request.PaymentRequest
import entities.factory.impl.IdentificationImpl
import entities.factory.impl.ItemImpl
import entities.factory.impl.PayerImpl
import entities.factory.impl.PaymentImpl
import entities.factory.impl.PreferenceImpl
import org.mockito.Mockito
import entities.Category
import entities.Order
import entities.Product
import spock.lang.Shared
import spock.lang.Specification

class OrderServiceTest extends Specification {

    @Shared
    OrderService orderServiceTest
    Product product
    Product product1
    Product product2
    OrderRequest orderRequest

    def setup() {
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

    def "should view items in cart"() {
        given:
             Map<Product, Integer> cart = new HashMap<>();
             cart.put(product, 1);
             cart.put(product1, 1);
            orderServiceTest.addInCart(orderRequest)
        when:
            def result = orderServiceTest.viewCart();
        then:
            result != null
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
            PreferenceImpl preferenceImplMock = Mockito.mock(PreferenceImpl)
            ItemImpl itemImplMock = Mockito.mock(ItemImpl)

            OrderService orderServiceTest = new OrderService(preferenceImplMock, itemImplMock)

            Preference preferenceMock = Mockito.mock(Preference)
            Item itemMock = Mockito.mock(Item)

            Item item = new Item().setTitle(product.getName())
                    .setId(product.getId().toString())
                    .setQuantity(1)
                    .setCategoryId(product.getCategory().toString())
                    .setUnitPrice(product.getPrice());
            Preference preference = new Preference().appendItem(item)

            orderServiceTest.addInCart(new OrderRequest([new Order(1L, 1)]))

            Mockito.when(preferenceImplMock.newPreference()).thenReturn(preferenceMock)
            Mockito.when(itemImplMock.newItem()).thenReturn(item)
            Mockito.when(preferenceMock.appendItem(itemMock)).thenReturn(preferenceMock)
            Mockito.when(preferenceMock.save()).thenReturn(preference)

        when:
            def preferenceResponseResult = orderServiceTest.preferenceOrder()
        then:
            preferenceResponseResult != null
    }

    def "should not make a preference"() {
        when:
            orderServiceTest.preferenceOrder()
        then:
            thrown(RuntimeException)
    }

    def "should not make a payment"() {
        when:
            Address address = new Address().setZipCode("06233200")
                    .setStreetName("Av. das Nações Unidas").setStreetNumber(3003)
                    .setNeighborhood("Bonfim").setCity("Osasco").setFederalUnit("SP")

            PaymentRequest paymentRequest =  new PaymentRequest("test_user_68226018@testuser.com",
                    "Test", "User", "19119119100", "bolbradesco", address)

            orderServiceTest.paymentOrder(paymentRequest)
        then:
            thrown(RuntimeException)
    }

    def "shpuld make a payment"() { //FIX
        given:
            PaymentImpl paymentImplMock = Mockito.mock(PaymentImpl)
            PayerImpl payerImplMock = Mockito.mock(PayerImpl)
            IdentificationImpl identificationImplMock = Mockito.mock(IdentificationImpl)

            Payment paymentMock = Mockito.mock(Payment)
            Payer payerMock = Mockito.mock(Payer)
            Identification identificationMock = Mockito.mock(Identification)

            OrderService orderServiceTest = new OrderService(paymentImplMock, payerImplMock, identificationImplMock)

            orderServiceTest.addInCart(new OrderRequest([new Order(1L, 1)]))

            Address address = new Address().setZipCode("06233200")
                .setStreetName("Av. das Nações Unidas").setStreetNumber(3003)
                .setNeighborhood("Bonfim").setCity("Osasco").setFederalUnit("SP")
            Identification identification = new Identification().setNumber("19119119100").setType("CPF")
            Payer payer = new Payer().setEmail("test_user_68226018@testuser.com")
                    .setFirstName("Test")
                    .setLastName("User")
                    .setIdentification(identification)
                    .setAddress(address)
            Payment payment = new Payment().setTransactionAmount(2000.00)
                    .setDescription("Produtos da MyTech")
                    .setPaymentMethodId("bolbradesco")
                    .setPayer(payer)


            Mockito.when(paymentImplMock.newPayment()).thenReturn(paymentMock)
            Mockito.when(payerImplMock.newPayer()).thenReturn(payer)
            Mockito.when(identificationImplMock.newIdentification()).thenReturn(identificationMock)
            Mockito.when(paymentMock.save()).thenReturn(payment)

            PaymentRequest paymentRequest =  new PaymentRequest("test_user_68226018@testuser.com",
                    "Test", "User", "19119119100", "bolbradesco", address)
        when:
            def result = orderServiceTest.paymentOrder(paymentRequest)
        then:
            result != null
    }

}
