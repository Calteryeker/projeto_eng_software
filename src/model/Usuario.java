package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.IRepositorioDespesa;
import dao.IRepositorioMeta;
import dao.impl.RepositorioDespesa;
import dao.impl.RepositorioMeta;

public class Usuario implements Serializable {

    private String nome;
    private String login;
    private String senha;
    private IRepositorioDespesa despesaRepo;
    private IRepositorioMeta metaRepo;
    private Map<String, Categoria> categorias;

    public Usuario(String nome, String login, String senha) {

        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.categorias = new HashMap<>();
        this.despesaRepo = new RepositorioDespesa("localstorage/" + this.login + "_" + "despesas" + ".ser");
        this.metaRepo = new RepositorioMeta("localstorage/" + this.login + "_" + "metas" + ".ser");
    }

    public Usuario() {
    };

    public Map<String, Categoria> getCategorias() {
        return categorias;
    }

    public void addCategoria(String name) {
        categorias.put(name, new Categoria(name));
    }

    public IRepositorioDespesa getRepositorioDespesa() {
        return despesaRepo;
    }

    public IRepositorioMeta getRepositorioMeta() {
        return metaRepo;
    }

    public String getNome() {
        return nome;
    }

    public String getSenha() {
        return senha;
    }

    public String getLogin() {
        return login;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof Usuario){
            Usuario aux = (Usuario)o;
            if(aux.getLogin() == this.login){
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode(){
        int primo = 31;
        return (primo*login.hashCode());
    }

}
