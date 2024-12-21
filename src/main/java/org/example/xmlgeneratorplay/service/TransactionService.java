package org.example.xmlgeneratorplay.service;

import lombok.AllArgsConstructor;
import org.example.xmlgeneratorplay.types.TransactionDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TransactionService {

    private XMLGenerator xmlGenerator;

    public List<String> processTransactionsCreateXmlFiles(List<TransactionDTO> transactions){

        List<String> filePaths = new ArrayList<>();

        // Group transactions by date
        Map<LocalDate, List<TransactionDTO>> transactionsByDate = transactions.stream()
                .collect(Collectors.groupingBy(TransactionDTO::getDate));

        // Generate XML files for each date group
        for (Map.Entry<LocalDate, List<TransactionDTO>> entry : transactionsByDate.entrySet()) {
            LocalDate date = entry.getKey();
            List<TransactionDTO> transactionsForDate = entry.getValue();

            String xmlPerDateFilePath = xmlGenerator.generateXml(date, transactionsForDate);
            filePaths.add(xmlPerDateFilePath);
        }

        return filePaths;
    }
}
