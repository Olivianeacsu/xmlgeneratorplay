package org.example.xmlgeneratorplay.service;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.xmlgeneratorplay.exception.MarshallingException;
import org.example.xmlgeneratorplay.exception.SchemaFileNotFoundException;
import org.example.xmlgeneratorplay.exception.XMLValidationException;
import org.example.xmlgeneratorplay.types.Currency;
import org.example.xmlgeneratorplay.types.TransactionDTO;
import org.example.xmlgeneratorplay.types.TransactionType;
import org.example.xmlgeneratorplay.types.xml.*;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Component
@AllArgsConstructor
public class XMLGenerator {
    private XMLValidator xmlValidator;
    private static final String SCHEMA_FILE = "/output.xsd";
    private static JAXBContext JAXB_CONTEXT;
    private static Marshaller MARSHALLER;

    static {
        try {
            JAXB_CONTEXT = JAXBContext.newInstance(Document.class);
            MARSHALLER = JAXB_CONTEXT.createMarshaller();
        } catch (JAXBException e) {
            log.error("Failed to initialize JAXBContext or Marshaller");
        }

    }

    public String generateXml(final LocalDate date, final List<TransactionDTO> transactionsDTOs) {
        try {
            final List<TransactionDetail> transactions = transactionsDTOs.stream()
                    .map(this::convertToTransactionDetail)
                    .collect(Collectors.toList());

            final Summary creditSummary = calculateSummary(transactions, TransactionType.CREDIT);
            final Summary debitSummary = calculateSummary(transactions, TransactionType.DEBIT);

            final String fileId = "transactions_" + date.toString() + "_" + UUID.randomUUID() + ".xml";
            final TransactionSummary transactionSummary = new TransactionSummary(creditSummary, debitSummary);
            final Header header = new Header(date, transactions.size(), fileId, transactionSummary);

            final Document document = new Document(header, transactions);

            MARSHALLER.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            final StringWriter stringWriter = new StringWriter();
            MARSHALLER.marshal(document, stringWriter);

            // Validate the XML string against the schema
            try (final InputStream schemaStream = getClass().getResourceAsStream(SCHEMA_FILE)) {
                if (schemaStream == null) {
                    throw new SchemaFileNotFoundException("Schema file not found in classpath.");
                }

                xmlValidator.validateXml(stringWriter.toString(), schemaStream);
            } catch (IOException | XMLValidationException e) {
                throw new XMLValidationException(e.getMessage(), e);
            }

            // Write the XML content to the file

            File file = new File(fileId);
            Files.writeString(file.toPath(), stringWriter.toString(), StandardOpenOption.CREATE);
            return file.getAbsolutePath();

        } catch (XMLValidationException | MarshallingException | SchemaFileNotFoundException e) {
            throw e;
        } catch (JAXBException e) {
            throw new MarshallingException("Error marshalling XML", e);
        } catch (Exception e) {
            log.error("Unexpected error occurred while generating XML", e);
            throw new RuntimeException("Unexpected error occurred while generating XML", e);
        }
    }

    private TransactionDetail convertToTransactionDetail(final TransactionDTO transactionDto) {
        AmountAndCurrency debit = new AmountAndCurrency(BigDecimal.ZERO, transactionDto.getCurrency());
        AmountAndCurrency credit = new AmountAndCurrency(BigDecimal.ZERO, transactionDto.getCurrency());
        if (transactionDto.getType() == TransactionType.DEBIT) {
            debit.setAmount(transactionDto.getAmount());
        } else if (transactionDto.getType() == TransactionType.CREDIT) {
            credit.setAmount(transactionDto.getAmount());
        }
        return new TransactionDetail(transactionDto.getAccountNumber(), debit, credit);
    }

    private Summary calculateSummary(final List<TransactionDetail> transactions, final TransactionType type) {
        int numberOfTransactions = 0;
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (TransactionDetail transaction : transactions) {
            if (type == TransactionType.DEBIT && transaction.getDebit() != null && transaction.getDebit().getAmount().compareTo(BigDecimal.ZERO) != 0) {
                numberOfTransactions++;
                totalAmount = totalAmount.add(transaction.getDebit().getAmount());
            } else if (type == TransactionType.CREDIT
                    && transaction.getCredit() != null
                    && transaction.getCredit().getAmount().compareTo(BigDecimal.ZERO) != 0) {
                numberOfTransactions++;
                totalAmount = totalAmount.add(transaction.getCredit().getAmount());
            }
        }

        // Default values in case there are no transactions of the specified type
        if (numberOfTransactions == 0) {
            totalAmount = BigDecimal.ZERO;
        }

        // as the output.xsd does not seem to separate between Summaries for different currencies
        // default to SEK
        AmountAndCurrency amountAndCurrency = new AmountAndCurrency(totalAmount, Currency.SEK);
        return new Summary(numberOfTransactions, amountAndCurrency);
    }
}
