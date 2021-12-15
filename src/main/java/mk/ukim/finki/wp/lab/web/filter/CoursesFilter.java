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

        Long courseId = (Long) request.getSession().getAttribute("selectedCourse");

        String path = request.getServletPath();
        if ((!path.equals("/h2") && !path.equals("/courses") && !path.equals("/studentsInCourse") && !path.startsWith("/courses")) && courseId == null){
            response.sendRedirect("/courses?error="+ "select a course");
        }
        else filterChain.doFilter(servletRequest,servletResponse);
    }
}
