package com.attend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "students", uniqueConstraints = {
    @UniqueConstraint(columnNames = "roll_number")
})
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "roll_number", nullable = false, unique = true, length = 50)
    private String rollNumber;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "email", length = 120)
    private String email;

    @Column(name = "department", length = 100)
    private String department;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Student() {
    }

    public Student(String rollNumber, String name, String email, String department) {
        this.rollNumber = rollNumber;
        this.name = name;
        this.email = email;
        this.department = department;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber != null ? rollNumber.trim().toUpperCase() : null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name != null ? name.trim() : null;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email != null ? email.trim() : null;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department != null ? department.trim() : null;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
