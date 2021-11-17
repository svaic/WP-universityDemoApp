package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.CourseType;
import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.TeacherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = {"/courses"}, produces = "text/html; charset=utf-8")
public class CourseController {

    private final CourseService courseService;
    private final TeacherService teacherService;

    public CourseController(CourseService courseService, TeacherService teacherService) {
        this.courseService = courseService;
        this.teacherService = teacherService;
    }

    @GetMapping
    public String getCoursesPage(@RequestParam(required = false) String error, @RequestParam(required = false) String search, HttpServletRequest req, Model model, @RequestParam(required = false) CourseType type) {
        if (req.getSession().getAttribute("selectedCourse") != null) {
            Long id = (Long) req.getSession().getAttribute("selectedCourse");
            model.addAttribute("selectedCourse", id);
        }
        if (error != null) {
            model.addAttribute("error", error);
        }
        else setCoursesToModel(search, type, model);
        return "listCourses";
    }

    @PostMapping
    public String getCoursesPage(@RequestParam(required = false) Long courseId, @RequestParam(required = false) String error, @RequestParam(required = false) String search, HttpServletRequest req, Model model, @RequestParam(required = false) CourseType type) {
        if (courseId != null) {
            req.getSession().setAttribute("selectedCourse", courseId);
            model.addAttribute("selectedCourse", courseId);
        }
        if (error != null) {
            model.addAttribute("error", error);
        }
        setCoursesToModel(search, type, model);
        return "listCourses";
    }


    @GetMapping("/edit-form")
    public String getAddCoursePage(Model model) {
        List<Teacher> teachers = teacherService.findAll();
        model.addAttribute("teachers", teachers);
        return "add-course";
    }

    @PostMapping("/edit-form")
    public String getAddCoursePage(@RequestParam(required = false) Long id, @RequestParam String name, @RequestParam String description, @RequestParam Long teacher, @RequestParam CourseType type) {

        if (id != null)
            try {
                courseService.saveCourse(new Course(id, name, description, courseService.findCourse(id).getStudents(), teacherService.findById(teacher), type));
            } catch (NullPointerException e) {
                return "redirect:/courses?error=course not valid";
            }
        else
            courseService.saveCourse(new Course(name, description, new ArrayList<>(), teacherService.findById(teacher), type));

        return "redirect:/courses";
    }

    @GetMapping("/edit-form/{id}")
    public String getEditCoursePage(@PathVariable Long id, Model model) {
        Course c;
        try {
            c = courseService.findCourse(id);
        } catch (NullPointerException e) {
            return "redirect:/courses?error=no valid id given";
        }

        List<Teacher> teachers = teacherService.findAll();
        model.addAttribute("course", c);
        model.addAttribute("teachers", teachers);
        return "add-course";
    }

    @GetMapping("/delete/{id}")
    public String deleteCourse(@PathVariable Long id) {
        if (!courseService.deleteCourse(id)) {
            return "redirect:/courses?error=error cannot delete";
        }
        return "redirect:/courses";
    }

    private void setCoursesToModel(String search,CourseType type, Model model) {
        if (search != null) {
            model.addAttribute("search", search);
            model.addAttribute("courses", courseService.searchCourses(search));
        }
        else if (type != null)
        {
            model.addAttribute("courses",courseService.getCoursesByType(type));
        }else {
            model.addAttribute("courses", courseService.listAll());
        }
    }
}
