package com.myretail.casestudy.adapter;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RedSkyAdapterTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private RedSkyAdapter redSkyAdapter;

    @Value("${redsky.productdetails.endpoint.url}")
    private String endpointUrl;

    @Test
    void getProductDetailsTest() {
        String expected = "{\"product\":{\"item\":{\"product_description\":{\"title\":\"The Big Lebowski (Blu-ray)\",\"bullet_description\":[\"<B>Movie Studio:</B> Universal Studios\",\"<B>Movie Genre:</B> Comedy\",\"<B>Software Format:</B> Blu-ray\"]}}}}";
        Long productId = 13860429L;
        Mockito.doReturn(new ResponseEntity<>(expected, HttpStatus.OK)).when(restTemplate).getForEntity(Mockito.eq(endpointUrl), Mockito.eq(String.class), Mockito.anyMap());
        redSkyAdapter.setEndpointUrl(endpointUrl);
        String actual = redSkyAdapter.getProductDetails(productId);
        assertEquals(expected, actual);
    }

    @Test
    void getProductDetailsFailureTest() {
        Long productId = 13860429L;
        Mockito.doThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND)).when(restTemplate).getForEntity(Mockito.eq(endpointUrl), Mockito.eq(String.class), Mockito.anyMap());
        redSkyAdapter.setEndpointUrl(endpointUrl);
        assertThrows(HttpClientErrorException.class, () -> redSkyAdapter.getProductDetails(productId));
    }
}