// 3. SERVICE
package com.example.Gii.service;

import com.example.Gii.entity.Report;
import com.example.Gii.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;

    // Get all reports
    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    // Get report by ID
    public Report getReportById(String id) {
        return reportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Report non trouvé avec l'ID : " + id));
    }

    // Add new report
    public Report addReport(Report report) {
        if (report.getTitle() == null || report.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Le titre est obligatoire");
        }

        if (report.getCreatedAt() == null) {
            report.setCreatedAt(LocalDateTime.now());
        }

        if (report.getStatus() == null) {
            report.setStatus("DRAFT");
        }

        return reportRepository.save(report);
    }

    // Update report
    public Report updateReport(String id, Report reportDetails) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Report non trouvé avec l'ID : " + id));

        report.setTitle(reportDetails.getTitle());
        report.setDescription(reportDetails.getDescription());
        report.setStatus(reportDetails.getStatus());
        report.setAuthorId(reportDetails.getAuthorId());
        report.setUpdatedAt(LocalDateTime.now());

        return reportRepository.save(report);
    }

    // Update report status
    public Report updateReportStatus(String id, String status) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Report non trouvé avec l'ID : " + id));

        report.setStatus(status);
        report.setUpdatedAt(LocalDateTime.now());

        return reportRepository.save(report);
    }

    // Delete report
    public void deleteReport(String id) {
        if (!reportRepository.existsById(id)) {
            throw new RuntimeException("Report non trouvé avec l'ID : " + id);
        }
        reportRepository.deleteById(id);
    }

    // Find reports by status
    public List<Report> findByStatus(String status) {
        return reportRepository.findByStatus(status);
    }

    // Find reports by author
    public List<Report> findByAuthor(String authorId) {
        return reportRepository.findByAuthorId(authorId);
    }

    // Find reports by title
    public List<Report> findByTitle(String title) {
        return reportRepository.findByTitleContainingIgnoreCase(title);
    }

    // Find reports by date range
    public List<Report> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return reportRepository.findByCreatedAtBetween(startDate, endDate);
    }

    // Count reports
    public Long countReports() {
        return reportRepository.count();
    }

    // Count reports by status
    public Long countReportsByStatus(String status) {
        return (long) reportRepository.findByStatus(status).size();
    }
}