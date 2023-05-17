package web.frontend.module;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String name = request.getParameter("name");
        String password = request.getParameter("password");
        LOGGER.info(name);
        LOGGER.info(password);
        if ("david".equals(name) && "test".equals(password)) {
            LOGGER.info("The name and password is correct");
            request.getSession().setAttribute("name", name);
            request.getSession().setAttribute("password", password);
            response.sendRedirect("/web/placeholder");
        } else {
            response.sendRedirect("/web/login");
            LOGGER.info("The name or password is incorrect");
        }
    }
}
