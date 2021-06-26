package controllers;

import dao.IRepositorioCategoria;
import dao.impl.RepositorioCategoria;
import dao.impl.RepositorioDespesa;
import dao.impl.exceptions.CategoriaJaCadastradaException;
import model.Categoria;

import java.util.ArrayList;
import java.util.List;

public class ControladorCategoria {

    private static ControladorCategoria instance;
    private IRepositorioCategoria repositorioCategoria;

    private ControladorCategoria() {
        this.repositorioCategoria = new RepositorioCategoria(".\\localstorage\\categorias.ser");
    }

    public static ControladorCategoria getInstance() {

        if (instance == null) {
            instance = new ControladorCategoria();
        }
        return instance;
    }

    public void criarCategoria(String nome) throws CategoriaJaCadastradaException {
        if (nome == null) {
            System.out.println("Categoria invalida");
        } else {
            repositorioCategoria.criarCategoria(nome);
        }

    }

    public List<Categoria> getCategorias() {
        return repositorioCategoria.getCategorias();
    }

    public void setCategorias(List<Categoria> categorias) {
        repositorioCategoria.setCategorias(categorias);
    }
}
