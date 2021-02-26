package com.printway.client.amazongateway.reponse.document;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "messageId",
        "processingReport",
})
@Data
public class MessageDocument {
    @XmlElement(name = "MessageID")
    private String messageId;
    @XmlElement(name = "ProcessingReport")
    private ProcessingReport processingReport;

}
