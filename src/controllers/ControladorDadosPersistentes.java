package controllers;

import dao.IDadosPersistentes;
import dao.impl.DadosPersistentes;
import dao.impl.exceptions.DadosNaoPreenchidosException;
import dao.impl.exceptions.UsuarioJaCadastradoException;
import dao.impl.exceptions.UsuarioNaoEncontradoException;
import model.Usuario;

public class ControladorDadosPersistentes {

    private IDadosPersistentes dadosPersistentes;
    private static ControladorDadosPersistentes instance;

    private ControladorDadosPersistentes() {
        this.dadosPersistentes = new DadosPersistentes("localstorage\\usuarios.ser");
    }

    public static ControladorDadosPersistentes getInstance() {

        if (instance == null) {
            instance = new ControladorDadosPersistentes();
        }
        return instance;
    }

    public void cadastrarUsuario(String nome, String login, String senha) throws DadosNaoPreenchidosException, UsuarioJaCadastradoException {

        if (nome == null || login == null || senha == null) {
            throw new DadosNaoPreenchidosException("Os dados não foram preenchidos corretamente");
        } else {
            dadosPersistentes.cadastrarNovoUsuario(nome, login, senha);
        }
    }

    public void atualizarUsuario(Usuario usuario) throws UsuarioNaoEncontradoException {
        if(usuario != null){
            dadosPersistentes.atualizarUsuario(usuario);
        }
    }

    public IDadosPersistentes getDadosPersistentes() {
        return dadosPersistentes;
    }

    public Usuario buscarUsuario(String login) {
        return ((DadosPersistentes) dadosPersistentes).buscarUsuario(login);
    }
}
