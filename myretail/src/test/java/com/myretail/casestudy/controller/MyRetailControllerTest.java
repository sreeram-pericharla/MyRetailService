package com.myretail.casestudy.controller;

import com.myretail.casestudy.model.Product;
import com.myretail.casestudy.model.ProductPrice;
import com.myretail.casestudy.model.ProductPriceUpdateResponse;
import com.myretail.casestudy.service.MyRetailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.context.request.async.DeferredResult;
import rx.Observable;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MyRetailControllerTest {

    @Mock
    private MyRetailService myRetailService;

    @InjectMocks
    private MyRetailController myRetailController;

    @Test
    void getProductDetails() {
        Long productId = 13860429L;
        Mockito.doReturn(Observable.just(getExampleProduct(productId))).when(myRetailService).getProductDetails(productId);
        Product actual = (Product) myRetailController.getProductDetails(productId).getResult();
        assertEquals(productId, actual.getId());
        assertEquals("title", actual.getName());
        assertEquals(new BigDecimal(25), actual.getProductPrice().getValue());
        assertEquals("USD", actual.getProductPrice().getCurrencyCode());
    }

    @Test
    void updateProductPrice() {
        Long productId = 13860429L;
        ProductPrice productPrice = getExampleProductPrice();
        Mockito.doReturn(Observable.just(getExamplePriceUpdateResponse(true)))
                .when(myRetailService).updateProductPrice(productId, productPrice);
        ProductPriceUpdateResponse actual = (ProductPriceUpdateResponse) myRetailController.updateProductPrice(productId, productPrice).getResult();
        assertTrue(actual.isPriceUpdated());
    }

    @Test
    void updateProductPriceFailure() {
        Long productId = 13860429L;
        ProductPrice productPrice = getExampleProductPrice();
        Mockito.doReturn(Observable.just(getExamplePriceUpdateResponse(false)))
                .when(myRetailService).updateProductPrice(productId, productPrice);
        ProductPriceUpdateResponse actual = (ProductPriceUpdateResponse) myRetailController.updateProductPrice(productId, productPrice).getResult();
        assertFalse(actual.isPriceUpdated());
    }

    private Product getExampleProduct(Long productId){
        return Product.builder().id(productId).name("title")
                .productPrice(ProductPrice.builder()
                        .value(new BigDecimal(25))
                        .currencyCode("USD").build()).build();
    }
    private ProductPriceUpdateResponse getExamplePriceUpdateResponse(boolean isUpdated){
        return ProductPriceUpdateResponse.builder().isPriceUpdated(isUpdated).build();
    }

    private ProductPrice getExampleProductPrice(){
        return ProductPrice.builder().value(new BigDecimal(20)).currencyCode("USD").build();
    }
}