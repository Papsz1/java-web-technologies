package web.frontend.module;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebFilter("/placeholder")
public class Filter extends HttpFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(Filter.class);

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws
            IOException, ServletException {
        String name = (String) req.getSession().getAttribute("name");
        String password = (String) req.getSession().getAttribute("password");
        LOGGER.info("/placeholder");
        LOGGER.info(name);
        if (name == null || password == null) {
            LOGGER.info("/placeholder - Incorrect name and password");
            res.sendRedirect("/web/login");
        } else {
            chain.doFilter(req, res);
        }
    }
}