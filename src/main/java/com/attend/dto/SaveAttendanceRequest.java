package com.attend.dto;

import com.attend.model.AttendanceStatus;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public class SaveAttendanceRequest {

    @NotNull(message = "Date is required")
    private LocalDate date;

    @NotNull(message = "Attendance records list cannot be null")
    private List<Item> items;

    public static class Item {
        @NotNull(message = "Student ID is required")
        private Long studentId;

        @NotNull(message = "Status is required")
        private AttendanceStatus status;

        private String remarks;

        public Item() {
        }

        public Item(Long studentId, AttendanceStatus status, String remarks) {
            this.studentId = studentId;
            this.status = status;
            this.remarks = remarks;
        }

        public Long getStudentId() {
            return studentId;
        }

        public void setStudentId(Long studentId) {
            this.studentId = studentId;
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
    }

    public SaveAttendanceRequest() {
    }

    public SaveAttendanceRequest(LocalDate date, List<Item> items) {
        this.date = date;
        this.items = items;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
