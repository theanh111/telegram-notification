package com.printway.client.amazongateway.reponse.document;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "messageID",
        "resultCode",
        "resultMessageCode",
        "resultDescription",
        "additionalInfo",
})
@Data
public class Result {
    @XmlElement(name = "MessageID")
    private String messageID;
    @XmlElement(name = "ResultCode")
    private String resultCode;
    @XmlElement(name = "ResultMessageCode")
    private String resultMessageCode;
    @XmlElement(name = "ResultDescription")
    private String resultDescription;
    @XmlElement(name = "AdditionalInfo")
    private AdditionalInfo additionalInfo;
}
