package com.printway.client.amazongateway.reponse.document;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "messagesProcessed",
        "messagesSuccessful",
        "messagesWithError",
        "messagesWithWarning"
})
@Data
public class ProcessingSummary {
    @XmlElement(name = "MessagesProcessed")
    private String messagesProcessed;
    @XmlElement(name = "MessagesSuccessful")
    private String messagesSuccessful;
    @XmlElement(name = "MessagesWithError")
    private String messagesWithError;
    @XmlElement(name = "MessagesWithWarning")
    private String messagesWithWarning;


}
