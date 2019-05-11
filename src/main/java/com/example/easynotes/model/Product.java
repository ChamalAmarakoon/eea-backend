package com.example.easynotes.model;

import net.bytebuddy.dynamic.loading.InjectionClassLoader;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "Products")
@EntityListeners(AuditingEntityListener.class)


public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String description;

    private String title;

    private int quantity;

    private double price;

    private String image;

    private String company;


    public Product() {

    }

    public String getTitle() { return title;}

    public void setTitle(String title) { this.title = title;}

    public String getImage() {return image;}

    public void setImage(String image) { this.image = image;}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCompany(){ return company;
    }

    public void setCompany(String company){
        this.company = company;
    }


}
