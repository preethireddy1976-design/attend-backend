package com.attend.service;

import com.attend.dto.*;
import com.attend.exception.ResourceNotFoundException;
import com.attend.model.Attendance;
import com.attend.model.AttendanceStatus;
import com.attend.model.Student;
import com.attend.repository.AttendanceRepository;
import com.attend.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;

    public AttendanceService(AttendanceRepository attendanceRepository, StudentRepository studentRepository) {
        this.attendanceRepository = attendanceRepository;
        this.studentRepository = studentRepository;
    }

    public AttendanceSessionDto getAttendanceForDate(LocalDate date) {
        List<Student> students = studentRepository.findAllByOrderByRollNumberAsc();
        List<Attendance> existingAttendance = attendanceRepository.findByDate(date);

        Map<Long, Attendance> attendanceMap = existingAttendance.stream()
                .collect(Collectors.toMap(a -> a.getStudent().getId(), a -> a, (a1, a2) -> a1));

        long presentCount = 0;
        long absentCount = 0;
        long unmarkedCount = 0;

        List<AttendanceRecordDto> recordDtos = new ArrayList<>();

        for (Student student : students) {
            Attendance attendance = attendanceMap.get(student.getId());
            AttendanceRecordDto dto = new AttendanceRecordDto();
            dto.setStudentId(student.getId());
            dto.setRollNumber(student.getRollNumber());
            dto.setStudentName(student.getName());
            dto.setDepartment(student.getDepartment());

            if (attendance != null) {
                dto.setAttendanceId(attendance.getId());
                dto.setStatus(attendance.getStatus());
                dto.setRemarks(attendance.getRemarks());
                dto.setMarkedAt(attendance.getMarkedAt());

                if (attendance.getStatus() == AttendanceStatus.PRESENT) {
                    presentCount++;
                } else if (attendance.getStatus() == AttendanceStatus.ABSENT) {
                    absentCount++;
                }
            } else {
                dto.setStatus(null); // unmarked
                unmarkedCount++;
            }
            recordDtos.add(dto);
        }

        long total = students.size();
        double percentage = total > 0 ? Math.round(((double) presentCount / total) * 1000.0) / 10.0 : 0.0;
        boolean hasSavedRecords = !existingAttendance.isEmpty();

        return new AttendanceSessionDto(
                date,
                total,
                presentCount,
                absentCount,
                unmarkedCount,
                percentage,
                hasSavedRecords,
                recordDtos
        );
    }

    @Transactional
    public AttendanceSessionDto saveOrUpdateAttendance(SaveAttendanceRequest request) {
        LocalDate date = request.getDate();
        if (date == null) {
            throw new IllegalArgumentException("Attendance date cannot be null.");
        }

        List<Student> allStudents = studentRepository.findAll();
        Map<Long, Student> studentMap = allStudents.stream()
                .collect(Collectors.toMap(Student::getId, s -> s));

        List<Attendance> existingRecords = attendanceRepository.findByDate(date);
        Map<Long, Attendance> existingMap = existingRecords.stream()
                .collect(Collectors.toMap(a -> a.getStudent().getId(), a -> a, (a1, a2) -> a1));

        List<Attendance> toSave = new ArrayList<>();

        if (request.getItems() != null) {
            // Track processed student IDs to prevent duplicate entries in the same request payload
            Set<Long> processedStudentIds = new HashSet<>();

            for (SaveAttendanceRequest.Item item : request.getItems()) {
                if (item.getStudentId() == null || item.getStatus() == null) {
                    continue;
                }

                if (processedStudentIds.contains(item.getStudentId())) {
                    // Duplicate in same payload: ignore or take latest
                    continue;
                }
                processedStudentIds.add(item.getStudentId());

                Student student = studentMap.get(item.getStudentId());
                if (student == null) {
                    continue;
                }

                Attendance attendance = existingMap.get(student.getId());
                if (attendance != null) {
                    // Update existing record
                    attendance.setStatus(item.getStatus());
                    attendance.setRemarks(item.getRemarks());
                    toSave.add(attendance);
                } else {
                    // Insert new record
                    Attendance newRecord = new Attendance(student, date, item.getStatus(), item.getRemarks());
                    toSave.add(newRecord);
                }
            }
        }

        attendanceRepository.saveAll(toSave);
        return getAttendanceForDate(date);
    }

    public SummaryDto getSummaryForDate(LocalDate date) {
        long totalStudents = studentRepository.count();
        long presentCount = attendanceRepository.countByDateAndStatus(date, AttendanceStatus.PRESENT);
        long absentCount = attendanceRepository.countByDateAndStatus(date, AttendanceStatus.ABSENT);
        long unmarkedCount = Math.max(0, totalStudents - (presentCount + absentCount));
        double percentage = totalStudents > 0 ? Math.round(((double) presentCount / totalStudents) * 1000.0) / 10.0 : 0.0;

        return new SummaryDto(date, totalStudents, presentCount, absentCount, unmarkedCount, percentage);
    }

    public List<DateSummaryDto> getRecentSessions() {
        List<LocalDate> recordedDates = attendanceRepository.findDistinctRecordedDates();
        long totalStudents = studentRepository.count();

        List<DateSummaryDto> summaries = new ArrayList<>();
        for (LocalDate date : recordedDates) {
            long present = attendanceRepository.countByDateAndStatus(date, AttendanceStatus.PRESENT);
            long absent = attendanceRepository.countByDateAndStatus(date, AttendanceStatus.ABSENT);
            double pct = totalStudents > 0 ? Math.round(((double) present / totalStudents) * 1000.0) / 10.0 : 0.0;
            summaries.add(new DateSummaryDto(date, totalStudents, present, absent, pct));
        }
        return summaries;
    }

    public List<AttendanceRecordDto> getStudentHistory(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + studentId));

        List<Attendance> attendances = attendanceRepository.findByStudentIdOrderByDateDesc(studentId);
        return attendances.stream().map(a -> new AttendanceRecordDto(
                a.getId(),
                student.getId(),
                student.getRollNumber(),
                student.getName(),
                student.getDepartment(),
                a.getStatus(),
                a.getRemarks(),
                a.getMarkedAt()
        )).collect(Collectors.toList());
    }
}
