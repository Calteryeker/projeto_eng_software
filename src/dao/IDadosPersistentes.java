package dao;

import dao.impl.exceptions.UsuarioJaCadastradoException;
import dao.impl.exceptions.UsuarioNaoEncontradoException;
import model.Categoria;
import model.Despesa;
import model.Meta;
import model.Usuario;

import java.util.List;
import java.util.Map;

public interface IDadosPersistentes {

    List<Meta> recuperarMetasUsuario(String login);

    List<Despesa> recuperarDespesasUsuario(String login);

    void salvarDadosUsuario(String login);

    Usuario cadastrarNovoUsuario(String nome, String login, String senha) throws UsuarioJaCadastradoException;

    public Usuario atualizarUsuario(Usuario usuario) throws UsuarioNaoEncontradoException;

    List<Categoria> recuperarCategorias(String login);

    Usuario removerUsuario(String login) throws UsuarioNaoEncontradoException;

    List<Usuario> getUsuarios();

}
