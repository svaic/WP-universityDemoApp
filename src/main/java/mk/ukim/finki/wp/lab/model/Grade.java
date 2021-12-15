package mk.ukim.finki.wp.lab.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mk.ukim.finki.wp.lab.model.Enum.Grades;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Character grade;

    public Grade(Grades grade, Student student, Course course, LocalDateTime timestamp) {
        this.student = student;
        this.course = course;
        this.timestamp = timestamp;
        setGrade(grade);
    }

    public void setGrade(Grades grade)
    {
        this.grade = grade.name().charAt(0);
    }

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Student student;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Course course;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;
}
