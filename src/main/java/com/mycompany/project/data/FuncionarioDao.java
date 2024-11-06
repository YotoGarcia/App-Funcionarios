package com.mycompany.project.data;

import com.mycompany.project.config.ConnectionConfig;
import com.mycompany.project.domain.Funcionario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDao {

    private static final String GET_FUNCIONARIOS
            = "SELECT f.id_funcionario, "
            + "f.id_tipo_identificacion, "
            + "ti.descripcion AS tipo_identificacion_desc, "
            + "f.numero_identificacion, "
            + "f.nombres, "
            + "f.apellidos, "
            + "f.direccion, "
            + "f.telefono, "
            + "f.id_estado_civil, "
            + "ec.descripcion AS estado_civil_desc, "
            + "f.id_sexo, "
            + "s.descripcion AS sexo_desc "
            + "FROM funcionarios f "
            + "JOIN tipo_identificacion ti ON f.id_tipo_identificacion = ti.id_tipo_identificacion "
            + "JOIN estados_civiles ec ON f.id_estado_civil = ec.id_estado_civil "
            + "JOIN sexos s ON f.id_sexo = s.id_sexo;";

    private static final String CREATE_FUNCIONARIO = "INSERT INTO funcionarios (id_tipo_identificacion, numero_identificacion, "
            + "nombres, apellidos, direccion, telefono, id_estado_civil, id_sexo) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String GET_FUNCIONARIO_BY_ID = "SELECT f.id_funcionario, "
            + "f.id_tipo_identificacion, "
            + "ti.descripcion AS tipo_identificacion_desc, "
            + "f.numero_identificacion, "
            + "f.nombres, "
            + "f.apellidos, "
            + "f.direccion, "
            + "f.telefono, "
            + "f.id_estado_civil, "
            + "ec.descripcion AS estado_civil_desc, "
            + "f.id_sexo, "
            + "s.descripcion AS sexo_desc "
            + "FROM funcionarios f "
            + "JOIN tipo_identificacion ti ON f.id_tipo_identificacion = ti.id_tipo_identificacion "
            + "JOIN estados_civiles ec ON f.id_estado_civil = ec.id_estado_civil "
            + "JOIN sexos s ON f.id_sexo = s.id_sexo "
            + "WHERE f.id_funcionario = ?";

    private static final String UPDATE_FUNCIONARIO = "update funcionarios set id_tipo_identificacion = ?, numero_identificacion = ?, "
            + "nombres = ?, apellidos = ?, direccion = ?, telefono = ?, id_estado_civil = ?, id_sexo = ? where id_funcionario = ?";

    private static final String DELETE_FUNCIONARIO = "delete from funcionarios where id_funcionario = ?";

    public List<Funcionario> getFuncionarios() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Funcionario> funcionarios = new ArrayList<>();

        try {
            connection = ConnectionConfig.getConnection();

            preparedStatement = connection.prepareStatement(GET_FUNCIONARIOS);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Funcionario funcionario = new Funcionario();
                funcionario.setId_funcionario(resultSet.getInt("id_funcionario"));
                funcionario.setTipo_identificacion(resultSet.getInt("id_tipo_identificacion"));
                funcionario.setNombreTipoIdentificacion(resultSet.getString("tipo_identificacion_desc"));
                funcionario.setNumero_identificacion(resultSet.getInt("numero_identificacion"));
                funcionario.setNombres(resultSet.getString("nombres"));
                funcionario.setApellidos(resultSet.getString("apellidos"));
                funcionario.setEstado_civil(resultSet.getInt("id_estado_civil"));
                funcionario.setNombreEstadoCivil(resultSet.getString("estado_civil_desc"));
                funcionario.setId_sexo(resultSet.getInt("id_sexo"));
                funcionario.setNombreSexo(resultSet.getString("sexo_desc"));
                funcionario.setDireccion(resultSet.getString("direccion"));
                funcionario.setTelefono(resultSet.getString("telefono"));

                funcionarios.add(funcionario);
            }
            return funcionarios;

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

    public void createFuncionario(Funcionario funcionario) throws SQLException {
        try (Connection connection = ConnectionConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(CREATE_FUNCIONARIO)) {

            preparedStatement.setInt(1, funcionario.getTipo_identificacion());
            preparedStatement.setInt(2, funcionario.getNumero_identificacion());
            preparedStatement.setString(3, funcionario.getNombres());
            preparedStatement.setString(4, funcionario.getApellidos());
            preparedStatement.setString(5, funcionario.getDireccion());
            preparedStatement.setString(6, funcionario.getTelefono());
            preparedStatement.setInt(7, funcionario.getEstado_civil());
            preparedStatement.setInt(8, funcionario.getId_sexo());

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException("Error al crear el funcionario: " + ex.getMessage(), ex);
        }
    }

    public Funcionario getFuncionario(int id_funcionario) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Funcionario funcionario = null;

        try {
            connection = ConnectionConfig.getConnection();
            preparedStatement = connection.prepareStatement(GET_FUNCIONARIO_BY_ID);
            preparedStatement.setInt(1, id_funcionario);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                funcionario = new Funcionario();
                funcionario.setId_funcionario(resultSet.getInt("id_funcionario"));
                funcionario.setTipo_identificacion(resultSet.getInt("id_tipo_identificacion"));
                funcionario.setNombreTipoIdentificacion(resultSet.getString("tipo_identificacion_desc"));
                funcionario.setNumero_identificacion(resultSet.getInt("numero_identificacion"));
                funcionario.setNombres(resultSet.getString("nombres"));
                funcionario.setApellidos(resultSet.getString("apellidos"));
                funcionario.setEstado_civil(resultSet.getInt("id_estado_civil"));
                funcionario.setNombreEstadoCivil(resultSet.getString("estado_civil_desc"));
                funcionario.setId_sexo(resultSet.getInt("id_sexo"));
                funcionario.setNombreSexo(resultSet.getString("sexo_desc"));
                funcionario.setDireccion(resultSet.getString("direccion"));
                funcionario.setTelefono(resultSet.getString("telefono"));
            }

            return funcionario;

        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    public void updateFuncionario(int id_funcionario, Funcionario funcionario) throws SQLException {
        try (Connection connection = ConnectionConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_FUNCIONARIO)) {

            preparedStatement.setInt(1, funcionario.getTipo_identificacion());
            preparedStatement.setInt(2, funcionario.getNumero_identificacion());
            preparedStatement.setString(3, funcionario.getNombres());
            preparedStatement.setString(4, funcionario.getApellidos());
            preparedStatement.setString(5, funcionario.getDireccion());
            preparedStatement.setString(6, funcionario.getTelefono());
            preparedStatement.setInt(7, funcionario.getEstado_civil());
            preparedStatement.setInt(8, funcionario.getId_sexo());
            preparedStatement.setInt(9, id_funcionario);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException("Error al crear el funcionario: " + ex.getMessage(), ex);
        
        }
    }

    public void deleteFuncionario(int id) throws SQLException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {

            connection = ConnectionConfig.getConnection();
            preparedStatement = connection.prepareStatement(DELETE_FUNCIONARIO);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();

        } finally {
            if (connection != null) {
                connection.close();
            }

            if (preparedStatement != null) {
                preparedStatement.close();
            }

        }

    }
}
