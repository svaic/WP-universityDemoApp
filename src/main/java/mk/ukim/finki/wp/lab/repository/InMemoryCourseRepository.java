package mk.ukim.finki.wp.lab.repository;

import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.CourseType;
import mk.ukim.finki.wp.lab.model.Student;
import org.springframework.stereotype.Repository;

import javax.management.InstanceNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Repository
public class InMemoryCourseRepository {

    public List<Course> findAllCourses()
    {
        return DataHolder.courses;
    }

    public Course findById(Long courseId) throws NullPointerException
    {
        return DataHolder.courses.stream().filter(x->x.getCourseId().equals(courseId)).findFirst().orElseThrow(NullPointerException::new);
    }

    public List<Student> findAllStudentsByCourse(Long courseId)
    {
        return DataHolder.courses.stream().filter(x->x.getCourseId().equals(courseId)).flatMap(r->r.getStudents().stream()).collect(Collectors.toList());
    }

    public Course addStudentToCourse(Student student, Course course)
    {
        course.getStudents().add(student);
        return course;
    }

    public List<Course> findAllCoursesByName(String text)
    {
        return DataHolder.courses.stream().filter(x->x.getName().toLowerCase(Locale.ROOT).contains(text.toLowerCase(Locale.ROOT)) || x.getDescription().toLowerCase(Locale.ROOT).contains(text.toLowerCase(Locale.ROOT))).collect(Collectors.toList());
    }

    public List<Course> findByType(CourseType type)
    {
        return DataHolder.courses.stream().filter(x->x.getType().equals(type)).collect(Collectors.toList());
    }

    public boolean saveCourse(Course c)
    {
        if (DataHolder.courses.removeIf(x->x.getCourseId().equals(c.getCourseId())));
        return DataHolder.courses.add(c);
    }

    public boolean deleteCourse(Long id)
    {
        return DataHolder.courses.removeIf(x->x.getCourseId().equals(id));
    }
}
