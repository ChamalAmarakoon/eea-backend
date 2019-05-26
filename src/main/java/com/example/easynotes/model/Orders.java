package com.example.easynotes.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    @Getter
    private long id;

    @Setter @Getter private String orderStatus;

    @Setter @Getter private long userId;

    @OneToMany(mappedBy = "orders",fetch = FetchType.EAGER)
    @Getter @Setter
    List<OrderProducts> orderProducts;
}
