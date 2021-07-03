package dao.impl;

import dao.IRepositorioMeta;
import dao.impl.exceptions.MetaJaCadastradaException;
import dao.impl.exceptions.MetaNaoEncontradaException;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import model.Meta;

public class RepositorioMeta implements IRepositorioMeta, Serializable {

    private List<Meta> metas = new ArrayList<>();
    private String path;

    public RepositorioMeta(String path) {

        this.path = path;
        this.metas = new ArrayList<>();

        Object elementsList = FileUtilRepository.readFile(this.path);
        if (elementsList != null && elementsList instanceof List<?>) {
            this.metas = (List<Meta>) elementsList;
        }

    }

    public Meta criarMeta(double valor, String descricao, LocalDate data) throws MetaJaCadastradaException {
        Meta auxMeta = new Meta(valor, descricao, data);

        for (Meta meta : metas) {
            if (meta.getData_criacao().getMonth().equals(data.getMonth())) {
                throw new MetaJaCadastradaException("Meta já Cadastrada");
            }
        }

        metas.add(auxMeta);
        System.out.println("Meta cadastrada com sucesso!!");

        FileUtilRepository.saveFile(metas, path);

        return auxMeta;
    }

    public Meta alterarMeta(double valor, String descricao, LocalDate data) throws MetaNaoEncontradaException {

        Meta altMeta = null;
        int auxiliar = -1;

        for (int i = 0; i < metas.size(); i++) {
            if (metas.get(i).getData_criacao().getMonthValue() == data.getMonthValue()) {
                auxiliar = i;
                altMeta = metas.get(i);
            }
        }

        if (auxiliar != -1) {
            altMeta = new Meta();
            altMeta.setDescricao(descricao);
            altMeta.setValor(valor);
            metas.set(auxiliar, altMeta);
        } else {
            throw new MetaNaoEncontradaException("Meta não encontrada!!");
        }

        FileUtilRepository.saveFile(metas, path);

        return altMeta;
    }

    public Meta removerMeta(int mes) throws MetaNaoEncontradaException {

        Meta delMeta = null;
        int auxiliar = -1;
        for (int i = 0; i < metas.size(); i++) {
            if (metas.get(i).getData_criacao().getMonthValue() == mes) {
                auxiliar = i;
                delMeta = metas.get(i);
            }
        }
        if (auxiliar != -1) {
            metas.remove(auxiliar);
        } else {
            throw new MetaNaoEncontradaException("Meta não encontrada!!");
        }

        return delMeta;
    }

    public void setMetas(List<Meta> metas) {
        this.metas = metas;
    }

    public List<Meta> getMetas() {
        return metas;
    }

    public void visualizarHistoricoDeMetas() {

        if (metas != null) {
            for (int i = LocalDate.now().getMonthValue(); i < 13; i++) {
                Meta aux = null;

                for (Meta meta : metas) {
                    if (meta.getData_criacao().getMonthValue() == i) {
                        aux = meta;
                    }
                }

                if (aux != null) {
                    System.out.println(aux);
                } else {
                    System.out.println("Meta do Mês " + i + ": Não Existe Meta");
                }
            }
        } else {
            for (int i = LocalDate.now().getMonthValue(); i < 13; i++) {
                System.out.println("Meta do Mês " + i + ": Não Existe Meta");
            }
        }
    }

}
