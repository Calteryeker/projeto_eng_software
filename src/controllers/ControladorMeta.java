package controllers;


import dao.IRepositorioMeta;
import dao.impl.exceptions.DadosNaoPreenchidosException;
import dao.impl.exceptions.MetaJaCadastradaException;
import dao.impl.exceptions.MetaNaoEncontradaException;
import model.Meta;

import java.time.LocalDate;

public class ControladorMeta {

    private static IRepositorioMeta repositorioMeta;
    private static ControladorMeta instance;

    private ControladorMeta(IRepositorioMeta repoMeta) {
        repositorioMeta = repoMeta;
    }

    public static ControladorMeta getInstance(IRepositorioMeta repoMeta) {

        if (instance == null) {
            instance = new ControladorMeta(repoMeta);
        }

        repositorioMeta = repoMeta;
        return instance;
    }

    public boolean validarDados(double valor, LocalDate data) {

        LocalDate dataAuxiliar = LocalDate.now();

        if (valor <= 0) {

            String CSI = "\u001B[";

            System.out.println();
            System.out.print(CSI + "31" + "m");
            System.out.println("Valor Digitado Para a Meta Inválido!!");
            System.out.print(CSI + "m");
            return false;
        } else if (data.getMonthValue() < dataAuxiliar.getMonthValue()) {
            String CSI = "\u001B[";

            System.out.println();
            System.out.print(CSI + "31" + "m");
            System.out.println("Você Não Pode Selecionar um Mês Anterior ao Atual!!");
            System.out.print(CSI + "m");
            return false;
        }
        return true;
    }

    public Meta criarMeta(double valor, String descricao, LocalDate data) throws MetaJaCadastradaException, DadosNaoPreenchidosException {

        if (!validarDados(valor, data)) {
            throw new DadosNaoPreenchidosException("Os dados não foram preenchidos corretamente");
        } else {
            return repositorioMeta.criarMeta(valor,descricao,data);
        }
    }

    public Meta removerMeta(LocalDate data) throws MetaNaoEncontradaException {
        return repositorioMeta.removerMeta(data);
    }

    public Meta alterarMeta(double valor, String descricao, LocalDate data) throws MetaNaoEncontradaException, DadosNaoPreenchidosException {

        if (!validarDados(valor, data)) {
            throw new DadosNaoPreenchidosException("Os dados não foram preenchidos corretamente");
        } else {
            return repositorioMeta.alterarMeta(valor,descricao,data);
        }
    }


}
