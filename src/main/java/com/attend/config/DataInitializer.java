package com.attend.config;

import com.attend.model.Attendance;
import com.attend.model.AttendanceStatus;
import com.attend.model.Student;
import com.attend.repository.AttendanceRepository;
import com.attend.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final StudentRepository studentRepository;
    private final AttendanceRepository attendanceRepository;

    public DataInitializer(StudentRepository studentRepository, AttendanceRepository attendanceRepository) {
        this.studentRepository = studentRepository;
        this.attendanceRepository = attendanceRepository;
    }

    @Override
    public void run(String... args) {
        if (studentRepository.count() == 0) {
            log.info("Initializing sample student records in MySQL...");
            List<Student> sampleStudents = Arrays.asList(
                    new Student("CS-101", "Aarav Sharma", "aarav.sharma@attend.edu", "Computer Science"),
                    new Student("CS-102", "Diya Patel", "diya.patel@attend.edu", "Computer Science"),
                    new Student("CS-103", "Rahul Reddy", "rahul.reddy@attend.edu", "Computer Science"),
                    new Student("IT-201", "Sneha Rao", "sneha.rao@attend.edu", "Information Technology"),
                    new Student("IT-202", "Vikram Singh", "vikram.singh@attend.edu", "Information Technology"),
                    new Student("EC-301", "Ananya Iyer", "ananya.iyer@attend.edu", "Electronics & Comm."),
                    new Student("EC-302", "Karan Verma", "karan.verma@attend.edu", "Electronics & Comm."),
                    new Student("ME-401", "Pooja Hegde", "pooja.hegde@attend.edu", "Mechanical Eng."),
                    new Student("ME-402", "Rohan Gupta", "rohan.gupta@attend.edu", "Mechanical Eng."),
                    new Student("CS-104", "Meera Nambiar", "meera.nambiar@attend.edu", "Computer Science")
            );
            List<Student> savedStudents = studentRepository.saveAll(sampleStudents);
            log.info("Saved {} students successfully.", savedStudents.size());

            // Add sample attendance for yesterday
            LocalDate yesterday = LocalDate.now().minusDays(1);
            LocalDate twoDaysAgo = LocalDate.now().minusDays(2);

            for (int i = 0; i < savedStudents.size(); i++) {
                Student s = savedStudents.get(i);
                AttendanceStatus yesterdayStatus = (i % 4 == 0) ? AttendanceStatus.ABSENT : AttendanceStatus.PRESENT;
                AttendanceStatus twoDaysAgoStatus = (i % 3 == 0) ? AttendanceStatus.ABSENT : AttendanceStatus.PRESENT;

                attendanceRepository.save(new Attendance(s, yesterday, yesterdayStatus, yesterdayStatus == AttendanceStatus.ABSENT ? "Medical leave" : "Present"));
                attendanceRepository.save(new Attendance(s, twoDaysAgo, twoDaysAgoStatus, twoDaysAgoStatus == AttendanceStatus.ABSENT ? "Uninformed absence" : "Present"));
            }
            log.info("Created sample attendance sessions for {} and {}", twoDaysAgo, yesterday);
        }
    }
}
