package com.printway.client.amazongateway.reponse.document;

import lombok.Data;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "header",
        "messageType",
        "messageDocument",
})
@XmlRootElement(name = "AmazonEnvelope")
@Data
public class DocumentReportFeedResponse {
    @XmlElement(name = "Header")
    private Header header;
    @XmlElement(name = "MessageType")
    private String messageType = "ProductImage";
    @XmlElement(name = "Message")
    private MessageDocument messageDocument;
}
