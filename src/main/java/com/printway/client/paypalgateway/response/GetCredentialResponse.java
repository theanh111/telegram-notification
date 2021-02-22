package com.printway.client.paypalgateway.response;

import com.paypal.http.annotations.Model;
import com.paypal.http.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Model
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetCredentialResponse {
    @SerializedName("client_id")
    private String client_id;
    @SerializedName("client_secret")
    private String client_secret;

}