
package com.mycompany.project.controller;

import com.mycompany.project.data.SexoDao;
import com.mycompany.project.domain.Sexo;
import java.sql.SQLException;
import java.util.List;


public class SexoController {

    private SexoDao sexoDao;
    
    public SexoController() {
        sexoDao = new SexoDao();
    }
    
    public List<Sexo> getSexo() throws SQLException {
        return sexoDao.getSexos();
    }
}


