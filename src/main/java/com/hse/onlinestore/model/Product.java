package com.hse.onlinestore.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Products")
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "productName", nullable = false, length = 500)
    private String name;

    @Column(name = "description", nullable = false, length = 5000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    private BigDecimal priceInEuro;

    @Column(name = "currency", nullable = false)
    private String currency;

    @Column(name = "numberOfDaysForFreeReturn", nullable = false)
    private int numberOfDaysForFreeReturn;

    @Column(name = "freeShipping", nullable = false)
    private boolean freeShipping;

    private BigDecimal shippingPrice;

    private int deliveryRangeFrom;

    private int deliveryRangeTo;
}
