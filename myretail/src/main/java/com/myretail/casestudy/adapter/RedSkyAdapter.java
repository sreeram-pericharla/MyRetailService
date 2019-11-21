package com.myretail.casestudy.adapter;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Repository
public class RedSkyAdapter {

    @Setter
    @Value("${redsky.productdetails.endpoint.url}")
    private String endpointUrl;
    private RestTemplate restTemplate = new RestTemplate();

    /**
     * This method performs HTTP GET call to the external 3rd party API
     * and responsible to give the response body as string
     * @param productId
     * @return String
     */
    public String getProductDetails(Long productId){
        Map<String, Object> pathParams = new HashMap<>();
        pathParams.put("productId", productId.toString());
        return restTemplate.getForEntity(endpointUrl, String.class, pathParams).getBody();
    }

}
