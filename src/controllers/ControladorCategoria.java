package controllers;

import model.Categoria;

import java.util.ArrayList;
import java.util.List;

public class ControladorCategoria {

    private static ControladorCategoria instance;
    private List<Categoria> categorias = new ArrayList<>();

    private ControladorCategoria() {

    }

    public static ControladorCategoria getInstance() {

        if (instance == null) {
            instance = new ControladorCategoria();
        }
        return instance;
    }

    public void criarCategoria(String nome) {
        if (nome == null) {
            System.out.println("String null");
        } else {
            categorias.add(new Categoria(nome));
        }

    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }
}
