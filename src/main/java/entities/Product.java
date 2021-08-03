package entities;

public class Product {
    private Long id;
    private String name;
    private Double price;

    public Product(Long id, String name, Double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Float getPrice() {
        return Float.parseFloat(String.valueOf(price));
    }

    //Builder Pattern
    public static class Builder {
        Long id;
        String name;
        Double price;

        public Builder withID(Long id) {
            this.id = id;
            return this;
        }
        public Builder withName(String name) {
            this.name = name;
            return this;
        }
        public Builder withPrice(Double price) {
            this.price = price;
            return this;
        }
        public Product build(){
            return new Product(id, name, price);
        }
    }

    @Override
    public String toString() {
        return "{id=" + id + ", name='" + name + ", price=" + price + "}";
    }
}
