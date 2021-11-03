package mk.ukim.finki.wp.lab.service.imp;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;

import java.util.List;

public interface ICourseService {
    List<Student> listStudentsByCourse(Long courseId);
    List<Student> listStudentsNotHavingCourse(Long courseId);
    List<Course> searchCourses(String text);
    Course addStudentInCourse(String username, Long courseId);
    Course findCourse(Long courseId);
    List<Course>  listAll();
}