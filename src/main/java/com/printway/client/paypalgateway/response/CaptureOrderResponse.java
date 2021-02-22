package com.printway.client.paypalgateway.response;

import lombok.Data;

@Data
public class CaptureOrderResponse {
    private String orderId;
    private String status;

    public CaptureOrderResponse() {
    }

    public CaptureOrderResponse(String orderId, String status) {
        this.orderId = orderId;
        this.status = status;
    }
}
