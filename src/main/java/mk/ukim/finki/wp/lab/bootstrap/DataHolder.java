package mk.ukim.finki.wp.lab.bootstrap;

import lombok.Getter;
import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Enum.CourseType;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.model.TeacherFullName;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.StudentService;
import mk.ukim.finki.wp.lab.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@Profile("h2")
@Getter
public class DataHolder {
    public static List<Student> students = new ArrayList<>();
    public static List<Course> courses = new ArrayList<>();
    public static List<Teacher> teachers = new ArrayList<>();

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private StudentService studentService;

    @PostConstruct
    public void init()
    {
        teachers.add(new Teacher(1L,new TeacherFullName("проф1","презиме1"), LocalDate.now()));
        teachers.add(new Teacher(2L,new TeacherFullName("проф2","презиме2"), LocalDate.now()));
        teachers.add(new Teacher(3L,new TeacherFullName("проф3","презиме3"), LocalDate.now()));

        students.add(new Student(1L,"user1","pass1","Трпе","Трпески"));
        students.add(new Student(2L,"user2","pass1","Цветко","Цветковски"));
        students.add(new Student(3L,"user3","pass1","Марко","Марковски"));
        students.add(new Student(4L,"user4","pass1","Стојко","Стојковски"));
        students.add(new Student(5L,"user5","pass1","Ристе","Ристовски"));

        courses.add(new Course(1L,"Апс","Алгоритми и Податочни Структури", new ArrayList<>(),teachers.get(0), CourseType.MANDATORY));
        courses.add(new Course(2L,"Вис","Веројатност и Статистика",new ArrayList<>(),teachers.get(1), CourseType.MANDATORY));
        courses.add(new Course(3L,"Нп","Напредно Програмирање",new ArrayList<>(),teachers.get(2), CourseType.WINTER));
        courses.add(new Course(4L,"Ипнкс","Интернет програмирање на клиентска страна",new ArrayList<>(),teachers.get(0), CourseType.WINTER));
        courses.add(new Course(5L,"КМиБ","Компјутерски мрежи и безбедност",new ArrayList<>(),teachers.get(2), CourseType.MANDATORY));

        teachers.forEach(x-> System.out.println(teacherService.save(x)));
        students.forEach(x -> System.out.println(studentService.save(x)));
        courses.forEach(x-> System.out.println(courseService.saveCourse(x)));

        courseService.addStudentInCourse(students.get(0).getUsername(),courses.get(0).getCourseId());
        courseService.addStudentInCourse(students.get(1).getUsername(),courses.get(1).getCourseId());
        courseService.addStudentInCourse(students.get(2).getUsername(),courses.get(2).getCourseId());
        courseService.addStudentInCourse(students.get(3).getUsername(),courses.get(3).getCourseId());
        courseService.addStudentInCourse(students.get(4).getUsername(),courses.get(4).getCourseId());
        System.out.println();
    }
}
