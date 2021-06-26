package dao;

import dao.impl.exceptions.CategoriaJaCadastradaException;
import dao.impl.exceptions.CategoriaNaoEncontradaException;

import java.util.List;
import model.Categoria;


public interface IRepositorioCategoria {

  Categoria criarCategoria(String nome) throws CategoriaJaCadastradaException;

  Categoria editarCategoria(String nome, int idCategoria) throws CategoriaNaoEncontradaException;

  Categoria removerCategoria(int idCategoria) throws CategoriaNaoEncontradaException;

  void setCategorias(List<Categoria> categorias);

  List<Categoria> getCategorias();
}
