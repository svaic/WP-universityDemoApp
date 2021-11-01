package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.repository.InMemoryStudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService implements mk.ukim.finki.wp.lab.service.imp.StudentService {

    private final InMemoryStudentRepository studentRepository;

    public StudentService(InMemoryStudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> listAll() {
        return studentRepository.findAllStudents();
    }

    @Override
    public List<Student> searchByNameOrSurname(String text) {
        return studentRepository.findAllByNameOrSurname(text);
    }

    @Override
    public Student findByUsername(String username) {
        return studentRepository.findByUsername(username).orElse(null);
    }

    @Override
    public Student save(String username, String password, String name, String surname) {
        Student s = new Student(username,password,name,surname);
        studentRepository.save(s);
        return s;
    }
}
