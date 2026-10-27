package com.attend.dto;

import com.attend.model.AttendanceStatus;
import java.time.LocalDateTime;

public class AttendanceRecordDto {
    private Long attendanceId;
    private Long studentId;
    private String rollNumber;
    private String studentName;
    private String department;
    private AttendanceStatus status; // PRESENT, ABSENT, or null (not marked)
    private String remarks;
    private LocalDateTime markedAt;

    public AttendanceRecordDto() {
    }

    public AttendanceRecordDto(Long attendanceId, Long studentId, String rollNumber, String studentName,
                               String department, AttendanceStatus status, String remarks, LocalDateTime markedAt) {
        this.attendanceId = attendanceId;
        this.studentId = studentId;
        this.rollNumber = rollNumber;
        this.studentName = studentName;
        this.department = department;
        this.status = status;
        this.remarks = remarks;
        this.markedAt = markedAt;
    }

    public Long getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(Long attendanceId) {
        this.attendanceId = attendanceId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public AttendanceStatus getStatus() {
        return status;
    }

    public void setStatus(AttendanceStatus status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public LocalDateTime getMarkedAt() {
        return markedAt;
    }

    public void setMarkedAt(LocalDateTime markedAt) {
        this.markedAt = markedAt;
    }
}
