package com.mycompany.project.view;

import com.mycompany.project.controller.FuncionarioController;
import com.mycompany.project.domain.Funcionario;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ProjectApp {
    
    
   public static void getFuncionarios(FuncionarioController funcionarioController) {
        try {
            List<Funcionario> funcionarios = funcionarioController.getListFuncionario();
            if (funcionarios.isEmpty()) {
                System.out.println("No hay funcionarios");
            } else {
                funcionarios.forEach(funcionario -> {
                    System.out.println("Id = " + funcionario.getId_funcionario());
                    System.out.println("Tipo de documento = " + funcionario.getNombreTipoIdentificacion());
                    System.out.println("Numero de documento = " + funcionario.getNumero_identificacion());
                    System.out.println("Nombres = " + funcionario.getNombres());
                    System.out.println("Apellidos = " + funcionario.getApellidos());
                    System.out.println("Estado civil = " + funcionario.getNombreEstadoCivil());
                    System.out.println("Sexo = " + funcionario.getNombreSexo());
                    System.out.println("Telefono = " + funcionario.getTelefono());
                    System.out.println("Dirección = " + funcionario.getDireccion());
                    System.out.println("------------------------");
                    System.out.println("------------------------");
                    System.out.println("------------------------");
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();

        }

    }
   
   public static void getFuncionario(FuncionarioController funcionarioController) {

        try {
            Scanner sc = new Scanner(System.in);
            
            System.out.println("Digite el id: ");
            int id_funcionario = sc.nextInt();
            System.out.println("id del funcionario: " + id_funcionario);
            System.out.println("-------------------------- ");
            
            Funcionario funcionario = funcionarioController.getFuncionario(id_funcionario);
            if (funcionario == null) {
                System.out.println("No existe funcionarios");
            } else {
                    System.out.println("Tipo de documento = " + funcionario.getNombreTipoIdentificacion());
                    System.out.println("Numero de documento = " + funcionario.getNumero_identificacion());
                    System.out.println("Nombres = " + funcionario.getNombres());
                    System.out.println("Apellidos = " + funcionario.getApellidos());
                    System.out.println("Estado civil = " + funcionario.getNombreEstadoCivil());
                    System.out.println("Sexo = " + funcionario.getNombreSexo());
                    System.out.println("Telefono = " + funcionario.getTelefono());
                    System.out.println("Dirección = " + funcionario.getDireccion());
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void create(FuncionarioController funcionarioController) {
        try {
            Scanner sc = new Scanner(System.in);

            System.out.println("Elija el tipo de documento:");
            int tipo_identificacion = sc.nextInt();
            sc.nextLine();
            System.out.println("Tipo de documento: " + tipo_identificacion);
            System.out.println("========================");

            System.out.println("Digite el número de identificación:");
            int identificacion = sc.nextInt();
            sc.nextLine();
            System.out.println("Número de identificación: " + identificacion);
            System.out.println("========================");

            System.out.println("Digite el nombre del funcionario:");
            String nombre = sc.nextLine();
            System.out.println("Nombre: " + nombre);
            System.out.println("========================");

            System.out.println("Digite los apellidos del funcionario:");
            String apellidos = sc.nextLine();
            System.out.println("Apellidos: " + apellidos);
            System.out.println("========================");

            System.out.println("Escoja el estado civil del funcionario:");
            int estado_civil = sc.nextInt();
            sc.nextLine();
            System.out.println("Estado civil: " + estado_civil);
            System.out.println("========================");

            System.out.println("Escoja el sexo del funcionario:");
            int id_sexo = sc.nextInt();
            sc.nextLine();
            System.out.println("Sexo: " + id_sexo);
            System.out.println("========================");

            System.out.println("Digite la dirección del funcionario:");
            String direccion = sc.nextLine();
            System.out.println("Dirección: " + direccion);
            System.out.println("========================");

            System.out.println("Digite el teléfono del funcionario:");
            String telefono = sc.nextLine();
            System.out.println("Teléfono: " + telefono);
            System.out.println("========================");

            Funcionario funcionario = new Funcionario();
            funcionario.setTipo_identificacion(tipo_identificacion);
            funcionario.setNumero_identificacion(identificacion);
            funcionario.setNombres(nombre);
            funcionario.setApellidos(apellidos);
            funcionario.setEstado_civil(estado_civil);
            funcionario.setId_sexo(id_sexo);
            funcionario.setDireccion(direccion);
            funcionario.setTelefono(telefono);

            funcionarioController.create(funcionario);
            System.out.println("Funcionario creado con éxito");

        } catch (SQLException ex) {
            System.out.println("Error al crear el funcionario: " + ex.getMessage());
            ex.printStackTrace();

        }
    }

    

    public static void UpdateFuncionario(FuncionarioController funcionarioController) throws SQLException {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Digite ID de funcionario a actualizar");
            int id_funcionario = sc.nextInt();
            System.out.println("el ID es: " + id_funcionario);
            System.out.println("--------------------------------------- ");
            Funcionario funcionarioExits = funcionarioController.getFuncionario(id_funcionario);
            if (funcionarioExits == null) {
                System.out.println("No existe funcionario");
                return;
            }
            Scanner tl = new Scanner(System.in);
            
            System.out.println("Elija el tipo de documento:");
            int tipo_identificacion = tl.nextInt();
            tl.nextLine();
            System.out.println("Tipo de documento: " + tipo_identificacion);
            System.out.println("========================");
            
            System.out.println("Digite el número de identificación:");
            int identificacion = tl.nextInt();
            tl.nextLine();
            System.out.println("Número de identificación: " + identificacion);
            System.out.println("========================");
            
            System.out.println("Digite el nombre del funcionario:");
            String nombre = tl.nextLine();
            System.out.println("Nombre: " + nombre);
            System.out.println("========================");
            
            System.out.println("Digite los apellidos del funcionario:");
            String apellidos = tl.nextLine();
            System.out.println("Apellidos: " + apellidos);
            System.out.println("========================");
            
            System.out.println("Escoja el estado civil del funcionario:");
            int estado_civil = tl.nextInt();
            tl.nextLine();
            System.out.println("Estado civil: " + estado_civil);
            System.out.println("========================");
            
            System.out.println("Escoja el sexo del funcionario:");
            int id_sexo = tl.nextInt();
            tl.nextLine();
            System.out.println("Sexo: " + id_sexo);
            System.out.println("========================");
            
            System.out.println("Digite la dirección del funcionario:");
            String direccion = tl.nextLine();
            System.out.println("Dirección: " + direccion);
            System.out.println("========================");
            
            System.out.println("Digite el teléfono del funcionario:");
            String telefono = tl.nextLine();
            System.out.println("Teléfono: " + telefono);
            System.out.println("========================");

            Funcionario funcionario = new Funcionario();
            funcionario.setTipo_identificacion(tipo_identificacion);
            funcionario.setNumero_identificacion(identificacion);
            funcionario.setNombres(nombre);
            funcionario.setApellidos(apellidos);
            funcionario.setEstado_civil(estado_civil);
            funcionario.setId_sexo(id_sexo);
            funcionario.setDireccion(direccion);
            funcionario.setTelefono(telefono);

            funcionarioController.updateFuncionario(id_funcionario, funcionario);
            System.out.println("Funcionario Actualizar con éxito");

        } catch (SQLException ex) {
            System.out.println("Error al Actualizar el funcionario: " + ex.getMessage());
            ex.printStackTrace();

        }
    }

    public static void deleteFuncionario(FuncionarioController funcionarioController) {
        try {
            Scanner sc = new Scanner(System.in);

            System.out.println("Digite el ID del funcionario a eliminar: ");
            int id_funcionario = sc.nextInt();
            System.out.println("el id del funcionario es: " + id_funcionario);
            Funcionario funcionarioExiste = funcionarioController.getFuncionario(id_funcionario);
            if (funcionarioExiste == null) {
                System.out.println("No existe funcionario ");
                return;
            }

            funcionarioController.deleteFuncionario(id_funcionario);
            System.out.println("Funcionario eliminado con exito ");
            System.out.println("-----------------------------------------");
            getFuncionarios(funcionarioController);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException {
        FuncionarioController funcionarioController = new FuncionarioController();
        //create(funcionarioController);
        getFuncionarios(funcionarioController);
        //getFuncionario(funcionarioController);
        //UpdateFuncionario(funcionarioController);
        //deleteFuncionario(funcionarioController);

    }

}
