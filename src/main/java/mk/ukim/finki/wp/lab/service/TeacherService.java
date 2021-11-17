package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.repository.InMemoryTeacherRepository;
import mk.ukim.finki.wp.lab.service.imp.ITeacherService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService implements ITeacherService {
    private final InMemoryTeacherRepository teacherRepository;

    public TeacherService(InMemoryTeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public List<Teacher> findAll() {
        return teacherRepository.findAll();
    }

    @Override
    public Teacher findById(Long id) {
        return teacherRepository.findById(id);
    }
}
