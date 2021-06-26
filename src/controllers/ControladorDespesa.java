package controllers;

import dao.IRepositorioDespesa;
import dao.impl.DadosPersistentes;
import dao.impl.RepositorioDespesa;
import dao.impl.exceptions.CategoriaNulaException;
import dao.impl.exceptions.DadosNaoPreenchidosException;
import dao.impl.exceptions.DespesaNaoEncontradaException;
import model.Categoria;

import java.time.LocalDate;
import java.util.List;

import model.Despesa;

public class ControladorDespesa {

    private IRepositorioDespesa repositorioDespesa;
    private static ControladorDespesa instance;

    public ControladorDespesa() {
        this.repositorioDespesa = new RepositorioDespesa(".\\localstorage\\despesas.ser");
    }

    public static ControladorDespesa getInstance() {

        if (instance == null) {
            instance = new ControladorDespesa();
        }
        return instance;
    }

    public void criarDespesa(String nome, double valor, LocalDate data_criacao, Categoria categoria) throws DadosNaoPreenchidosException {


        if (nome.equals(null) || valor <= 0 || data_criacao.equals(null) || categoria.equals(null)) {
            throw new DadosNaoPreenchidosException("Os dados não foram preenchidos corretamente");
        } else {
            repositorioDespesa.criarDespesa(nome, valor, data_criacao, categoria);
        }

    }

    public void alterarDespesa(String nome, int idDespesa, double valor, LocalDate data_criacao, Categoria categoria)
            throws DadosNaoPreenchidosException, DespesaNaoEncontradaException {

        if (nome.equals(null) || valor <= 0 || data_criacao.equals(null) || categoria.equals(null)) {
            throw new DadosNaoPreenchidosException("Os dados não foram preenchidos corretamente");
        } else {
            repositorioDespesa.editarDespesa(nome, idDespesa, valor, data_criacao, categoria);
        }
    }

    public void removerDespesa(int idDespesa) throws DespesaNaoEncontradaException {
        repositorioDespesa.removerDespesa(idDespesa);
    }

    public List<Despesa> visualizarDespesasPorCategoria(Categoria categoria) throws CategoriaNulaException {

        if (categoria == null) {
            throw new CategoriaNulaException("Categoria Nula!!");
        } else {
            return repositorioDespesa.visualizarDespesasPorCategoria(categoria);
        }

    }

}
