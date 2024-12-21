package org.example.xmlgeneratorplay.types.xml;

import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.xmlgeneratorplay.types.TransactionType;


@Getter
@Setter
@NoArgsConstructor // Lombok annotation to generate a no-argument constructor
@AllArgsConstructor
@XmlRootElement(name = "transaction")
@XmlType(propOrder = {"amount", "accountNumber", "type", "date", "currency"})
@XmlAccessorType(XmlAccessType.FIELD)
public class Transaction {
    @XmlElement(name = "type", required = true)
    private TransactionType type;

    @XmlElement(name = "detail", required = true)
    private TransactionDetail detail;

}
