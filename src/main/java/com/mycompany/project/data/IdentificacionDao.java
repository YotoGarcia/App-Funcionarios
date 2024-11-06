
package com.mycompany.project.data;

import com.mycompany.project.config.ConnectionConfig;
import com.mycompany.project.domain.Identificacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class IdentificacionDao {

    private static final String GET_IDENTIFICACION = "select * from tipo_identificacion";
    
    
    public List<Identificacion> getIdentificacion() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Identificacion> identificaciones = new ArrayList<>();
        try {
            connection = ConnectionConfig.getConnection();
            preparedStatement = connection.prepareStatement(GET_IDENTIFICACION);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Identificacion identificacion = new Identificacion();
                identificacion.setId(resultSet.getInt("id_tipo_identificacion"));
                identificacion.setDescripcion(resultSet.getString("descripcion"));
                identificaciones.add(identificacion);
            }
            return identificaciones;
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
