package com.attend.service;

import com.attend.dto.StudentDto;
import com.attend.exception.DuplicateResourceException;
import com.attend.exception.ResourceNotFoundException;
import com.attend.model.Student;
import com.attend.repository.AttendanceRepository;
import com.attend.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final AttendanceRepository attendanceRepository;

    public StudentService(StudentRepository studentRepository, AttendanceRepository attendanceRepository) {
        this.studentRepository = studentRepository;
        this.attendanceRepository = attendanceRepository;
    }

    public List<StudentDto> getAllStudents() {
        return studentRepository.findAllByOrderByRollNumberAsc().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public StudentDto getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + id));
        return toDto(student);
    }

    @Transactional
    public StudentDto createStudent(StudentDto dto) {
        String cleanRoll = dto.getRollNumber().trim().toUpperCase();
        if (studentRepository.existsByRollNumber(cleanRoll)) {
            throw new DuplicateResourceException("A student with roll number '" + cleanRoll + "' already exists.");
        }

        Student student = new Student();
        student.setRollNumber(cleanRoll);
        student.setName(dto.getName());
        student.setEmail(dto.getEmail());
        student.setDepartment(dto.getDepartment());

        Student saved = studentRepository.save(student);
        return toDto(saved);
    }

    @Transactional
    public StudentDto updateStudent(Long id, StudentDto dto) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + id));

        String cleanRoll = dto.getRollNumber().trim().toUpperCase();
        if (studentRepository.existsByRollNumberAndIdNot(cleanRoll, id)) {
            throw new DuplicateResourceException("Roll number '" + cleanRoll + "' is already assigned to another student.");
        }

        student.setRollNumber(cleanRoll);
        student.setName(dto.getName());
        student.setEmail(dto.getEmail());
        student.setDepartment(dto.getDepartment());

        Student updated = studentRepository.save(student);
        return toDto(updated);
    }

    @Transactional
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Student not found with ID: " + id);
        }
        // Delete all attendance records associated with this student first
        attendanceRepository.deleteByStudentId(id);
        studentRepository.deleteById(id);
    }

    public long getTotalStudentCount() {
        return studentRepository.count();
    }

    private StudentDto toDto(Student student) {
        return new StudentDto(
                student.getId(),
                student.getRollNumber(),
                student.getName(),
                student.getEmail(),
                student.getDepartment()
        );
    }
}
