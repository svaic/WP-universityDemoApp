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
        students.add(new Student("user1","pass1","Tpre","Trpevski"));
        students.add(new Student("user2","pass1","Cvetko","Cvetkovski"));
        students.add(new Student("user3","pass1","Marko","Markovski"));
        students.add(new Student("user4","pass1","Stojko","Stojkovski"));
        students.add(new Student("user5","pass1","Riste","Ristovski"));

        courses.add(new Course(0L,"Aps","Algoritmi i Podatocni Strukturi", new ArrayList<>(students.subList(0,1))));
        courses.add(new Course(1L,"Vis","Verojatnost i Statistika",new ArrayList<>(students.subList(1,2))));
        courses.add(new Course(2L,"NP","Napredno Programiranje",new ArrayList<>(students.subList(2,3))));
        courses.add(new Course(3L,"IP","Internet Tehnologija na klientska strana",new ArrayList<>(students.subList(3,4))));
        courses.add(new Course(4L,"KmB","Kompjuterski mrezi i bezbednost",new ArrayList<>(students.subList(4,5))));
    }
}
