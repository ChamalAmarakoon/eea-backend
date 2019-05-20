package com.example.easynotes.model;

public class OrderProductDTO {
    public OrderProducts orderProd;

    public Product product;

    public OrderProducts getOrderProd() {
        return orderProd;
    }

    public Product getProduct() {
        return product;
    }

    public void setOrderProd(OrderProducts orderProd) {
        this.orderProd = orderProd;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
