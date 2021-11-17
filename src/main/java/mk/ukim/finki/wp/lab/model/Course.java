package mk.ukim.finki.wp.lab.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.annotation.PostConstruct;
import java.util.List;

@AllArgsConstructor
@Data
public class Course {
    private Long courseId;
    private String name;
    private String description;
    private List<Student> students;
    private Teacher teacher;
    private CourseType type;

    public Course(String name, String description, List<Student> students, Teacher teacher, CourseType type) {
        this.name = name;
        this.description = description;
        this.students = students;
        this.teacher = teacher;
        this.courseId = (long) (Math.random() * 1000);
        this.type = type;
    }
}

