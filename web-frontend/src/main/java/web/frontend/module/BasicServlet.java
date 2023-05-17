package web.frontend.module;

import com.fasterxml.jackson.databind.ObjectMapper;
import backend.module.dao.memory.CateringDaoMemory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import backend.module.dao.CateringDao;
import backend.module.model.Catering;

import java.io.IOException;

@WebServlet("/food")
public class BasicServlet extends HttpServlet {

    private CateringDao cateringDao;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        cateringDao = new CateringDaoMemory();
        objectMapper = ObjectMapperFactory.getObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idParam = req.getParameter("id");
        if (idParam == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            try {
                Long id = Long.parseLong(idParam);
                Catering catering = cateringDao.findById(id);
                if (catering == null) {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                } else {
                    resp.setHeader("Content-Type", "application/json");
                    objectMapper.writeValue(resp.getOutputStream(), catering);
                }
            } catch (NumberFormatException exc) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Catering catering = objectMapper.readValue(req.getInputStream(), Catering.class);
            if (catering == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            } else {
                cateringDao.create(catering);
                resp.setHeader("Content-Type", "application/json");
                objectMapper.writeValue(resp.getOutputStream(), catering);
            }
        } catch (NumberFormatException exc) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        String idParam = req.getParameter("id");
        if (idParam == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            try {
                Long id = Long.parseLong(idParam);
                cateringDao.delete(id);
            } catch (NumberFormatException exc) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Catering catering = objectMapper.readValue(req.getInputStream(), Catering.class);
        Long idParam = catering.getId();
        String name = catering.getName();
        try {
            Catering catering2 = cateringDao.findById(idParam);
            catering2.setName(name);
        } catch (NumberFormatException exc) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}