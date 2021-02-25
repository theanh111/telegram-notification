package com.printway.client.amazongateway.reponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetFeedResponse {
    private Payload payload;
    private List<Error> errors;
}
