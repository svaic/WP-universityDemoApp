package mk.ukim.finki.wp.lab.service.Interface;

import mk.ukim.finki.wp.lab.model.Teacher;

import java.util.List;

public interface ITeacherService {
    List<Teacher> findAll();
    Teacher findById(Long id);
}
