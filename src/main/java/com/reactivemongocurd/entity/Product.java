package com.reactivemongocurd.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="products")
public class Product {

    @Id
    private String id;

    private String name;

    private int qty;

    private double price;
}
