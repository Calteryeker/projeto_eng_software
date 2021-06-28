package dao.impl;

import dao.impl.exceptions.MetaNaoEncontradaException;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.Meta;

public class RepositorioMeta {

    private List<Meta> metas = new ArrayList<>();
    private String path;

    public RepositorioMeta(String path) {

        this.path = path;
        this.metas = new ArrayList<>();
    
        Object elementsList = FileUtilRepository.readFile(this.path);
        if (elementsList != null && elementsList instanceof List<?>){
          this.metas = (List<Meta>) elementsList;
        }
        
      }

    public Meta criarMeta(double valor, String descricao) {
        Meta auxMeta = new Meta(valor, descricao);

        metas.add(auxMeta);

        FileUtilRepository.saveFile(metas, path);

        return auxMeta;
    }

    public Meta alterarMeta(double valor, String descricao, LocalDate data) throws MetaNaoEncontradaException {

        Meta altMeta = null;
        int auxiliar = -1;

        for (int i = 0; i < metas.size(); i++) {
            if (metas.get(i).getData_criacao().equals(data)) {
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

    public Meta removerMeta(double valor, String descricao, LocalDate data) throws MetaNaoEncontradaException {

        Meta delMeta = null;
        int auxiliar = -1;
        for (int i = 0; i < metas.size(); i++) {
            if (metas.get(i).getData_criacao().equals(data)) {
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

        for (Meta x : metas) {
            System.out.println(x);
        }
    }


}

