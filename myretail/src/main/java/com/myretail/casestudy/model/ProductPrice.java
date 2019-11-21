package com.myretail.casestudy.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductPrice {

    @NotNull(message = "{field.notnull}")
    @Min(value = 0, message = "{field.value.invalid}")
    @JsonProperty("value")
    private BigDecimal value;

    @NotNull(message = "{field.notnull}")
    @Size(min = 3, max = 3, message = "{currencycode.invalid}")
    @JsonProperty("currency_code")
    private String currencyCode;

}
