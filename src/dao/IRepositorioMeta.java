package dao;

import dao.impl.exceptions.MetaJaCadastradaException;
import dao.impl.exceptions.MetaNaoEncontradaException;
import model.Meta;

import java.time.LocalDate;
import java.util.List;

public interface IRepositorioMeta {

    Meta criarMeta(double valor, String descricao, LocalDate data) throws MetaJaCadastradaException;

    Meta removerMeta(LocalDate data) throws MetaNaoEncontradaException;

    Meta alterarMeta(double valor, String descricao, LocalDate data) throws MetaNaoEncontradaException;

    void setMetas(List<Meta> metas);

    List<Meta> getMetas();

    void visualizarHistoricoDeMetas();


}
