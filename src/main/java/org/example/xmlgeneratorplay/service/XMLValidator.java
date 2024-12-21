package org.example.xmlgeneratorplay.service;

import lombok.extern.slf4j.Slf4j;
import org.example.xmlgeneratorplay.exception.SchemaFileNotFoundException;
import org.example.xmlgeneratorplay.exception.XMLValidationException;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

@Slf4j
@Component
public class XMLValidator {

    public static final String  PARSING_EXCEPTION_MESSAGE = "XML parsing error occurred";
    public static final String  IO_EXCEPTION_MESSAGE = "IO error during XML validation";
    public static final String  GENERAL_VALIDATION_EXCEPTION_MESSAGE = "General XML validation error";

    public void validateXml(String xmlString, InputStream schemaStream)
            throws XMLValidationException, SchemaFileNotFoundException, IOException {
        if (schemaStream == null) {
            throw new SchemaFileNotFoundException("Schema file not found in classpath.");
        }

        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new StreamSource(schemaStream));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new StringReader(xmlString)));
        } catch (SAXException e) {
            throw new XMLValidationException(PARSING_EXCEPTION_MESSAGE);
        } catch (IOException e) {
            throw new IOException(IO_EXCEPTION_MESSAGE, e);
        } catch (Exception e) {
            throw new XMLValidationException(GENERAL_VALIDATION_EXCEPTION_MESSAGE, e);
        }
    }
}