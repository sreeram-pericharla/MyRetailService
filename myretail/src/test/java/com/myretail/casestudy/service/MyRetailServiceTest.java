package com.myretail.casestudy.service;

import com.myretail.casestudy.adapter.RedSkyAdapter;
import com.myretail.casestudy.configuration.SpringContext;
import com.myretail.casestudy.dao.ProductPriceDao;
import com.myretail.casestudy.model.Product;
import com.myretail.casestudy.model.ProductPrice;
import com.myretail.casestudy.model.ProductPriceUpdateResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MyRetailServiceTest {

    @MockBean
    private ProductPriceDao productPriceDao;
    @MockBean
    private RedSkyAdapter redSkyAdapter;
    @InjectMocks
    private MyRetailService myRetailService;

    @Test
    void getProductDetails() {
        Long productId = 13860429L;
        String productDetails = "{\"product\":{\"item\":{\"product_description\":{\"title\":\"The Big Lebowski (Blu-ray)\",\"bullet_description\":[\"<B>Movie Studio:</B> Universal Studios\",\"<B>Movie Genre:</B> Comedy\",\"<B>Software Format:</B> Blu-ray\"]}}}}";
        Mockito.doReturn(productDetails).when(redSkyAdapter).getProductDetails(productId);
        Mockito.doReturn(getExampleProductPrice()).when(productPriceDao).getProductPrice(productId);
        Product actual = myRetailService.getProductDetails(productId).toBlocking().first();
        assertEquals(productId, actual.getId());
        assertEquals("The Big Lebowski (Blu-ray)", actual.getName());
        assertEquals(new BigDecimal(50), actual.getProductPrice().getValue());
        assertEquals("USD", actual.getProductPrice().getCurrencyCode());
    }

    @Test
    void getProductPriceFailure() {
        Long productId = 13860429L;
        String productDetails = "{\"product\":{\"item\":{\"product_description\":{\"title\":\"The Big Lebowski (Blu-ray)\",\"bullet_description\":[\"<B>Movie Studio:</B> Universal Studios\",\"<B>Movie Genre:</B> Comedy\",\"<B>Software Format:</B> Blu-ray\"]}}}}";
        Mockito.doReturn(productDetails).when(redSkyAdapter).getProductDetails(productId);
        Mockito.doReturn(null).when(productPriceDao).getProductPrice(productId);
        Product actual = myRetailService.getProductDetails(productId).toBlocking().first();
        assertEquals(productId, actual.getId());
        assertEquals("The Big Lebowski (Blu-ray)", actual.getName());
        assertNull(actual.getProductPrice());
    }

    @Test
    void updateProductPrice() {
        Long productId = 13860429L;
        ProductPrice productPrice = getExampleProductPrice();
        Mockito.doReturn(true).when(productPriceDao).updateProductPrice(productId, productPrice);
        ProductPriceUpdateResponse actual = myRetailService.updateProductPrice(productId, productPrice).toBlocking().first();
        assertTrue(actual.isPriceUpdated());
    }

    @Test
    void updateProductPriceFailure() {
        Long productId = 13860429L;
        ProductPrice productPrice = getExampleProductPrice();
        Mockito.doReturn(false).when(productPriceDao).updateProductPrice(productId, productPrice);
        ProductPriceUpdateResponse actual = myRetailService.updateProductPrice(productId, productPrice).toBlocking().first();
        assertFalse(actual.isPriceUpdated());
    }

    private ProductPrice getExampleProductPrice(){
        return ProductPrice.builder().value(new BigDecimal(50))
                .currencyCode("USD").build();
    }
}