package com.attend.dto;

import java.time.LocalDate;
import java.util.List;

public class AttendanceSessionDto {
    private LocalDate date;
    private long totalStudents;
    private long presentCount;
    private long absentCount;
    private long unmarkedCount;
    private double attendancePercentage;
    private boolean hasSavedRecords;
    private List<AttendanceRecordDto> records;

    public AttendanceSessionDto() {
    }

    public AttendanceSessionDto(LocalDate date, long totalStudents, long presentCount,
                                long absentCount, long unmarkedCount, double attendancePercentage,
                                boolean hasSavedRecords, List<AttendanceRecordDto> records) {
        this.date = date;
        this.totalStudents = totalStudents;
        this.presentCount = presentCount;
        this.absentCount = absentCount;
        this.unmarkedCount = unmarkedCount;
        this.attendancePercentage = attendancePercentage;
        this.hasSavedRecords = hasSavedRecords;
        this.records = records;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public long getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(long totalStudents) {
        this.totalStudents = totalStudents;
    }

    public long getPresentCount() {
        return presentCount;
    }

    public void setPresentCount(long presentCount) {
        this.presentCount = presentCount;
    }

    public long getAbsentCount() {
        return absentCount;
    }

    public void setAbsentCount(long absentCount) {
        this.absentCount = absentCount;
    }

    public long getUnmarkedCount() {
        return unmarkedCount;
    }

    public void setUnmarkedCount(long unmarkedCount) {
        this.unmarkedCount = unmarkedCount;
    }

    public double getAttendancePercentage() {
        return attendancePercentage;
    }

    public void setAttendancePercentage(double attendancePercentage) {
        this.attendancePercentage = attendancePercentage;
    }

    public boolean isHasSavedRecords() {
        return hasSavedRecords;
    }

    public void setHasSavedRecords(boolean hasSavedRecords) {
        this.hasSavedRecords = hasSavedRecords;
    }

    public List<AttendanceRecordDto> getRecords() {
        return records;
    }

    public void setRecords(List<AttendanceRecordDto> records) {
        this.records = records;
    }
}
