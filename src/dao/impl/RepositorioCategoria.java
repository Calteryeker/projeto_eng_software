package dao.impl;

import dao.IRepositorioCategoria;
import dao.impl.exceptions.CategoriaJaCadastradaException;
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
  public void criarCategoria(String nome) throws CategoriaJaCadastradaException {

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
  }

  @Override
  public void editarCategoria(String nome, int idCategoria) {

  }

  @Override
  public void removerCategoria(int idCategoria) {

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
