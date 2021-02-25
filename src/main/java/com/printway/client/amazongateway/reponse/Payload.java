package com.printway.client.amazongateway.reponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payload {
    private String feedId;
    private String feeType;
    private List<String> marketplaceIds;
    private String processingStatus;
    private String processingStartTime;
    private String processingEndTime;
    private String resultFeedDocumentId;
}
