// 4. CONTROLLER
package com.example.Gii.controller;

import com.example.Gii.entity.Report;
import com.example.Gii.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ReportController {
    private final ReportService reportService;

    // Get all reports
    @GetMapping("/AllReports")
    public ResponseEntity<List<Report>> getAllReports() {
        return ResponseEntity.ok(reportService.getAllReports());
    }

    // Add a new report
    @PostMapping("/addReport")
    public ResponseEntity<Report> addReport(@RequestBody Report report) {
        Report newReport = reportService.addReport(report);
        return ResponseEntity.ok(newReport);
    }

    // Update an existing report
    @PutMapping("/{id}")
    public ResponseEntity<Report> updateReport(@PathVariable String id, @RequestBody Report reportDetails) {
        System.out.println("report id " + id);
        System.out.println("data to change " + reportDetails);
        Report updatedReport = reportService.updateReport(id, reportDetails);
        return ResponseEntity.ok(updatedReport);
    }

    // Update report status
    @PatchMapping("/{id}/status")
    public ResponseEntity<Report> updateReportStatus(@PathVariable String id, @RequestBody String status) {
        Report updatedReport = reportService.updateReportStatus(id, status);
        return ResponseEntity.ok(updatedReport);
    }

    // Get a report by ID
    @GetMapping("/{id}")
    public ResponseEntity<Report> getReportById(@PathVariable String id) {
        Report report = reportService.getReportById(id);
        return ResponseEntity.ok(report);
    }

    // Delete a report by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable String id) {
        reportService.deleteReport(id);
        return ResponseEntity.noContent().build();
    }

    // Count total number of reports
    @GetMapping("/count")
    public ResponseEntity<Long> countReports() {
        return ResponseEntity.ok(reportService.countReports());
    }

    // Get reports by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Report>> getReportsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(reportService.findByStatus(status));
    }

    // Get reports by author
    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<Report>> getReportsByAuthor(@PathVariable String authorId) {
        return ResponseEntity.ok(reportService.findByAuthor(authorId));
    }

    // Get reports by title search
    @GetMapping("/search")
    public ResponseEntity<List<Report>> searchReportsByTitle(@RequestParam String title) {
        return ResponseEntity.ok(reportService.findByTitle(title));
    }

    // Get reports by date range
    @GetMapping("/date-range")
    public ResponseEntity<List<Report>> getReportsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return ResponseEntity.ok(reportService.findByDateRange(startDate, endDate));
    }

    // Count reports by status
    @GetMapping("/count/status/{status}")
    public ResponseEntity<Long> countReportsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(reportService.countReportsByStatus(status));
    }

    @GetMapping("/recent")
    public ResponseEntity<List<Report>> getRecentReports() {
        return ResponseEntity.ok(reportService.getRecentReports());
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getReportStats() {
        return ResponseEntity.ok(reportService.getReportStatistics());
    }
}