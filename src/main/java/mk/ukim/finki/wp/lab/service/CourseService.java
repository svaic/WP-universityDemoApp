package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.CourseType;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.repository.InMemoryCourseRepository;
import mk.ukim.finki.wp.lab.service.imp.ICourseService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService implements ICourseService {

    private final StudentService studentService;
    private final InMemoryCourseRepository courseRepository;

    public CourseService(StudentService studentService, InMemoryCourseRepository courseRepository) {
        this.studentService = studentService;
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Student> listStudentsByCourse(Long courseId) {
        return courseRepository.findAllStudentsByCourse(courseId);
    }

    @Override
    public List<Student> listStudentsNotHavingCourse(Long courseId) {
        List<Student> all = studentService.listAll();
        List<Student> studentsInClass = listStudentsByCourse(courseId);
        return all.stream().filter(x -> !studentsInClass.contains(x)).collect(Collectors.toList());
    }

    @Override
    public List<Course> searchCourses(String text) {
        return courseRepository.findAllCoursesByName(text);
    }

    @Override
    public Course addStudentInCourse(String username, Long courseId) {
        Course c = courseRepository.findById(courseId);
        Student s = studentService.findByUsername(username);

        if (c == null || s == null || c.getStudents().contains(s)) return null;

        return courseRepository.addStudentToCourse(s, c);
    }

    @Override
    public Course findCourse(Long courseId) {
        return courseRepository.findById(courseId);
    }

    @Override
    public List<Course> listAll() {
        return courseRepository.findAllCourses().stream().sorted(Comparator.comparing(Course::getName)).collect(Collectors.toList());
    }

    @Override
    public List<Course> getCoursesByType(CourseType type) {
        return courseRepository.findByType(type);
    }

    @Override
    public Boolean saveCourse(Course c) {
        return courseRepository.saveCourse(c);
    }

    @Override
    public Boolean deleteCourse(Long courseId) {
        return courseRepository.deleteCourse(courseId);
    }
}
