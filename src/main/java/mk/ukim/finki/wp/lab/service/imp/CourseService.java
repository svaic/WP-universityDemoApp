package mk.ukim.finki.wp.lab.service.imp;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;

import java.util.List;

public interface CourseService{
    List<Student> listStudentsByCourse(Long courseId);
    List<Student> listStudentsNotHavingCourse(Long courseId);
    Course addStudentInCourse(String username, Long courseId);
    Course findCourse(Long courseId);
    List<Course>  listAll();
}