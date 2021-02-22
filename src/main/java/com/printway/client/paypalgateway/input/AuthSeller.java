package com.printway.client.paypalgateway.input;

import lombok.Data;

@Data
public class AuthSeller {
    private String authCode;
    private String sharedId;
}
