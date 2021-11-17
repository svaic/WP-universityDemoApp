package mk.ukim.finki.wp.lab.web.servlet;

import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.imp.IStudentService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="ListStudentServlet",urlPatterns = "/addstudent")
public class ListStudentServlet extends HttpServlet {

    private final SpringTemplateEngine springTemplateEngine;
    private final IStudentService studentService;
    private final CourseService courseService;

    public ListStudentServlet(SpringTemplateEngine springTemplateEngine, IStudentService studentService, CourseService courseService) {
        this.springTemplateEngine = springTemplateEngine;
        this.studentService = studentService;
        this.courseService = courseService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=utf-8");
        WebContext webContext = new WebContext(req,resp,req.getServletContext());

        Long courseId = (Long) req.getSession().getAttribute("selectedCourse");

        webContext.setVariable("students",courseService.listStudentsNotHavingCourse(courseId));//courseService.listStudentsNotHavingCourse(course.getCourseId()
        springTemplateEngine.process("listStudents.html",webContext,resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext webContext = new WebContext(req,resp, req.getServletContext());
        resp.setContentType("text/html; charset=utf-8");

        Long courseId = (Long) webContext.getSession().getAttribute("selectedCourse");
        String username = req.getParameter("username");
        courseService.addStudentInCourse(username, courseId);

        resp.sendRedirect("/studentsInCourse");
    }
}
