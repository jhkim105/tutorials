package jhkim105.tutorials.spring.mvc.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;

@WebServlet(name = "helloServlet", urlPatterns = "/hello")
@Slf4j
public class HelloServlet extends HttpServlet {
 
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("Hello, Servlet");

        String username = request.getParameter("username");
        log.debug("username: {}", username);

        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write("Hello " + username);
    }
}