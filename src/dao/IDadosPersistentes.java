package dao;

import dao.impl.exceptions.UsuarioJaCadastradoException;
import model.Usuario;

import java.util.List;

public interface IDadosPersistentes {

    void recuperarMetasUsuario();

    void recuperarDespesasUsuario();

    void salvarDadosUsuario();

    void cadastrarNovoUsuario(String nome, String login, String senha) throws UsuarioJaCadastradoException;

    void recuperarCategorias();

    void removerUsuario();

    List<Usuario> getUsuarios();

}
