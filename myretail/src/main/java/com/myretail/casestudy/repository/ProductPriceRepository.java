package com.myretail.casestudy.repository;

import com.myretail.casestudy.model.ProductPriceEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import java.math.BigDecimal;

public interface ProductPriceRepository extends CassandraRepository<ProductPriceEntity, Long> {

    /**
     * To update the product price details in the db
     * @param productId
     * @param value
     * @param currencyCode
     * @return boolean
     */
    @Query("UPDATE product_prices SET value=?1, currency_code=?2 WHERE product_id=?0 IF EXISTS")
    public boolean updateProductPrice(Long productId, BigDecimal value, String currencyCode);
}
