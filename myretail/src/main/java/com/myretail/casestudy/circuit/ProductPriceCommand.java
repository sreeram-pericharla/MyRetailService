package com.myretail.casestudy.circuit;

import com.myretail.casestudy.configuration.SpringContext;
import com.myretail.casestudy.dao.ProductPriceDao;
import com.myretail.casestudy.model.ProductPrice;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class ProductPriceCommand extends HystrixCommand<ProductPrice> {

    private ProductPriceDao productPriceDao;
    private Long productId;

    public ProductPriceCommand(Long productId){
        super(HystrixCommandGroupKey.Factory.asKey("ProductPriceCommand"));
        this.productId = productId;
        this.productPriceDao = SpringContext.getBean(ProductPriceDao.class);
    }

    @Override
    protected ProductPrice run(){
        return productPriceDao.getProductPrice(productId);
    }

}

