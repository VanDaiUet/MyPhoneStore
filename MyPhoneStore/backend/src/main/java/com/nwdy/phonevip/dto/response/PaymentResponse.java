package com.nwdy.phonevip.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PaymentResponse {

    @JsonProperty("return_url")
    private String returnUrl;
}
