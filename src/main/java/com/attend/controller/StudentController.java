package com.attend.controller;

import com.attend.dto.ApiResponse;
import com.attend.dto.StudentDto;
import com.attend.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<StudentDto>>> getAllStudents() {
        List<StudentDto> list = studentService.getAllStudents();
        return ResponseEntity.ok(ApiResponse.ok("Students retrieved successfully", list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentDto>> getStudentById(@PathVariable Long id) {
        StudentDto student = studentService.getStudentById(id);
        return ResponseEntity.ok(ApiResponse.ok("Student found", student));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<StudentDto>> createStudent(@Valid @RequestBody StudentDto dto) {
        StudentDto created = studentService.createStudent(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Student registered successfully", created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentDto>> updateStudent(@PathVariable Long id, @Valid @RequestBody StudentDto dto) {
        StudentDto updated = studentService.updateStudent(id, dto);
        return ResponseEntity.ok(ApiResponse.ok("Student updated successfully", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok(ApiResponse.ok("Student deleted successfully", null));
    }
}
