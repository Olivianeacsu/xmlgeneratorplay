package org.example.xmlgeneratorplay.service;

import org.example.xmlgeneratorplay.exception.SchemaFileNotFoundException;
import org.example.xmlgeneratorplay.exception.XMLValidationException;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class XMLValidatorTest {
    private static final String VALID_XML = "<root></root>";
    private static final String INVALID_XML = "<root>";

    private final XMLValidator xmlValidator = new XMLValidator();

    @Test
    public void testValidateXmlWithValidInput() throws Exception {
        InputStream schemaStream = new ByteArrayInputStream((
                "<xs:schema xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">" +
                        "  <xs:element name=\"root\" type=\"xs:string\"/>" +
                        "</xs:schema>").getBytes()
        );

        // Should not throw any exceptions
        xmlValidator.validateXml(VALID_XML, schemaStream);
    }

    @Test
    public void testValidateXmlWithNullSchemaStream() {
        assertThrows(SchemaFileNotFoundException.class, () ->
                xmlValidator.validateXml(VALID_XML, null), "Schema file not found in classpath.");
    }

    @Test
    public void testValidateXmlWithInvalidSchema() throws Exception {
        InputStream schemaStream = new ByteArrayInputStream("<xs:schema xmlns:xs=\"http://www.w3.org/2001/XMLSchema\"></xs:schema>".getBytes());

        assertThatThrownBy(() -> xmlValidator.validateXml(INVALID_XML, schemaStream))
                .isInstanceOf(XMLValidationException.class)
                .hasMessageContaining(XMLValidator.PARSING_EXCEPTION_MESSAGE);
    }
}
