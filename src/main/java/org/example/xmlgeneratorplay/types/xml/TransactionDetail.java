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
@XmlType(propOrder ={ "accountNumber", "debit", "credit" })
public class TransactionDetail {

    @XmlElement(name = "accountNumber", required = true)
    private String accountNumber;

    @XmlElement(name = "debit", required = true)
    private AmountAndCurrency debit;

    @XmlElement(name = "credit", required = true)
    private AmountAndCurrency credit;
}