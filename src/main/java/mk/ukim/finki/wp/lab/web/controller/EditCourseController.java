package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Enum.CourseType;
import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("courses/course")
public class EditCourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private TeacherService teacherService;

    @GetMapping
    public String getAddCoursePage(Model model) {
        List<Teacher> teachers = teacherService.findAll();
        model.addAttribute("teachers", teachers);
        return "add-course";
    }

    @PostMapping
    public String getAddCoursePage(@RequestParam(required = false) Long id,
                                   @RequestParam String name,
                                   @RequestParam String description,
                                   @RequestParam Long teacher,
                                   @RequestParam CourseType type,
                                   RedirectAttributes redirectAttributes) {
        if (id != null)
            try {
                courseService.saveCourse(new Course(id, name, description, courseService.findCourse(id).getStudents(), teacherService.findById(teacher), type));
            } catch (NullPointerException e) {
                redirectAttributes.addAttribute("error", "course not found");
                return "redirect:/courses?error=course not valid";
            }
        else
            courseService.saveCourse(new Course(name, description, new ArrayList<>(), teacherService.findById(teacher), type));

        return "redirect:/courses";
    }

    @GetMapping("/{id}")
    public String getEditCoursePage(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Course c;
        try {
            c = courseService.findCourse(id);
        } catch (NullPointerException e) {
            redirectAttributes.addAttribute("error", "course not found");
            return "redirect:/courses";
        }

        List<Teacher> teachers = teacherService.findAll();
        model.addAttribute("course", c);
        model.addAttribute("teachers", teachers);
        return "add-course";
    }

    @GetMapping("/delete/{id}")
    public String deleteCourse(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            courseService.deleteCourse(id);
            if (courseService.deleteCourse(id) == null) {
                return "redirect:/courses?error=error cannot delete";
            }
        } catch (NullPointerException e) {
            redirectAttributes.addAttribute("error", "course not found");
        } catch (org.hibernate.exception.ConstraintViolationException e) {
            redirectAttributes.addAttribute("error", "students are in the class");
        } catch (Exception e) {
            redirectAttributes.addAttribute("error", e.getMessage());
        }
        return "redirect:/courses";
    }
}
