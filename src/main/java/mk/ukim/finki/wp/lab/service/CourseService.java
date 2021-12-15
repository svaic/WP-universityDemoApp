package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.*;
import mk.ukim.finki.wp.lab.model.Enum.CourseType;
import mk.ukim.finki.wp.lab.model.Enum.Grades;
import mk.ukim.finki.wp.lab.repository.jpa.CourseRepository;
import mk.ukim.finki.wp.lab.repository.jpa.GradeRepository;
import mk.ukim.finki.wp.lab.service.Interface.ICourseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CourseService implements ICourseService {

    private final StudentService studentService;
    private final CourseRepository courseRepository;
    private final GradeService gradeService;

    public CourseService(StudentService studentService, CourseRepository courseRepository, GradeService gradeService) {
        this.studentService = studentService;
        this.courseRepository = courseRepository;
        this.gradeService = gradeService;
    }

    @Override
    public List<Student> findStudentsWithCourse(Long courseId) {
        return courseRepository.findById(courseId).orElse(null).getStudents();
    }

    @Override
    public List<Student> listStudentsNotHavingCourse(Long courseId) {
        List<Student> all = studentService.listAll();
        List<Student> studentsInClass = findStudentsWithCourse(courseId);
        return all.stream().filter(x -> !studentsInClass.contains(x)).collect(Collectors.toList());
    }

    @Override
    public List<Course> searchCourses(String text) {
        Stream<Course> st1 = gradeService.searchStudents(text).stream().map(Grade::getCourse).distinct();
        Stream<Course> st2 =  courseRepository.findCourseByDescriptionContainingIgnoreCaseOrNameContainingIgnoreCase(text,text).stream();
        return Stream.concat(st1,st2).distinct().sorted(Comparator.comparing(Course::getCourseId)).collect(Collectors.toList());
    }

    @Override
    public Course addStudentInCourse(String username, Long courseId) {
        Course c = courseRepository.findById(courseId).orElse(null);
        Student s = studentService.findByUsername(username);

        if (c == null || s == null || c.getStudents().contains(s)) return null;

        c.getStudents().add(s);
        Grade g = new Grade(Grades.F,s,c,LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        gradeService.saveGrade(g);

        return courseRepository.save(c);
    }

    @Override
    public Course findCourse(Long courseId) {
        return courseRepository.findById(courseId).orElseThrow(NullPointerException::new);
    }

    @Override
    public List<Course> listAll() {
        return courseRepository.findAll().stream().sorted(Comparator.comparing(Course::getName)).collect(Collectors.toList());
    }

    @Override
    public List<Course> getCoursesByType(CourseType type) {
        return courseRepository.findByType(type);
    }

    @Override
    public Course saveCourse(Course c) {
        return courseRepository.save(c);
    }

    @Override
    @Transactional
    public Course deleteCourse(Long courseId) {
        Course c = courseRepository.findById(courseId).orElseThrow(NullPointerException::new);
        courseRepository.delete(c);
        return c;
    }
}
