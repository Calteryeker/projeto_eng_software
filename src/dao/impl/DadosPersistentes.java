package dao.impl;

import dao.IDadosPersistentes;
import dao.impl.exceptions.UsuarioJaCadastradoException;
import dao.impl.exceptions.UsuarioNaoEncontradoException;
import model.Categoria;
import model.Despesa;
import model.Meta;
import model.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class DadosPersistentes implements IDadosPersistentes {

    private List<Usuario> usuarios;
    private String path;

    public DadosPersistentes(String path) {

        this.path = path;
        this.usuarios = new ArrayList<>();

        Object elementsList = FileUtilRepository.readFile(this.path);
        if (elementsList != null && elementsList instanceof List<?>){
            this.usuarios = (List<Usuario>) elementsList;
        }

    }

    public List<Meta> recuperarMetasUsuario(String login) {
        Usuario user = buscarUsuario(login);
        return user.getRepositorioMeta().getMetas();
    }

    public List<Despesa> recuperarDespesasUsuario(String login) {
        Usuario user = buscarUsuario(login);
        return user.getRepositorioDespesa().getDespesas();
    }

    public void salvarDadosUsuario(String login) {
        Usuario user = buscarUsuario(login);
        usuarios.remove(user);
        usuarios.add(user);
        FileUtilRepository.saveFile(usuarios, path);
    }

    public Usuario cadastrarNovoUsuario(String nome, String login, String senha) throws UsuarioJaCadastradoException {

        Usuario novoUsuario = null;

        for (Usuario x : usuarios) {
            if (x.getNome().equals(nome) || x.getLogin().equals(login)) {
                throw new UsuarioJaCadastradoException("Usuario já Cadastrado");
            }
        }
        
        novoUsuario = new Usuario(nome, login, senha);

        usuarios.add(novoUsuario);
        System.out.println("Usuario cadastrado com sucesso!!");
        FileUtilRepository.saveFile(usuarios, path);

        return novoUsuario;
    }

    public Map<String, Categoria> recuperarCategorias(Usuario usuario) {
        return usuario.getCategorias();
    }

    public Usuario removerUsuario(String login) throws UsuarioNaoEncontradoException {
        Usuario usuarioDel = null;
        usuarioDel = buscarUsuario(login);
        if (usuarioDel != null) {
            usuarios.remove(usuarioDel);
        } else {
            throw new UsuarioNaoEncontradoException("Usuario não encontrado!!");
        }

        FileUtilRepository.saveFile(usuarios, path);

        return usuarioDel;
    }

    @Override
    public List<Usuario> getUsuarios() {
        return usuarios;
    }


    public Usuario buscarUsuario(String login) {
        for (Usuario usuario : usuarios) {
            if (usuario.getLogin().equals(login)) {
                return usuario;
            }
        }
        return null;
    }

    @Override
    public Map<String, Categoria> recuperarCategorias(String login) {
        Usuario user = buscarUsuario(login);
        return user.getCategorias();
    }
}
