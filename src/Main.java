import controllers.ControladorDadosPersistentes;
import controllers.ControladorLogin;
import dao.impl.exceptions.DadosNaoPreenchidosException;
import dao.impl.exceptions.SenhaIncorretaException;
import dao.impl.exceptions.UsuarioJaCadastradoException;
import dao.impl.exceptions.UsuarioNaoEncontradoException;

import java.util.Scanner;

import model.Usuario;
import views.LoginViewController;

public class Main {

    public static void main(String[] args) throws DadosNaoPreenchidosException, UsuarioJaCadastradoException {

        LoginViewController loginViewController = LoginViewController.getInstance();

        loginViewController.execute();


    }
}
