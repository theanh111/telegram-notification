package com.printway.client.amazongateway.reponse.orders;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetOrderResponse {
    private Order payload;
    private List<Error> errors;
}
