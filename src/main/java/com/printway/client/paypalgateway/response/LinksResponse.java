package com.printway.client.paypalgateway.response;

import com.paypal.http.annotations.Model;
import com.paypal.http.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Model
public class LinksResponse {
    @SerializedName(
            value = "links",
            listClass = LinkDescription.class
    )
    private List<LinkDescription> links;
}
