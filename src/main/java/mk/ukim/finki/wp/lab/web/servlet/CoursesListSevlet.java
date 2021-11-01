package mk.ukim.finki.wp.lab.web.servlet;

import mk.ukim.finki.wp.lab.service.CourseService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="listServlet",urlPatterns = "/listCourses")
public class CoursesListSevlet extends HttpServlet {

    private final CourseService courseService;
    private final SpringTemplateEngine springTemplateEngine;

    public CoursesListSevlet(CourseService courseService, SpringTemplateEngine springTemplateEngine) {
        this.courseService = courseService;
        this.springTemplateEngine = springTemplateEngine;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext webContext = new WebContext(req,resp,req.getServletContext());
        resp.setContentType("text/html; charset=utf-8");
        webContext.setVariable("courses",courseService.listAll());
        springTemplateEngine.process("listCourses.html",webContext,resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long courseId = Long.valueOf(req.getParameter("courseId"));
        req.getSession().setAttribute("SelectedCourse",courseId);

        WebContext webContext = new WebContext(req,resp,req.getServletContext());
        webContext.setVariable("courses",courseService.listAll());
        webContext.setVariable("selectedCourse",courseId);

        springTemplateEngine.process("listCourses.html",webContext,resp.getWriter());
        //resp.sendRedirect("addstudent");
    }
}
