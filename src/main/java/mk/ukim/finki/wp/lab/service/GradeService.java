package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Grade;
import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.repository.jpa.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class GradeService {

    @Autowired
    private GradeRepository gradeRepository;

    public List<Grade> findStudentGradesOfCourse(Long courseId) {
        return gradeRepository.findAllByCourse_CourseIdOrderById(courseId);
    }

    public Grade getStudentWithGrade(Long studentId, Long courseId){
        return gradeRepository.findByStudent_IdAndCourse_CourseId(studentId, courseId).orElseThrow(NullPointerException::new);
    }

    public Grade saveGrade(Grade grade) {
        return gradeRepository.save(grade);
    }

    public List<Grade> findByTimestampBetween(Long courseId, LocalDateTime start, LocalDateTime end) {
        return gradeRepository.findAllByCourse_CourseIdAndTimestampBetweenOrderById(courseId, start, end);
    }
}
