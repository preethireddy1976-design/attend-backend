package com.attend.repository;

import com.attend.model.Attendance;
import com.attend.model.AttendanceStatus;
import com.attend.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    List<Attendance> findByDate(LocalDate date);

    Optional<Attendance> findByStudentAndDate(Student student, LocalDate date);

    Optional<Attendance> findByStudentIdAndDate(Long studentId, LocalDate date);

    long countByDateAndStatus(LocalDate date, AttendanceStatus status);

    long countByDate(LocalDate date);

    List<Attendance> findByStudentIdOrderByDateDesc(Long studentId);

    void deleteByStudentId(Long studentId);

    @Query("SELECT DISTINCT a.date FROM Attendance a ORDER BY a.date DESC")
    List<LocalDate> findDistinctRecordedDates();
}
