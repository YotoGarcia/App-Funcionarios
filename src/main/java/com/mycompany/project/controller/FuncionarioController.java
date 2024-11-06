package com.mycompany.project.controller;

import com.mycompany.project.data.FuncionarioDao;
import com.mycompany.project.domain.Funcionario;
import java.sql.SQLException;
import java.util.List;

public class FuncionarioController {

    private FuncionarioDao funcionarioDao;

    public FuncionarioController() {

        funcionarioDao = new FuncionarioDao();
    }

    public List<Funcionario> getListFuncionario() throws SQLException {
        return funcionarioDao.getFuncionarios();
    }

    public void create(Funcionario funcionario) throws SQLException {
        funcionarioDao.createFuncionario(funcionario);
    }

    public Funcionario getFuncionario(int id_funcionario) throws SQLException {
        return funcionarioDao.getFuncionario(id_funcionario);
    }

    public void updateFuncionario(int id, Funcionario funcionario) throws SQLException {
        funcionarioDao.updateFuncionario(id, funcionario);
    }

    public void deleteFuncionario(int id_funcionario) throws SQLException {
        funcionarioDao.deleteFuncionario(id_funcionario);
    }
}
