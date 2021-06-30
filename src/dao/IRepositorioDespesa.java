package dao;

import dao.impl.exceptions.DespesaNaoEncontradaException;
import java.time.LocalDate;
import java.util.List;
import model.Categoria;
import model.Despesa;

public interface IRepositorioDespesa {

  Despesa criarDespesa(String nome, double valor, LocalDate data_criacao, Categoria categoria);

  Despesa editarDespesa(String nome, int idDespesa, double valor, LocalDate data_criacao, Categoria categoria) throws DespesaNaoEncontradaException;

  Despesa removerDespesa(int idDespesa) throws DespesaNaoEncontradaException;

  void setDespesas(List<Despesa> despesas);

  List<Despesa> getDespesas();

  void visualizarDespesas();

  void visualizarDespesaGrafico();

  List<Despesa> visualizarDespesasPorCategoria(Categoria categoria);


}
