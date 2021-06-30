package dao.impl;

import dao.IDadosPersistentes;
import dao.impl.exceptions.UsuarioJaCadastradoException;
import model.Usuario;

import java.util.ArrayList;
import java.util.List;


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

    public void recuperarMetasUsuario() {

    }

    public void recuperarDespesasUsuario() {

    }

    public void salvarDadosUsuario() {

    }

    public void cadastrarNovoUsuario(String nome, String login, String senha) throws UsuarioJaCadastradoException {

        if (usuarios.isEmpty()) {
            usuarios.add(new Usuario(nome, login, senha));
            System.out.println("Usuario cadastrado com sucesso!!");
        } else {

            for (Usuario x : usuarios) {

                if (x.getNome().equals(nome) || x.getLogin().equals(login)) {
                    throw new UsuarioJaCadastradoException("Usuario já Cadastrado");
                }
            }
            usuarios.add(new Usuario(nome, login, senha));
            System.out.println("Usuario cadastrado com sucesso!!");
        }
        FileUtilRepository.saveFile(usuarios, path);
    }

    public void recuperarCategorias() {

    }

    public void removerUsuario() {

    }

    @Override
    public List<Usuario> getUsuarios() {
        return usuarios;
    }

}
