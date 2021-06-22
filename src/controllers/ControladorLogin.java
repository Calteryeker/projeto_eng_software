package controllers;

import dao.IDadosPersistentes;
import dao.impl.exceptions.SenhaIncorretaException;
import dao.impl.exceptions.UsuarioNaoEncontradoException;
import model.Usuario;

import java.util.List;

public class ControladorLogin {

    private static ControladorLogin instance;

    public static ControladorLogin getInstance() {

        if (instance == null) {
            instance = new ControladorLogin();
        }
        return instance;
    }

    public boolean login(String nome, String senha) throws SenhaIncorretaException, UsuarioNaoEncontradoException {

        List<Usuario> usuarios = ControladorDadosPersistentes.getInstance().getDadosPersistentes().getUsuarios();
        for (Usuario x : usuarios) {
            if (x.getLogin().equals(nome)) {
                if (x.getSenha().equals(senha)) {
                    System.out.println("Logado!!");
                    return true;
                } else {
                    throw new SenhaIncorretaException("Senha Incorreta !!");
                }
            }
        }
        throw new UsuarioNaoEncontradoException("Usuario não encontrado!!");
    }
}
