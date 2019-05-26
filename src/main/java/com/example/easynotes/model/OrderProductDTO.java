package com.example.easynotes.model;

import lombok.Getter;
import lombok.Setter;

public class OrderProductDTO {
    @Setter @Getter private long id;

    @Setter @Getter private int quantity;

    @Getter @Setter OrdersDTO ordersDTO;

    @Getter @Setter ProductDTO productDTO;

}
