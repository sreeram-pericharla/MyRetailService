package com.myretail.casestudy.circuit;

import com.myretail.casestudy.configuration.SpringContext;
import com.myretail.casestudy.dao.ProductPriceDao;
import com.myretail.casestudy.model.ProductPrice;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class ProductPriceUpdateCommand extends HystrixCommand<Boolean> {

    private ProductPriceDao productPriceDao;
    private Long productId;
    private ProductPrice productPrice;

    public ProductPriceUpdateCommand(Long productId, ProductPrice productPrice){
        super(HystrixCommandGroupKey.Factory.asKey("ProductPriceUpdateCommand"));
        this.productId = productId;
        this.productPrice = productPrice;
        this.productPriceDao = SpringContext.getBean(ProductPriceDao.class);
    }

    @Override
    protected Boolean run(){
        return productPriceDao.updateProductPrice(productId, productPrice);
    }

}
