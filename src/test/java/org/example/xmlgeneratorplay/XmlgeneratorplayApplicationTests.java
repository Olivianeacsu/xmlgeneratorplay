package org.example.xmlgeneratorplay;

import org.example.xmlgeneratorplay.utils.TestBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@ActiveProfiles("test")
@DisplayName("Integration Tests")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class XmlgeneratorplayApplicationTests extends TestBase {

    private static final String POST_TRANSACTIONS_URL = "/transactions";


    @Test
    @DisplayName("Process one transaction - one xml file")
    void processOneTransaction() throws IOException {
        // Given

        // When
        ResponseEntity<List<String>> response = runRequestFromFile("one_transaction_json_input",
                POST_TRANSACTIONS_URL, HttpMethod.POST);

        // Then
        // verify that one xml file is created
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(1);
    }

    @Test
    @DisplayName("Process four transactions in two different dates and two different accounts - two xml files")
    void processFourTransactionsTwoDifferentAccounts() throws IOException {
        // Given

        // When
        ResponseEntity<List<String>> response = runRequestFromFile("four_transactions_json_input",
                POST_TRANSACTIONS_URL, HttpMethod.POST);

        // Then
        // verify that two xml files are created
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(2);
    }

    @Test
    @DisplayName("Process empty transactions input")
    void processEmptyTransactionsInput() {
        // Given

        // When
        ResponseEntity<String> response = runRequestEmptyInput(POST_TRANSACTIONS_URL, HttpMethod.POST);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

}
