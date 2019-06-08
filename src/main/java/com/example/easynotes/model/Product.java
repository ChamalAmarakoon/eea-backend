package com.example.easynotes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "Product")
@EntityListeners(AuditingEntityListener.class)


public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    @Getter
    private long id;

    @Setter @Getter private String description;

    @Setter @Getter private String title;

    @Setter @Getter private int quantity;

    @Setter @Getter private double price;

    @Setter @Getter private byte[] image;

    @Setter @Getter private String company;

    @JsonIgnore
    @OneToMany(mappedBy = "product",fetch = FetchType.EAGER)
    @Getter @Setter
    List<OrderProducts> orderProducts;
}
