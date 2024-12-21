package org.example.xmlgeneratorplay.types.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "date", "numberOfTransactions", "fileId", "transactionSummary" })
public class Header {
    @XmlElement(name = "date", required = true)
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate date;

    @XmlElement(name = "numberOfTransactions", required = true)
    private int numberOfTransactions;

    @XmlElement(name = "fileId", required = true)
    private String fileId;

    @XmlElement(name = "transactionSummary", required = true)
    private TransactionSummary transactionSummary;
}
