package com.myretail.casestudy.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ProductPriceUpdateResponse {

    @JsonProperty("isPriceUpdated")
    private boolean isPriceUpdated;
}
