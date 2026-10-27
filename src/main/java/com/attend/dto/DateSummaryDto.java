package com.attend.dto;

import java.time.LocalDate;

public class DateSummaryDto {
    private LocalDate date;
    private long totalStudents;
    private long presentCount;
    private long absentCount;
    private double percentage;

    public DateSummaryDto() {
    }

    public DateSummaryDto(LocalDate date, long totalStudents, long presentCount, long absentCount, double percentage) {
        this.date = date;
        this.totalStudents = totalStudents;
        this.presentCount = presentCount;
        this.absentCount = absentCount;
        this.percentage = percentage;
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

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
}
