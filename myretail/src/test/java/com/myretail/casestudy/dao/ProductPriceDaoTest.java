package com.myretail.casestudy.dao;

import com.myretail.casestudy.model.ProductPrice;
import com.myretail.casestudy.model.ProductPriceEntity;
import com.myretail.casestudy.repository.ProductPriceRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class ProductPriceDaoTest {

    @Mock
    private ProductPriceRepository productPriceRepository;

    @InjectMocks
    private ProductPriceDao productPriceDao;

    @Test
    public void getProductPriceTest() {
        Long productId = 13860429L;
        Mockito.doReturn(Optional.of(getExampleProductPriceEntity(productId))).when(productPriceRepository).findById(productId);
        ProductPrice actual = productPriceDao.getProductPrice(productId);
        assertEquals(new BigDecimal(30), actual.getValue());
        assertEquals("USD", actual.getCurrencyCode());
    }

    @Test
    public void getProductPriceNoRecordTest() {
        Long productId = 13860429L;
        Mockito.doReturn(Optional.empty()).when(productPriceRepository).findById(productId);
        ProductPrice actual = productPriceDao.getProductPrice(productId);
        assertNull(actual);
    }

    @Test
    public void updateProductPriceTest() {
        Long productId = 13860429L;
        ProductPrice productPrice = getExampleProductPrice();
        Mockito.doReturn(true).when(productPriceRepository).updateProductPrice(productId, productPrice.getValue(), productPrice.getCurrencyCode());
        boolean actual = productPriceDao.updateProductPrice(productId, productPrice);
        assertTrue(actual);
    }

    @Test
    public void updateProductPriceFailedTest() {
        Long productId = 13860429L;
        ProductPrice productPrice = getExampleProductPrice();
        Mockito.doReturn(false).when(productPriceRepository).updateProductPrice(productId, productPrice.getValue(), productPrice.getCurrencyCode());
        boolean actual = productPriceDao.updateProductPrice(productId, productPrice);
        assertFalse(actual);
    }

    private ProductPriceEntity getExampleProductPriceEntity(Long productId){
        ProductPriceEntity productPriceEntity = new ProductPriceEntity();
        productPriceEntity.setProduct_Id(productId);
        productPriceEntity.setValue(new BigDecimal(30));
        productPriceEntity.setCurrency_Code("USD");
        return productPriceEntity;
    }

    private ProductPrice getExampleProductPrice(){
        return ProductPrice.builder().value(new BigDecimal(50))
                .currencyCode("USD").build();
    }

}