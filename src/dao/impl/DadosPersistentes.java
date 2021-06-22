package dao.impl;

import dao.IDadosPersistentes;
import dao.impl.exceptions.UsuarioCadastradoException;
import model.Usuario;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class DadosPersistentes implements IDadosPersistentes {

    private List<Usuario> usuarios = new ArrayList<>();
    private static String path = "./localstorage/usuarios.dat";

    public DadosPersistentes() {

        File e = new File(path);

        if (e.exists()) {
            FileUtilRepository.readFile(path);
        } else {
            usuarios = new ArrayList<>();
            FileUtilRepository.saveFile(usuarios, path);
        }
        FileUtilRepository.saveFile(usuarios, path);
    }

    public void recuperarMetasUsuario() {

    }

    public void recuperarDespesasUsuario() {

    }

    public void salvarDadosUsuario() {

    }

    public void cadastrarNovoUsuario(String nome, String login, String senha) throws UsuarioCadastradoException {

        if (usuarios.isEmpty()) {
            usuarios.add(new Usuario(nome, login, senha));
        } else {

            for (Usuario x : usuarios) {

                if (x.getNome().equals(nome) || x.getLogin().equals(login)) {

                    throw new UsuarioCadastradoException("Usuario Cadastrado");

                } else {
                    usuarios.add(new Usuario(nome, login, senha));
                }
            }
        }
        FileUtilRepository.saveFile(usuarios, path);
    }

    public void recuperarCategorias() {

    }

    public void removerUsuario() {

    }
}
