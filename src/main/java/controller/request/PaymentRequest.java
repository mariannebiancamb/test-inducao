package controller.request;

import com.mercadopago.resources.datastructures.payment.Address;

public class PaymentRequest {

    private String email;
    private String firstName;
    private String lastName;
    private String cpf;
    private String paymentMethodId;
    private Address address;

    public PaymentRequest(String email, String firstName, String lastName, String cpf, String paymentMethodId, Address address) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cpf = cpf;
        this.paymentMethodId = paymentMethodId;
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCpf() {
        return cpf;
    }

    public String getPaymentMethodId() {
        return paymentMethodId;
    }

    public Address getAddress() {
        return address;
    }

}
