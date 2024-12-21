package org.example.xmlgeneratorplay.types.xml;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE;

    @Override
    public LocalDate unmarshal(String v) {
        return LocalDate.parse(v, DATE_FORMAT);
    }

    @Override
    public String marshal(LocalDate v) {
        return v.format(DATE_FORMAT);
    }
}
