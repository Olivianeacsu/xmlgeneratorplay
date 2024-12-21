package org.example.xmlgeneratorplay.types.xml;

import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "document")
@XmlType(propOrder = {"header", "transactions"})
@XmlAccessorType(XmlAccessType.FIELD)
public class Document {
    @XmlElement(name = "header", required = true)
    private Header header;
    @XmlElement(name = "transactions", required = true)
    private List<TransactionDetail> transactions;


}
