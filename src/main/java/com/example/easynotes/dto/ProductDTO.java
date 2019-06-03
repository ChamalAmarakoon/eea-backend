package com.example.easynotes.dto;

import com.example.easynotes.dto.OrderProductDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class ProductDTO {
    @Setter @Getter private long id;

    @Setter @Getter private String description;

    @Setter @Getter private String title;

    @Setter @Getter private int quantity;

    @Setter @Getter private double price;

    @Setter @Getter private String image;

    @Setter @Getter private String company;

    @Setter @Getter private List<OrderProductDTO> orderProductDTOList;
}
