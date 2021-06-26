package dao;

import dao.impl.exceptions.CategoriaJaCadastradaException;
import java.util.List;
import model.Categoria;


public interface IRepositorioCategoria {

  void criarCategoria(String nome) throws CategoriaJaCadastradaException;

  void editarCategoria(String nome, int idCategoria);

  void removerCategoria(int idCategoria);

  void setCategorias(List<Categoria> categorias);

  List<Categoria> getCategorias();
}
