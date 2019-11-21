package com.myretail.casestudy.circuit;

import com.myretail.casestudy.adapter.RedSkyAdapter;
import com.myretail.casestudy.configuration.SpringContext;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class RedSkyCommand extends HystrixCommand<String> {

    private RedSkyAdapter redSkyAdapter;
    private Long productId;

    public RedSkyCommand(Long productId){
        super(HystrixCommandGroupKey.Factory.asKey("RedSkyCommand"));
        this.productId = productId;
        this.redSkyAdapter = SpringContext.getBean(RedSkyAdapter.class);
    }

    @Override
    protected String run(){
        return redSkyAdapter.getProductDetails(productId);
    }

}
