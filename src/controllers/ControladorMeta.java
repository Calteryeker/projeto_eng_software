package controllers;


import dao.IRepositorioMeta;
import dao.impl.DadosPersistentes;
import dao.impl.RepositorioMeta;
import dao.impl.exceptions.DadosNaoPreenchidosException;
import dao.impl.exceptions.MetaJaCadastradaException;
import model.Meta;

import java.time.LocalDate;

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

            String CSI = "\u001B[";

            System.out.println();
            System.out.print(CSI + "31" + "m");
            System.out.println("Valor Digitado Para a Meta Inválido!!");
            System.out.print(CSI + "m");
            return false;
        } else if (data.getDayOfMonth() < dataAuxiliar.getDayOfMonth()) {
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

    public void removerMeta() {

    }


}
