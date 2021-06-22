package dao;

import dao.impl.exceptions.UsuarioCadastradoException;
import model.Usuario;

import java.util.List;

public interface IDadosPersistentes {

    void recuperarMetasUsuario();

    void recuperarDespesasUsuario();

    void salvarDadosUsuario();

    void cadastrarNovoUsuario(String nome, String login, String senha) throws UsuarioCadastradoException;

    void recuperarCategorias();

    void removerUsuario();

    List<Usuario> getUsuarios();

}
