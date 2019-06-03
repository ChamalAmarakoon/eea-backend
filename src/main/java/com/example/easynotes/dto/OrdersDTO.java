package com.example.easynotes.dto;

import com.example.easynotes.dto.OrderProductDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class OrdersDTO {
    @Setter
    @Getter
    private long id;

    @Setter @Getter private String orderStatus;

    @Setter @Getter private long userId;

    @Getter @Setter
    List<OrderProductDTO> orderProductDTO;
}
