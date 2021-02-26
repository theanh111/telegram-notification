package com.printway.client.amazongateway.reponse.document;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Header", propOrder = {
        "documentVersion",
        "merchantIdentifier"
})
public class Header {


    @XmlElement(name = "DocumentVersion")
    private String documentVersion = "1.01";
    @XmlElement(name = "MerchantIdentifier")
    private String merchantIdentifier = "A2JJ76W78VWD5R";

    public String getDocumentVersion() {
        return documentVersion;
    }

    public void setDocumentVersion(String documentVersion) {
        this.documentVersion = documentVersion;
    }

    public String getMerchantIdentifier() {
        return merchantIdentifier;
    }

    public void setMerchantIdentifier(String merchantIdentifier) {
        this.merchantIdentifier = merchantIdentifier;
    }
}
