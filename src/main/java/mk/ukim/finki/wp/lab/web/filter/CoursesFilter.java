package mk.ukim.finki.wp.lab.web.filter;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter
public class CoursesFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        Long courseId = (Long) request.getSession().getAttribute("SelectedCourse");

        String path = request.getServletPath();
        if ((!path.equals("/listCourses") && !path.equals("/studentsInCourse")) && courseId == null)response.sendRedirect("/listCourses");
        else filterChain.doFilter(servletRequest,servletResponse);
    }
}
