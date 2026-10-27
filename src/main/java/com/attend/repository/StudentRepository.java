package com.attend.repository;

import com.attend.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    boolean existsByRollNumber(String rollNumber);

    boolean existsByRollNumberAndIdNot(String rollNumber, Long id);

    Optional<Student> findByRollNumber(String rollNumber);

    List<Student> findAllByOrderByRollNumberAsc();

    List<Student> findByNameContainingIgnoreCaseOrRollNumberContainingIgnoreCase(String name, String rollNumber);
}
