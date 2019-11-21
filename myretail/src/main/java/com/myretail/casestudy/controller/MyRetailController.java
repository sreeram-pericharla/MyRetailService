package com.myretail.casestudy.controller;

import com.myretail.casestudy.model.Product;
import com.myretail.casestudy.model.ProductPrice;
import com.myretail.casestudy.model.ProductPriceUpdateResponse;
import com.myretail.casestudy.service.MyRetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/myretail")
@Validated
public class MyRetailController {

    private static final Logger LOGGER = LoggerFactory.getLogger("MyRetailController");

    @Autowired
    private MyRetailService retailService;

    /**
     * This is a HTTP GET method.
     * Responsibility of this method is to give product details by taking product id.
     * @param productId
     * @return DefferedResult<Product>
     */
    @GetMapping(value = "/products/{id}", produces = ("application/json; charset=utf-8"))
    public DeferredResult<Product> getProductDetails(@PathVariable("id") @NotNull(message = "{productid.notnull}")
                                                         @Min(value = 1, message = "{productid.invalid}") Long productId){
        if(LOGGER.isDebugEnabled()){
            LOGGER.debug("Entered into the get product details handler with product id: {}", productId);
        }
        DeferredResult<Product> deferredResult = new DeferredResult<>();
        retailService.getProductDetails(productId).subscribe(deferredResult::setResult, deferredResult::setErrorResult);
        return deferredResult;
    }

    /**
     * This is a HTTP PUT method
     * Responsibility of this method is to take product price of a product id and update in the db
     * @param productId
     * @param productPrice
     * @return DeferredResult<ProductPriceUpdateResponse>
     */
    @PutMapping(value = "/products/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public DeferredResult<ProductPriceUpdateResponse> updateProductPrice(@PathVariable("id") @NotNull(message = "{productid.notnull}")
                                                                             @Min(value = 1, message = "{productid.invalid}")Long productId,
                                                                         @RequestBody @Validated ProductPrice productPrice){
        if(LOGGER.isDebugEnabled()){
            LOGGER.debug("Entered into the update product price handler with product id: {}", productId);
        }
        DeferredResult<ProductPriceUpdateResponse> deferredResult = new DeferredResult<>();
        retailService.updateProductPrice(productId, productPrice).subscribe(deferredResult::setResult, deferredResult::setErrorResult);
        return deferredResult;
    }
}
