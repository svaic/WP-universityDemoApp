package mk.ukim.finki.wp.lab.model;

import lombok.*;
import mk.ukim.finki.wp.lab.model.Enum.CourseType;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;

    private String name;

    private String description;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Student> students;

    @ManyToOne(cascade = CascadeType.ALL)
    private Teacher teacher;

    @Enumerated(EnumType.STRING)
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

