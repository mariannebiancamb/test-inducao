package controller.request;

public class OrderRequest {
    private Long idProduct;
    private Integer quantity;

    public OrderRequest(Long idProduct, Integer quantity) {
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
