package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Enum.CourseType;
import mk.ukim.finki.wp.lab.service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = {"/courses"}, produces = "text/html; charset=utf-8")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public String getCoursesPage(@RequestParam(required = false) String error,
                                 @RequestParam(required = false) String search,
                                 @SessionAttribute(required = false) Long selectedCourse,
                                 @RequestParam(required = false) CourseType type,
                                 Model model) {
        if (selectedCourse != null) {
            model.addAttribute("selectedCourse", selectedCourse);
        }
        setCoursesToModel(error, search, type, model);
        return "listCourses";
    }

    @PostMapping
    public String getCoursesPage(@RequestParam Long courseId,
                                 RedirectAttributes redirectAttributes,
                                 HttpSession session) {
        if (courseId != null) {
            session.setAttribute("selectedCourse", courseId);
        } else redirectAttributes.addAttribute("error", "selected course is null");
        return "redirect:/courses";
    }

    private void setCoursesToModel(String error, String search, CourseType type, Model model) {
        if (error != null) {
            model.addAttribute("error", error);
        }

        try {
            if (search != null) {
                model.addAttribute("search", search);
                model.addAttribute("courses", courseService.searchCourses(search));
            } else if (type != null) {
                model.addAttribute("courses", courseService.getCoursesByType(type));
            } else {
                model.addAttribute("courses", courseService.listAll());
            }
        } catch (NullPointerException e) {
            model.addAttribute("error", "error searching for course");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
    }
}
