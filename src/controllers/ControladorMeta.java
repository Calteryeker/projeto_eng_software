package controllers;


import dao.IRepositorioMeta;
import dao.impl.DadosPersistentes;
import dao.impl.RepositorioMeta;
import dao.impl.exceptions.DadosNaoPreenchidosException;
import dao.impl.exceptions.MetaJaCadastradaException;
import dao.impl.exceptions.MetaNaoEncontradaException;
import model.Meta;

import java.time.LocalDate;
import java.util.List;

public class ControladorMeta {

    private IRepositorioMeta repositorioMeta;
    private static ControladorMeta instance;

    public ControladorMeta() {

        this.repositorioMeta = new RepositorioMeta(".\\localstorage\\metas.ser");

    }

    public static ControladorMeta getInstance() {

        if (instance == null) {
            instance = new ControladorMeta();
        }
        return instance;
    }

    public boolean validarDados(double valor, LocalDate data) {

        LocalDate dataAuxiliar = LocalDate.now();

        if (valor <= 0) {
            return false;
        } else if (data.getMonthValue() <= dataAuxiliar.getMonthValue()) {
            return false;
        }
        return true;
    }

    public Meta criarMeta(double valor, String descricao, LocalDate data) throws MetaJaCadastradaException, DadosNaoPreenchidosException {

        if (!validarDados(valor, data)) {
            throw new DadosNaoPreenchidosException("Os Dados Não Foram Preenchidos corretamente!!");
        } else {
            return repositorioMeta.criarMeta(valor, descricao, data);
        }
    }

    public Meta alterarMeta(double valor, String descricao, LocalDate data) throws MetaNaoEncontradaException, DadosNaoPreenchidosException {

        if (!validarDados(valor, data)) {
            throw new DadosNaoPreenchidosException("Os Dados Não Foram Preenchidos Corretamente!!");
        } else {
            return alterarMeta(valor, descricao, data);
        }
    }

    public Meta removerMeta(LocalDate data) throws MetaNaoEncontradaException, DadosNaoPreenchidosException {

        LocalDate dataAuxiliar = LocalDate.now();

        if (data.getMonthValue() < dataAuxiliar.getMonthValue()) {
            throw new DadosNaoPreenchidosException("Os Dados Não Foram Preenchidos Corretamente!!");
        } else {
            return repositorioMeta.removerMeta(data);
        }
    }

    public void visualizarHistoricoDeMetas() {

        for (Meta x : repositorioMeta.getMetas()) {
            System.out.println(x);
        }
    }

    public void setMetas(List<Meta> metas) {
        repositorioMeta.setMetas(metas);
    }

    public List<Meta> getMetas() {
        return repositorioMeta.getMetas();
    }

}
