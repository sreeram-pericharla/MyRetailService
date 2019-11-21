package com.myretail.casestudy.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigDecimal;

@Getter
@Setter
@Table(value = "product_prices")
public class ProductPriceEntity {

    @PrimaryKey
    @Column(value = "product_id")
    private Long product_Id;

    @Column()
    private BigDecimal value;

    @Column(value = "currency_code")
    private String currency_Code;
}
