package mk.ukim.finki.wp.lab.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class Course {
    private Long courseId;
    private String name;
    private String description;
    private List<Student> students;
}
