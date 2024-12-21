package org.example.xmlgeneratorplay.types.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "creditSummary", "debitSummary" })
public class TransactionSummary {

    @XmlElement(name = "creditSummary", required = true)
    private Summary creditSummary;

    @XmlElement(name = "debitSummary", required = true)
    private Summary debitSummary;
}