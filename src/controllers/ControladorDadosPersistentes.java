package controllers;

import dao.IDadosPersistentes;
import dao.impl.exceptions.DadosNaoPreenchidosException;
import dao.impl.exceptions.UsuarioCadastradoException;

public class ControladorDadosPersistentes {

    private IDadosPersistentes dadosPersistentes;
    private static ControladorDadosPersistentes instance;

    public static ControladorDadosPersistentes getInstance() {

        if (instance == null) {
            instance = new ControladorDadosPersistentes();
        }
        return instance;
    }

    public void cadastrarUsuario(String nome, String login, String senha) throws DadosNaoPreenchidosException, UsuarioCadastradoException {

        if (nome.equals(null) || login.equals(null) || senha.equals(null)) {
            throw new DadosNaoPreenchidosException("Os dados não foram preenchidos corretamente");
        } else {

            dadosPersistentes.cadastrarNovoUsuario(nome, login, senha);
        }


    }


}
