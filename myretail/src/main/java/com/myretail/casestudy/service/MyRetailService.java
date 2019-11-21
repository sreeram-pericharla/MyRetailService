package com.myretail.casestudy.service;

import com.myretail.casestudy.circuit.ProductPriceCommand;
import com.myretail.casestudy.circuit.ProductPriceUpdateCommand;
import com.myretail.casestudy.circuit.RedSkyCommand;
import com.myretail.casestudy.model.Product;
import com.myretail.casestudy.model.ProductPrice;
import com.myretail.casestudy.model.ProductPriceUpdateResponse;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import rx.Observable;

@Service
public class MyRetailService {

    private static final Logger LOGGER = LoggerFactory.getLogger("MyRetailService");
    private static final String PRODUCT = "product";
    private static final String ITEM = "item";
    private static final String PRODUCT_DESCRIPTION = "product_description";
    private static final String TITLE = "title";

    /**
     * The method gives the product details for the product id.
     * This method calls both the third part API and db to get the product details.
     * @param productId
     * @return Observable<Product>
     */
    public Observable<Product> getProductDetails(Long productId){
        return Observable.zip(new RedSkyCommand(productId).observe(),
                new ProductPriceCommand(productId).observe(),
                (productDetails, priceDetails)-> Product.builder().id(productId).name(getTitle(productDetails)).productPrice(priceDetails).build());
    }

    /**
     * The method updates the product price for the product id.
     * @param productId
     * @param productPrice
     * @return Observable<ProductPriceUpdateResponse>
     */
    public Observable<ProductPriceUpdateResponse> updateProductPrice(Long productId, ProductPrice productPrice){
        return new ProductPriceUpdateCommand(productId, productPrice).observe()
                .map(priceUpdateStatus -> ProductPriceUpdateResponse.builder().isPriceUpdated(priceUpdateStatus).build());
    }

    /**
     * This method gets the value of the title from the string
     * logs if any exception occurs
     * @param productDetails
     * @return String
     */
    private String getTitle(String productDetails){
        try{
            return new JSONObject(productDetails)
                    .getJSONObject(PRODUCT)
                    .getJSONObject(ITEM)
                    .getJSONObject(PRODUCT_DESCRIPTION)
                    .getString(TITLE);
        } catch (Exception ex){
            LOGGER.error("Error occurred while getting the title from json string: {} \nwith exception {}", productDetails, ex);
        }
        return null;
    }
}
