package dao.impl;

import dao.impl.exceptions.MetaNaoEncontradaException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import model.Meta;

public class RepositorioMeta {

    List<Meta> metas = new ArrayList<>();

    public void criarMeta(double valor, String descricao) {
        metas.add(new Meta(valor, descricao));
    }

    public void alterarMeta(double valor, String descricao) throws MetaNaoEncontradaException {

        int auxiliar = -1;
        for (int i = 0; i < metas.size(); i++) {
            if (metas.get(i).getDescricao().equals(descricao)) {
                auxiliar = i;
            }
        }

        if (auxiliar != -1) {

            metas.set(auxiliar, new Meta(valor, descricao));
            System.out.println("Meta alterada com sucesso !!");
        } else {
            throw new MetaNaoEncontradaException("Despesa não encontrada!!");
        }
    }

    public void removerMeta(double valor, String descricao) throws MetaNaoEncontradaException {

        int auxiliar = -1;
        for (int i = 0; i < metas.size(); i++) {
            if (metas.get(i).getDescricao().equals(descricao)) {
                auxiliar = i;
            }
        }

        if (auxiliar != -1) {

            metas.remove(auxiliar);
            System.out.println("Meta removida com sucesso !!");
        } else {
            throw new MetaNaoEncontradaException("Despesa não encontrada!!");
        }
    }

    public void setMetas(List<Meta> metas) {
        this.metas = metas;
    }

    public List<Meta> getMetas() {
        return metas;
    }

    public void visualizarHistoricoDeMetas() {

        for (Meta x : metas) {
            System.out.println(x);
        }
    }


}

