package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Enum.Grades;
import mk.ukim.finki.wp.lab.model.Grade;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("courses/grades")
public class GradeController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private GradeService gradeService;

    @GetMapping()
    public String GradesView(Model model,
                             @SessionAttribute(required = false) Long selectedCourse,
                             @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
                             @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
                             RedirectAttributes redirectAttributes) {
        if (selectedCourse == null) {
            redirectAttributes.addAttribute("error", "select a course");
            return "redirect:/courses";
        }

        try {
            List<Grade> students;
            if (from != null && to != null) {
                students = gradeService.findByTimestampBetween(selectedCourse, from, to);
                model.addAttribute("from", from);
                model.addAttribute("to", to);
            } else students = gradeService.findStudentGradesOfCourse(selectedCourse);

            model.addAttribute("course", courseService.findCourse(selectedCourse));
            model.addAttribute("studentsListeningSelectedCourse", students);
        } catch (NullPointerException e) {
            redirectAttributes.addAttribute("error", "course not found");
        } catch (Exception e) {
            redirectAttributes.addAttribute("error", e.getMessage());
        }

        return "add-grade";
    }

    @PostMapping
    public String GradesView(@SessionAttribute(required = false) Long selectedCourse,
                             @RequestParam Long studentId,
                             @RequestParam Grades grade,
                             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date,
                             @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
                             @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
                             RedirectAttributes redirectAttributes) {
        if (selectedCourse == null) {
            redirectAttributes.addAttribute("error", "select a course");
            return "redirect:/courses";
        }

        try {
            Grade g = gradeService.getStudentWithGrade(studentId, selectedCourse);
            g.setGrade(grade);
            g.setTimestamp(date);
            gradeService.saveGrade(g);
        } catch (NullPointerException e) {
            redirectAttributes.addAttribute("error", "student not found");
        } catch (Exception e) {
            redirectAttributes.addAttribute("error", e.getMessage());
        }

        if (from != null && to != null) {
            redirectAttributes.addAttribute("from", from.toString());
            redirectAttributes.addAttribute("to", to.toString());
        }
        return "redirect:/courses/grades";
    }
}
