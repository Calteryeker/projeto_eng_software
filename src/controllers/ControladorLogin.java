package controllers;

import dao.impl.exceptions.SenhaIncorretaException;
import dao.impl.exceptions.UsuarioNaoEncontradoException;
import java.util.List;
import model.Usuario;

public class ControladorLogin {

  private Usuario loggedUser = new Usuario();

  private static ControladorLogin instance;

  public static ControladorLogin getInstance() {

    if (instance == null) {
      instance = new ControladorLogin();
    }
    return instance;
  }

  public boolean login(String nome, String senha)
      throws SenhaIncorretaException, UsuarioNaoEncontradoException {

    List<Usuario> usuarios =
        ControladorDadosPersistentes.getInstance().getDadosPersistentes().getUsuarios();
    for (Usuario x : usuarios) {
      if (x.getLogin().equals(nome)) {
        if (x.getSenha().equals(senha)) {

          loggedUser = x;
          return true;
        } else {
          throw new SenhaIncorretaException("Senha Incorreta!!");
        }
      }
    }
    throw new UsuarioNaoEncontradoException("Usuario não encontrado!!");
  }

  public void logout() {
    loggedUser = null;
  }

  public Usuario getLoggedUser() {
    return loggedUser;
  }
}
