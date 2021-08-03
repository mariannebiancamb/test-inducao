package entities;


public class Order {
    private final Long idProduct;
    private final Integer quantity;

    public Order(Long idProduct, Integer quantity) {
        this.idProduct = idProduct;
        this.quantity = quantity;
    }

    public Long getIdProduct() {
        return idProduct;
    }

    public Integer getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "OrderRequest{" + "idProduct=" + idProduct + ", quantity=" + quantity + '}';
    }
}
