package controllers;

import dao.impl.exceptions.DadosNaoPreenchidosException;
import model.Categoria;

import java.time.LocalDate;

public class ControladorDespesa {

    private static ControladorDespesa instance;

    public static ControladorDespesa getInstance() {

        if (instance == null) {
            instance = new ControladorDespesa();
        }
        return instance;
    }

    public void criarDespesa(String nome, double valor, LocalDate data_criacao, Categoria categoria) throws DadosNaoPreenchidosException {

        if (nome.equals(null) || valor == 0 || data_criacao.equals(null) || categoria.equals(null)) {
            throw new DadosNaoPreenchidosException("Os dados não foram preenchidos corretamente");
        } else {

        }




    }
}
