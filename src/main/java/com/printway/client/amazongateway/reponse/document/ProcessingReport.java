package com.printway.client.amazongateway.reponse.document;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "documentTransactionID",
        "statusCode",
        "processingSummary",
        "result",
})
@Data
public class ProcessingReport {
    @XmlElement(name = "DocumentTransactionID")
    private String documentTransactionID;
    @XmlElement(name = "StatusCode")
    private String statusCode;
    @XmlElement(name = "ProcessingSummary")
    private ProcessingSummary processingSummary;
    @XmlElement(name = "Result")
    private Result result;
}
