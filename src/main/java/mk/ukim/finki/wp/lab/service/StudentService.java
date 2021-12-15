package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.repository.jpa.StudentRepository;
import mk.ukim.finki.wp.lab.service.Interface.IStudentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService implements IStudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> listAll() {
        return studentRepository.findAll();
    }

    @Override
    public List<Student> searchByNameOrSurname(String text) {
        return studentRepository.findStudentByName(text);
    }

    @Override
    public Student findByUsername(String username) {
        return studentRepository.findByUsername(username).orElse(null);
    }

    @Override
    public Student save(String username, String password, String name, String surname) {
        Student s = new Student(0L,username,password,name,surname);
        studentRepository.save(s);
        return s;
    }

    public List<Student> searchStudents (String searchP) {
        return studentRepository.findStudentsByNameIgnoreCaseContainsOrSurnameContainsIgnoreCaseOrUsernameContainsIgnoreCase(searchP,searchP,searchP);
    }

    public Student save(Student s) {
        return studentRepository.save(s);
    }
}
