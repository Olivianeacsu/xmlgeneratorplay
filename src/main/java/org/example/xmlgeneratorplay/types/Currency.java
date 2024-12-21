package org.example.xmlgeneratorplay.types;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(name = "currency")
@XmlEnum(String.class)
public enum Currency {
    @XmlEnumValue("USD")
    USD("USD"),

    @XmlEnumValue("EUR")
    EUR("EUR"),

    @XmlEnumValue("GBP")
    GBP("GBP"),

    @XmlEnumValue("SEK")
    SEK("SEK"),

    @XmlEnumValue("UNKNOWN")
    UNKNOWN("UNKNOWN");

    private final String value;

    Currency(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public static Currency fromValue(String v) {
        for (Currency c : Currency.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        return UNKNOWN; // Default to UNKNOWN for invalid currency values
    }
}