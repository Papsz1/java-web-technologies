package web.frontend.module;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogoutServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LOGGER.info("Logged out");
        req.getSession().invalidate();
        resp.sendRedirect("/web/login");
    }
}