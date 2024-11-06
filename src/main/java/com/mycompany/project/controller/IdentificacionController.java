package com.mycompany.project.controller;

import com.mycompany.project.data.IdentificacionDao;
import com.mycompany.project.domain.Identificacion;
import java.sql.SQLException;
import java.util.List;

public class IdentificacionController {

    private IdentificacionDao identificacionDao;

    public IdentificacionController() {
        identificacionDao = new IdentificacionDao();
    }

    public List<Identificacion> getIdentificacion() throws SQLException {
        return identificacionDao.getIdentificacion();
    }
}


