package com.printway.client.amazongateway.reponse;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Error {
    @SerializedName("code")
    private String code = null;

    @SerializedName("message")
    private String message = null;

    @SerializedName("details")
    private String details = null;
}
