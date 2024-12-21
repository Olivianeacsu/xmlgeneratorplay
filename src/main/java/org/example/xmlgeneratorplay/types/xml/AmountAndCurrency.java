package org.example.xmlgeneratorplay.types.xml;

import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.xmlgeneratorplay.types.Currency;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "amount", "currency" })
public class AmountAndCurrency {

    @XmlValue
    private BigDecimal amount;

    @XmlAttribute(name = "currency", required = true)
    private Currency currency;
}
