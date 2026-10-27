package com.attend.controller;

import com.attend.dto.*;
import com.attend.service.AttendanceService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin(origins = "*")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<AttendanceSessionDto>> getAttendance(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        LocalDate targetDate = (date != null) ? date : LocalDate.now();
        AttendanceSessionDto session = attendanceService.getAttendanceForDate(targetDate);
        return ResponseEntity.ok(ApiResponse.ok("Attendance records retrieved", session));
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse<AttendanceSessionDto>> saveAttendance(
            @Valid @RequestBody SaveAttendanceRequest request) {
        AttendanceSessionDto session = attendanceService.saveOrUpdateAttendance(request);
        return ResponseEntity.ok(ApiResponse.ok("Attendance saved successfully", session));
    }

    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<SummaryDto>> getSummary(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        LocalDate targetDate = (date != null) ? date : LocalDate.now();
        SummaryDto summary = attendanceService.getSummaryForDate(targetDate);
        return ResponseEntity.ok(ApiResponse.ok("Summary retrieved", summary));
    }

    @GetMapping("/sessions")
    public ResponseEntity<ApiResponse<List<DateSummaryDto>>> getRecentSessions() {
        List<DateSummaryDto> sessions = attendanceService.getRecentSessions();
        return ResponseEntity.ok(ApiResponse.ok("Recent sessions retrieved", sessions));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<ApiResponse<List<AttendanceRecordDto>>> getStudentHistory(
            @PathVariable Long studentId) {
        List<AttendanceRecordDto> history = attendanceService.getStudentHistory(studentId);
        return ResponseEntity.ok(ApiResponse.ok("Student attendance history retrieved", history));
    }
}
