package com.PSL.PSL.grossiste;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/rapports")
public class ReportController {

    @Autowired
    private RapportService rapportService;

    @GetMapping("/exporter")
    public String exportReport(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        // Générer le rapport
        Report report = rapportService.generateReport(startDate, endDate);

        // Exporter le rapport en CSV
        return rapportService.exportToCSV(report);
    }
}
