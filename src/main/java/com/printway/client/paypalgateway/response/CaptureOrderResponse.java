package com.printway.client.paypalgateway.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaptureOrderResponse {
    private String orderId;
    private String status;
}
