package model;

import dao.impl.exceptions.CategoriaJaCadastradaException;
import java.io.Serializable;


import dao.IRepositorioDespesa;
import dao.IRepositorioMeta;
import dao.impl.RepositorioDespesa;
import dao.impl.RepositorioMeta;
import java.util.ArrayList;
import java.util.List;

public class Usuario implements Serializable {

    private String nome;
    private String login;
    private String senha;
    private IRepositorioDespesa despesaRepo;
    private IRepositorioMeta metaRepo;
    private List<Categoria> categorias;

    public Usuario(String nome, String login, String senha) {

        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.categorias = new ArrayList<>();
        this.despesaRepo = new RepositorioDespesa("localstorage\\" + this.login + "_" + "despesas" + ".ser");
        this.metaRepo = new RepositorioMeta("localstorage\\" + this.login + "_" + "metas" + ".ser");
    }

    public Usuario() {
    };

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void addCategoria(String name) throws CategoriaJaCadastradaException {

        Categoria categoria = new Categoria(name);

        if(!categorias.contains(categoria)) {
            categorias.add(categoria);
        } else{
            throw new CategoriaJaCadastradaException("Categoria ja cadastrada");
        }
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
