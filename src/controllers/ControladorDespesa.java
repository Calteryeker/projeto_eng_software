package controllers;

import dao.IRepositorioDespesa;
import dao.impl.RepositorioDespesa;
import dao.impl.exceptions.CategoriaNulaException;
import dao.impl.exceptions.DadosNaoPreenchidosException;
import dao.impl.exceptions.DespesaNaoEncontradaException;
import model.Categoria;

import java.time.LocalDate;
import java.util.List;

import model.Despesa;

public class ControladorDespesa {

    private static IRepositorioDespesa repositorioDespesa;
    private static ControladorDespesa instance;

    private ControladorDespesa(IRepositorioDespesa despesaRepo) {
        repositorioDespesa = despesaRepo;
    }

    public static ControladorDespesa getInstance(IRepositorioDespesa despesaRepo) {

        if (instance == null) {
            instance = new ControladorDespesa(despesaRepo);
        }

        repositorioDespesa = despesaRepo;
        return instance;
    }

    public Despesa criarDespesa(String nome, double valor, LocalDate data_criacao, Categoria categoria) throws DadosNaoPreenchidosException {


        if (nome == null || valor <= 0 || data_criacao == null || categoria == null) {
            throw new DadosNaoPreenchidosException("Os dados não foram preenchidos corretamente");
        } else {
            return repositorioDespesa.criarDespesa(nome, valor, data_criacao, categoria);
        }
    }

    public Despesa alterarDespesa(String nome, int idDespesa, double valor, LocalDate data_criacao, Categoria categoria)
            throws DadosNaoPreenchidosException, DespesaNaoEncontradaException {

        if (nome.equals(null) || valor <= 0 || data_criacao.equals(null) || categoria.equals(null)) {
            throw new DadosNaoPreenchidosException("Os dados não foram preenchidos corretamente");
        } else {
            return repositorioDespesa.editarDespesa(nome, idDespesa, valor, data_criacao, categoria);
        }
    }

    public Despesa removerDespesa(int idDespesa) throws DespesaNaoEncontradaException {
        return repositorioDespesa.removerDespesa(idDespesa);
    }

    public List<Despesa> visualizarDespesasPorCategoria(Categoria categoria) throws CategoriaNulaException {

        if (categoria == null) {
            throw new CategoriaNulaException("Categoria Nula!!");
        } else {
            return repositorioDespesa.visualizarDespesasPorCategoria(categoria);
        }

    }

}
