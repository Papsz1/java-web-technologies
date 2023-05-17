package web.frontend.module;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import backend.module.dao.CateringDao;
import backend.module.dao.memory.CateringDaoMemory;
import backend.module.model.Catering;

import java.io.IOException;

@WebServlet("/placeholder")
public class ServletBlueprint extends HttpServlet {

    private final CateringDao cateringDao = new CateringDaoMemory();

    @Override
    public void init() {
        Catering catering = new Catering("Menu", "k2",
                1.0, "k2", "071234568");
        cateringDao.create(catering);
        Catering catering2 = new Catering("Menu2", "k3",
                3.1, "k3", "071234568");
        cateringDao.create(catering2);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("food", cateringDao.findAll());
        req.getRequestDispatcher("placeholder.jsp").forward(req, resp);
    }
}