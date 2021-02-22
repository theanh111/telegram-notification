package com.printway.client.paypalgateway.response;

import com.paypal.http.annotations.Model;
import com.paypal.http.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Model
public class LinkDescription {
    @SerializedName("href")
    private String href;
    @SerializedName("rel")
    private String rel;
    @SerializedName("method")
    private String method;
    @SerializedName("description")
    private String description;
}
