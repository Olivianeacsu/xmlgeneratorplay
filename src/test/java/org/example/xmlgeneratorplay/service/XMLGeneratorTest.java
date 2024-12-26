package org.example.xmlgeneratorplay.service;

import jakarta.xml.bind.JAXBContext;
import org.example.xmlgeneratorplay.exception.XMLValidationException;
import org.example.xmlgeneratorplay.types.Currency;
import org.example.xmlgeneratorplay.types.TransactionDTO;
import org.example.xmlgeneratorplay.types.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.example.xmlgeneratorplay.service.XMLValidator.IO_EXCEPTION_MESSAGE;
import static org.example.xmlgeneratorplay.service.XMLValidator.PARSING_EXCEPTION_MESSAGE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class XMLGeneratorTest {
    @Mock
    private XMLValidator xmlValidator;

    @InjectMocks
    private XMLGenerator xmlGenerator;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private static final LocalDate DATE = LocalDate.now();
    private static final TransactionDTO TRANSACTION_DTO_1 = new TransactionDTO(BigDecimal.valueOf(100L), "111222", TransactionType.CREDIT,
            LocalDate.now(), Currency.SEK);
    private static final TransactionDTO TRANSACTION_DTO_2 = new TransactionDTO(BigDecimal.valueOf(200L), "333444", TransactionType.DEBIT,
            LocalDate.now(), Currency.SEK);



    @Test
    @DisplayName("Test generation of one XML file for input 2 transactions same date")
    public void testGenerateXmlWithValidInput() throws Exception {
        // Given
        final List<TransactionDTO> transactionsDTOs = List.of(TRANSACTION_DTO_1, TRANSACTION_DTO_2);

        doNothing().when(xmlValidator).validateXml(anyString(), any(InputStream.class));

        // When
        final String result = xmlGenerator.generateXml(DATE, transactionsDTOs);

        // Then
        final File file = new File(result);
        assertThat(file).exists();
        assertThat(file.getName()).contains("transactions_" + DATE);
    }

    @Test
    @DisplayName("Test when validation fails because of XML parsing issue")
    public void testGenerateXmlWithXMLParseException() throws Exception {
        // Given
        final List<TransactionDTO> transactionsDTO = List.of(TRANSACTION_DTO_1);

        // When
        doThrow(new XMLValidationException(PARSING_EXCEPTION_MESSAGE)).when(xmlValidator).validateXml(anyString(), any(InputStream.class));

        // Then
        assertThatThrownBy(() -> xmlGenerator.generateXml(DATE, transactionsDTO))
                .isInstanceOf(XMLValidationException.class)
                .hasMessageContaining(PARSING_EXCEPTION_MESSAGE);
    }

    @Test
    @DisplayName("Test when validation fails because of IO Exception issue")
    public void testGenerateXmlWithIOException() throws Exception {
        // Given
        final List<TransactionDTO> transactionsDTO = List.of(TRANSACTION_DTO_1);

        // When
        doThrow(new XMLValidationException(IO_EXCEPTION_MESSAGE)).when(xmlValidator).validateXml(anyString(), any(InputStream.class));

        // Then
        assertThatThrownBy(() -> xmlGenerator.generateXml(DATE, transactionsDTO))
                .isInstanceOf(XMLValidationException.class)
                .hasMessageContaining(IO_EXCEPTION_MESSAGE);
    }
}
