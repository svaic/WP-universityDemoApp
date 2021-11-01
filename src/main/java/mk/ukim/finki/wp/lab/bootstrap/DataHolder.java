package mk.ukim.finki.wp.lab.bootstrap;

import lombok.Getter;
import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Getter
public class DataHolder {
    public static List<Student> students = new ArrayList<>();
    public static List<Course> courses = new ArrayList<>();

    @PostConstruct
    public void init()
    {
        students.add(new Student("user1","pass1","Трпе","Трпески"));
        students.add(new Student("user2","pass1","Цветко","Цветковски"));
        students.add(new Student("user3","pass1","Марко","Марковски"));
        students.add(new Student("user4","pass1","Стојко","Стојковски"));
        students.add(new Student("user5","pass1","Ристе","Ристовски"));

        courses.add(new Course(0L,"Апс","Апгоритми и Податочни Структури", new ArrayList<>(students.subList(0,1))));
        courses.add(new Course(1L,"Вис","Веројатност и Статистика",new ArrayList<>(students.subList(1,2))));
        courses.add(new Course(2L,"Нп","Напредно Програмирање",new ArrayList<>(students.subList(2,3))));
        courses.add(new Course(3L,"Ипнкс","Интернет програмирање на клиентска страна",new ArrayList<>(students.subList(3,4))));
        courses.add(new Course(4L,"КМиБ","Компјутерски мрежи и безбедност",new ArrayList<>(students.subList(4,5))));
    }
}
