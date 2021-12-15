package mk.ukim.finki.wp.lab.repository.jpa;

import mk.ukim.finki.wp.lab.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface GradeRepository extends JpaRepository<Grade, Long> {

    List<Grade> findAllByCourse_CourseIdOrderById(Long courseId);

    Optional<Grade> findByStudent_IdAndCourse_CourseId(Long studentId, Long courseId);

    List<Grade> findAllByCourse_CourseIdAndTimestampBetweenOrderById(Long courseId, LocalDateTime start, LocalDateTime end);

    List<Grade> findAllByStudent_NameContainsIgnoreCaseOrStudent_SurnameIgnoreCaseContainsOrStudent_UsernameContainsIgnoreCase(String n,String s,String u);
}
