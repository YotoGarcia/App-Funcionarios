
package com.mycompany.project.controller;

import com.mycompany.project.data.EstadosCivilesDao;
import com.mycompany.project.domain.EstadosCiviles;
import java.sql.SQLException;
import java.util.List;

public class EstadosCivilesController {

    private EstadosCivilesDao estadosCivilesDao;

    public EstadosCivilesController() {
        estadosCivilesDao = new EstadosCivilesDao();
    }

    public List<EstadosCiviles> getEstadosCiviles() throws SQLException {
        return estadosCivilesDao.getEstadosCiviles();
    }
}



