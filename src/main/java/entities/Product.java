package entities;

import java.util.Objects;

public class Product {
    private Long id;
    private String name;
    private Double price;
    private Category category;

    public Product(Long id, String name, Double price, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
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

    public Category getCategory() {
        return category;
    }

    //Builder Pattern
    public static class Builder {
        Long id;
        String name;
        Double price;
        Category category;

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
        public Builder withCategory(Category category) {
            this.category = category;
            return this;
        }
        public Product build(){
            return new Product(id, name, price, category);
        }
    }

    @Override
    public String toString() {
        return "{id=" + id + ", name='" + name + ", price=" + price + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
