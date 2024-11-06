
package com.mycompany.project.data;

import com.mycompany.project.config.ConnectionConfig;
import com.mycompany.project.domain.Sexo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class SexoDao {

    private static final String GET_SEXOS = "select * from sexos";
    
    
    public List<Sexo> getSexos() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Sexo> sexos = new ArrayList<>();
        try {
            connection = ConnectionConfig.getConnection();
            preparedStatement = connection.prepareStatement(GET_SEXOS);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Sexo sexo = new Sexo();
                sexo.setId(resultSet.getInt("id_sexo"));
                sexo.setDescripcion(resultSet.getString("descripcion"));
                sexos.add(sexo);
            }
            return sexos;
        } finally {

            if (connection != null) {
                connection.close();
            }

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (resultSet != null) {
                resultSet.close();
            }
        }

    }
}