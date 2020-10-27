package com.task2.repository;


import com.task2.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report,Long> {
    @Query("select r from Report r where r.id = :id")
    Report findReportById(long id);

    @Query("select r from Report r where r.status = :name")
    List<Report> reportDataList(String name);
}
