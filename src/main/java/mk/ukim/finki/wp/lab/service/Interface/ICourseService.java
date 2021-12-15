package mk.ukim.finki.wp.lab.service.Interface;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Enum.CourseType;
import mk.ukim.finki.wp.lab.model.Student;

import java.util.List;

public interface ICourseService {
    List<Student> findStudentsWithCourse(Long courseId);
    List<Student> listStudentsNotHavingCourse(Long courseId);
    List<Course> searchCourses(String text);
    Course addStudentInCourse(String username, Long courseId);
    Course findCourse(Long courseId);
    List<Course>  listAll();
    List<Course> getCoursesByType(CourseType type);
    Course saveCourse(Course c);
    Course deleteCourse(Long courseId);
}