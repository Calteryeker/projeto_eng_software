package dao.impl;

import dao.IRepositorioCategoria;
import dao.impl.exceptions.CategoriaJaCadastradaException;
import dao.impl.exceptions.CategoriaNaoEncontradaException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.Categoria;

public class RepositorioCategoria implements IRepositorioCategoria {

  private List<Categoria> categorias;
  private String path;

  public RepositorioCategoria(String path) {

    this.path = path;
    this.categorias = new ArrayList<>();

    Object elementsList = FileUtilRepository.readFile(this.path);
    if (elementsList != null && elementsList instanceof List<?>){
      this.categorias = (List<Categoria>) elementsList;
    }

  }

  @Override
  public Categoria criarCategoria(String nome) throws CategoriaJaCadastradaException {

    int idAux = LocalDateTime.now().hashCode();
    Categoria categoriaAux = new Categoria(nome);
    categoriaAux.setIdCategoria(idAux);

    if (categorias.isEmpty()) {
      categorias.add(categoriaAux);
      System.out.println("Categoria cadastrada com sucesso!!");
    } else {

      for (Categoria x : categorias) {

        if (x.getNome().equals(nome)) {
          throw new CategoriaJaCadastradaException("Categoria já Cadastrada");
        }
      }
      categorias.add(categoriaAux);
      System.out.println("Categoria cadastrada com sucesso!!");
    }
    FileUtilRepository.saveFile(categorias, path);

    return categoriaAux;
  }

  @Override
  public Categoria editarCategoria(String nome, int idCategoria) throws CategoriaNaoEncontradaException {
    int auxiliar = -1;
        Categoria altCategoria = null;

        for (int i = 0; i < categorias.size(); i++) {
            if (categorias.get(i).getIdCategoria() == idCategoria) {
                auxiliar = i;
            }
        }

        if (auxiliar != -1) {
            altCategoria = new Categoria(nome);
            categorias.set(auxiliar, altCategoria);
        } else {
            throw new CategoriaNaoEncontradaException("Despesa não encontrada!!");
        }

        FileUtilRepository.saveFile(categorias, path);

        return altCategoria;
  }

  @Override
  public Categoria removerCategoria(int idCategoria) throws CategoriaNaoEncontradaException {
    Categoria delCategoria = null;
        int auxiliar = -1;
        for (int i = 0; i < categorias.size(); i++) {
            if (categorias.get(i).getIdCategoria() == idCategoria) {
                auxiliar = i;
                delCategoria = categorias.get(i);
            }
        }
        if (auxiliar != -1) {
            categorias.remove(auxiliar);
        } else {
            throw new CategoriaNaoEncontradaException("Despesa não encontrada!!");
        }

        FileUtilRepository.saveFile(categorias, path);

        return delCategoria;
  }

  @Override
  public void setCategorias(List<Categoria> categorias) {
    this.categorias = categorias;
  }

  @Override
  public List<Categoria> getCategorias() {
    return categorias;
  }
}
