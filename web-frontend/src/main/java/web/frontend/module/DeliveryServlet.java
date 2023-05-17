package web.frontend.module;

import com.fasterxml.jackson.databind.ObjectMapper;
import backend.module.dao.memory.DeliveryDaoMemory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import backend.module.dao.DeliveryDao;
import backend.module.model.Delivery;

import java.io.IOException;

@WebServlet("/delivery")
public class DeliveryServlet extends HttpServlet {

    private DeliveryDao deliveryDao;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        deliveryDao = new DeliveryDaoMemory();
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
                Delivery delivery = deliveryDao.findById(id);
                if (delivery == null) {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                } else {
                    resp.setHeader("Content-Type", "application/json");
                    objectMapper.writeValue(resp.getOutputStream(), delivery);
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
            Delivery delivery = objectMapper.readValue(req.getInputStream(), Delivery.class);
            if (delivery == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            } else {
                deliveryDao.create(delivery);
                resp.setHeader("Content-Type", "application/json");
                objectMapper.writeValue(resp.getOutputStream(), delivery);
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
                deliveryDao.delete(id);
            } catch (NumberFormatException exc) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Delivery delivery = objectMapper.readValue(req.getInputStream(), Delivery.class);
        Long idParam = delivery.getId();
        String address = delivery.getAddress();
        try {
            Delivery delivery2 = deliveryDao.findById(idParam);
            delivery2.setAddress(address);
        } catch (NumberFormatException exc) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}