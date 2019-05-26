package com.example.easynotes.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class OrderProducts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    @Getter
    private long id;

    @Setter @Getter private int quantity;

    @ManyToOne
    @JoinColumn(name="order_id",referencedColumnName = "id")
    @Getter @Setter Orders orders;

    @ManyToOne
    @JoinColumn(name="product_id",referencedColumnName = "id")
    @Getter @Setter Product product;

}
