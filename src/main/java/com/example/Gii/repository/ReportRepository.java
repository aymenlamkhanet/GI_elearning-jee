package com.example.Gii.repository;

import com.example.Gii.entity.Report;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReportRepository extends MongoRepository<Report, String> {
    List<Report> findByStatus(String status);
    List<Report> findByAuthorId(String authorId);
    List<Report> findByTitleContainingIgnoreCase(String title);
    List<Report> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<Report> findByAuthorIdAndStatus(String authorId, String status);
    Optional<Report> findByTitle(String title);
}