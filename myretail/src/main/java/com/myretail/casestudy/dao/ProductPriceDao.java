package com.myretail.casestudy.dao;

import com.myretail.casestudy.model.Product;
import com.myretail.casestudy.model.ProductPrice;
import com.myretail.casestudy.model.ProductPriceEntity;
import com.myretail.casestudy.repository.ProductPriceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ProductPriceDao {

    private static final Logger LOGGER = LoggerFactory.getLogger("ProductPriceDao");

    @Autowired
    private ProductPriceRepository productPriceRepository;

    /**
     * This method retrieve the price details of the product from the db.
     * @param productId
     * @return ProductPrice
     */
    public ProductPrice getProductPrice(Long productId){
        Optional<ProductPriceEntity> optionalProductPrice = productPriceRepository.findById(productId);
        if(optionalProductPrice.isPresent()){
            return ProductPrice.builder().value(optionalProductPrice.get().getValue())
                    .currencyCode(optionalProductPrice.get().getCurrency_Code()).build();
        }
        LOGGER.info("Price details not found for the product id: {}", productId);
        return null;
    }

    /**
     * This method is used to update the price details for the product id in the db.
     * it returns true if the update success or false if the update fails
     * @param productId
     * @param productPrice
     * @return boolean
     */
    public boolean updateProductPrice(Long productId, ProductPrice productPrice){
        return productPriceRepository.updateProductPrice(productId, productPrice.getValue(), productPrice.getCurrencyCode().toUpperCase());
    }
}
