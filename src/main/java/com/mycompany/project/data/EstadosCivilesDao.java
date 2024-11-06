
package com.mycompany.project.data;

import com.mycompany.project.config.ConnectionConfig;
import com.mycompany.project.domain.EstadosCiviles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class EstadosCivilesDao {

    private static final String GET_ESTADOS_CIVILES = "select * from estados_civiles";
    
    
    public List<EstadosCiviles> getEstadosCiviles() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<EstadosCiviles> estadosCivilesList = new ArrayList<>();
        try {
            connection = ConnectionConfig.getConnection();
            preparedStatement = connection.prepareStatement(GET_ESTADOS_CIVILES);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                EstadosCiviles estadosCiviles = new EstadosCiviles();
                estadosCiviles.setId(resultSet.getInt("id_estado_civil"));
                estadosCiviles.setDescripcion(resultSet.getString("descripcion"));
                estadosCivilesList.add(estadosCiviles);
            }
            return estadosCivilesList;
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


