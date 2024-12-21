package org.example.xmlgeneratorplay.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.example.xmlgeneratorplay.exception.BadRequestException;
import org.example.xmlgeneratorplay.service.TransactionService;
import org.example.xmlgeneratorplay.types.TransactionDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class TransactionController {

    private TransactionService transactionService;

    @Operation(summary = "Processes transactions and generates XML files",
            description = "Processes transactions and generates XML files that conform to a predefined xsd schema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "XML generated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request. Transaction list cannot be empty."),
            @ApiResponse(responseCode = "500", description = "Internal Server Error.")
    })
    @PostMapping(value = "/transactions",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> createXMLTransactionFiles(@RequestBody List<TransactionDTO> transactions) {
        if(CollectionUtils.isEmpty(transactions)) {
            throw new BadRequestException("No transactions provided. No XML files were generated.");
        }
        List<String> xmlFilePaths = transactionService.processTransactionsCreateXmlFiles(transactions);
        return ResponseEntity.ok(xmlFilePaths);
    }
}