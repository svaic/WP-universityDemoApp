package mk.ukim.finki.wp.lab.repository;

import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab.model.Student;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class InMemoryStudentRepository {

    public List<Student> findAllStudents() {
        return DataHolder.students;
    }

    public Student save(Student s)
    {
        if(s == null || s.getName().isEmpty())
        {
            return null;
        }
        DataHolder.students.removeIf(x->x.getUsername().equals(s.getUsername()));
        DataHolder.students.add(s);

        return s;
    }

    public  List<Student> findAllByNameOrSurname(String text)
    {
        return DataHolder.students.stream().filter(x->x.getUsername().contains(text) || x.getSurname().contains(text)).collect(Collectors.toList());
    }

    public Optional<Student> findByUsername(String username)
    {
        return DataHolder.students.stream().filter(x ->x.getUsername().equals(username)).findFirst();
    }


}
