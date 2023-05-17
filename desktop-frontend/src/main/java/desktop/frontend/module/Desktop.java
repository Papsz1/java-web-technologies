package desktop.frontend.module;

import backend.module.dao.CateringDao;
import backend.module.dao.DaoFactory;
import backend.module.model.Catering;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Desktop {

    private static final Logger LOG = LoggerFactory.getLogger(Desktop.class);

    public static void main(String[] args) {
        DaoFactory daoFactory = DaoFactory.getInstance();
        CateringDao catering = daoFactory.getCateringDao();
        Catering catering2 = catering.findById(1L);
        LOG.info(catering2.toString());
    }
}
