package com.printway.client.paypalgateway.response;

import lombok.Data;

@Data
public class OrderCreateResponse {
    private String id;
    private String redirectLink;
    private String status;
}
