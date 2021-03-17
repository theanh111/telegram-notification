package com.printway.client.paypalgateway.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateResponse {
    private String id;
    private String redirectLink;
    private String status;
}
