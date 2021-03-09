package com.printway.client.amazongateway.reponse.orders;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String name;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String city;
    private String county;
    private String district;
    private String stateOrRegion;
    private String municipality;
    private String postalCode;
    private String countryCode;
    private String phone;
    private String addressType;
}
